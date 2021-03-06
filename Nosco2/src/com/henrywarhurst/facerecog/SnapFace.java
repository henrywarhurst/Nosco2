package com.henrywarhurst.facerecog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class SnapFace extends Activity implements CvCameraViewListener2,
		OnTouchListener {

	private static final String TAG = "OCVSample::Activity";
	private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
	public static final int JAVA_DETECTOR = 0;
	public static final int NATIVE_DETECTOR = 1;
	
	private static final String imgPath 		= Environment.DIRECTORY_PICTURES;

	private Mat mRgba;
	private Mat mCurFace;
	private Mat mGray;
	private File mCascadeFile;
	private CascadeClassifier mJavaDetector;
	private DetectionBasedTracker mNativeDetector;

	private String[] mDetectorName;

	private float mRelativeFaceSize = 0.3f;
	private int mAbsoluteFaceSize = 0;

	private String mFirstname;
	private String mLastname;
	private String mPersonId;
	
	// To notify the user when an image is captured
	Toast mToast;

	// Store the coordinates of the user touch.
	private double x = -1, y = -1;

	private CameraBridgeViewBase mOpenCvCameraView;

	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				Log.i(TAG, "OpenCV loaded successfully");

				// Load native library after(!) OpenCV initialization
				System.loadLibrary("detection_based_tracker");
				System.loadLibrary("adaptive_histogram");

				try {
					// load cascade file from application resources
					InputStream is = getResources().openRawResource(
							R.raw.lbpcascade_frontalface);
					File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
					mCascadeFile = new File(cascadeDir,
							"lbpcascade_frontalface.xml");
					FileOutputStream os = new FileOutputStream(mCascadeFile);

					byte[] buffer = new byte[4096];
					int bytesRead;
					while ((bytesRead = is.read(buffer)) != -1) {
						os.write(buffer, 0, bytesRead);
					}
					is.close();
					os.close();

					mJavaDetector = new CascadeClassifier(
							mCascadeFile.getAbsolutePath());
					if (mJavaDetector.empty()) {
						Log.e(TAG, "Failed to load cascade classifier");
						mJavaDetector = null;
					} else
						Log.i(TAG, "Loaded cascade classifier from "
								+ mCascadeFile.getAbsolutePath());

					mNativeDetector = new DetectionBasedTracker(
							mCascadeFile.getAbsolutePath(), 0);

					cascadeDir.delete();

				} catch (IOException e) {
					e.printStackTrace();
					Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
				}

				mOpenCvCameraView.enableView();
			}
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}
	};

	public SnapFace() {
		mDetectorName = new String[2];
		mDetectorName[JAVA_DETECTOR] = "Java";
		mDetectorName[NATIVE_DETECTOR] = "Native (tracking)";

		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	/** Called when the activity is first created. */
	@SuppressLint("ShowToast")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "called onCreate");
		super.onCreate(savedInstanceState);
		
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		mToast.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 0, 0);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_snap_face);

		mOpenCvCameraView = (TrainCameraView) findViewById(R.id.activity_train_camera_view);
		mOpenCvCameraView.setCvCameraViewListener(this);

		mFirstname = getIntent().getStringExtra("firstname");
		mLastname = getIntent().getStringExtra("lastname");
		mPersonId = getIntent().getStringExtra("personid");
		mCurFace = null;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
		
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!OpenCVLoader.initDebug()) {
			Log.d(TAG,
					"Internal OpenCV library not found. Using OpenCV Manager for initialization");
			OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this,
					mLoaderCallback);
		} else {
			Log.d(TAG, "OpenCV library found inside package. Using it!");
			mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
		}
	}

	public void onDestroy() {
		super.onDestroy();
		mOpenCvCameraView.disableView();
	}

	public void onCameraViewStarted(int width, int height) {
		mGray = new Mat();
		mRgba = new Mat();
	}

	public void onCameraViewStopped() {
		mGray.release();
		mRgba.release();
	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

		mRgba = inputFrame.rgba();
		mGray = inputFrame.gray();

		if (mAbsoluteFaceSize == 0) {
			int height = mGray.rows();
			if (Math.round(height * mRelativeFaceSize) > 0) {
				mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
			}
		}

		MatOfRect faces = new MatOfRect();

		if (mJavaDetector != null)
			mJavaDetector.detectMultiScale(mGray, faces, 1.1, 2, 2, new Size(
					mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());

		Rect[] facesArray = faces.toArray();
		Point p = new Point(x, y);
		if (facesArray.length > 0) {
			for (Rect rect : facesArray) {
				if (Utility.roiSizeOk(mRgba, facesArray[0])) {
					// Draws the rectangle tl = top left, br = bottom right
					Imgproc.rectangle(mRgba, facesArray[0].tl(),
							facesArray[0].br(), FACE_RECT_COLOR, 3);
					// If the user clicked on this particular face
					if (rect.contains(p)) {
						mCurFace = new Mat();
						mGray.submat(rect).copyTo(mCurFace);
						picSnapped2();
					}
				}
			}
		}

		// Draw a little circle to test the onTouch stuff
		if (x != -1 && y != -1) {
			Imgproc.circle(mRgba, new Point(x, y), 50, new Scalar(0, 255, 0,
					255));
			x = -1;
			y = -1;
		}

		return mRgba;
	}

	// Checks if roi is smaller than mat
	public static boolean roiSizeOk(Mat mat, Rect roi) {
		if (roi.x >= 0 && roi.y >= 0 && roi.x + roi.width < mat.cols()
				&& roi.y + roi.height < mat.rows()) {
			return true;
		} else {
			return false;
		}
	}

	public void toLibrary(View view) {
		Intent intent = new Intent(this, FacesLibrary.class);
		startActivity(intent);
	}
	
	public void picSnapped2() {
		Log.i(TAG, "Picture taken event");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
				Locale.US);
		String currentDateandTime = sdf.format(new Date());
		String fileName = Environment
				.getExternalStoragePublicDirectory(imgPath) + "/Nosco"
				+ "/" + mPersonId + "-" + currentDateandTime + ".jpg";
		// Resize image to 100x100
		Mat resizedImg = new Mat();
		Size size = new Size(100, 100);
		Imgproc.resize(mCurFace, resizedImg, size);
		Mat claheImg = new Mat();
		// AdaptiveHistogram.doClahe(resizedImg.getNativeObjAddr(),
		// claheImg.getNativeObjAddr());
		Imgproc.equalizeHist(resizedImg, claheImg);
		// Save img to file
		Boolean bool = null;
		bool = Imgcodecs.imwrite(fileName, claheImg);
		if (bool == true) {
			Log.i(TAG, "SUCCESS writing image to external storage");
		} else {
			Log.i(TAG, "Failure writing image to external storage");
		}
		SnapFace.this.runOnUiThread(new Runnable() {
			  @SuppressLint("RtlHardcoded")
			public void run() {
					// Display popup
//					mToast = Toast.makeText(SnapFace.this, "Image of " + mFirstname + " "
//							+ mLastname + " captured", Toast.LENGTH_SHORT);
				  	mToast.setText("Image of " + mFirstname + " "
				  							+ mLastname + " captured");
					mToast.show();
			  }
			});
		// Show the new file in the filesystem
		sendBroadcast(new Intent(
				Intent.ACTION_MEDIA_MOUNTED,
				Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View view, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Utility.setAlpha(view, 0.5f);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			Utility.setAlpha(view, 1f);
		}
		return false;
	}

	public boolean onTouchEvent(MotionEvent event) {
		double rows = mRgba.rows();
		double cols = mRgba.cols();

		double xOffset = (mOpenCvCameraView.getWidth() - cols) / 2;
		double yOffset = (mOpenCvCameraView.getHeight() - rows) / 2;

		x = (double) event.getX() - xOffset;
		y = (double) event.getY() - yOffset;

		return true;
	}

}
