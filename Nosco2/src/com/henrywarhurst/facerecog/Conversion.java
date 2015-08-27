package com.henrywarhurst.facerecog;

import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

import java.util.List;

import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.opencv.android.Utils;

import android.graphics.Bitmap;

public class Conversion {
	// ***************** Utility Functions **********************
	// Credit to Github: ayuso2013
	// **********************************************************
	public static Mat MatToJavaCvMat(org.opencv.core.Mat m) {
		Bitmap bmp = Bitmap.createBitmap(m.width(), m.height(),
				Bitmap.Config.ARGB_8888);

		Utils.matToBitmap(m, bmp);
		return bitmapToJavaCvMat(bmp, m.width(), m.height());
	}
	
	public static org.opencv.core.Mat JavaCvMatToMat(Mat m) {
		Bitmap bmp = Bitmap.createBitmap(m.arrayWidth(), m.arrayHeight(),
				Bitmap.Config.ARGB_8888);
		bmp.copyPixelsFromBuffer(m.getByteBuffer());
		org.opencv.core.Mat outMat = new org.opencv.core.Mat();
		Utils.bitmapToMat(bmp, outMat);
		return outMat;
	}
	
	public static Mat bitmapToJavaCvMat(Bitmap bmp, int width, int height) {
		if ((width != -1) || (height != -1)) {
			Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, width, height, false);
			bmp = bmp2;
		}
		
		IplImage image = IplImage.create(bmp.getWidth(), bmp.getHeight(),
				IPL_DEPTH_8U, 4);

		bmp.copyPixelsToBuffer(image.getByteBuffer());
		IplImage grayImg = IplImage.create(image.width(), image.height(),
				IPL_DEPTH_8U, 1);

		cvCvtColor(image, grayImg, opencv_imgproc.CV_BGR2GRAY);
		Mat imgMat = new Mat(grayImg, true);
		return imgMat;
	}
}
