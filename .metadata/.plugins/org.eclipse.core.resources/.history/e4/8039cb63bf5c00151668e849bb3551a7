package com.henrywarhurst.facerecog;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import android.os.Environment;

public class FaceRec2 {
	private FisherSvmJava faceRecognizer;
	private static final String TAG 			= "FaceRec";
	private static final String imgPath 		= Environment.DIRECTORY_PICTURES;
	private List<Integer> seenIds;
	private boolean emptyTrainingSet;
	
	public FaceRec2() {
		faceRecognizer = new FisherSvmJava();
		emptyTrainingSet = false;
	}
	
	public void train() {
		File path = Environment.getExternalStoragePublicDirectory(imgPath);

		FilenameFilter imgFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				name = name.toLowerCase();
				return name.endsWith(".jpg") || name.endsWith(".pgm")
						|| name.endsWith(".png");
			}
		};

		File[] imageFiles = path.listFiles(imgFilter);
		
		if (imageFiles.length == 0) {
			emptyTrainingSet = true;
			return;
		}
		
		ArrayList<Mat> trainingImgs 			= new ArrayList<Mat>();
		ArrayList<Integer> labels				= new ArrayList<Integer>();
		seenIds = new ArrayList<Integer>();
		
		for (int i=0; i<imageFiles.length; ++i) {
			Mat img = Imgcodecs.imread(imageFiles[i].getAbsolutePath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
			int cur_id = Integer
					.parseInt(imageFiles[i].getName().split("\\-")[0]);
			int label = -1;
			// If we have seen this label before
			if (Utility.inArray(cur_id, seenIds)) {
				label = seenIds.indexOf(cur_id);
			// Make a new label
			} else {
				seenIds.add(cur_id);
				label = seenIds.size() - 1;
			}
			trainingImgs.add(img);
			labels.add(label);
		}
		faceRecognizer.train(trainingImgs, labels);
	}
	
	public int numSubjects() {
		return seenIds.size();
	}
	
	public Prediction predict(Mat face) {
		int classNum = seenIds.get(faceRecognizer.predict(face));
		// TODO: Remove this bogus confidence score and replace with the real one.
		Prediction p = new Prediction(classNum, 50);
		return p;
	}
	
	public void release() {
		faceRecognizer.release();
	}
}
