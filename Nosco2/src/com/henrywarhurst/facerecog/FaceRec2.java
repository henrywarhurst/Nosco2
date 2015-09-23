package com.henrywarhurst.facerecog;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Locale;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import android.os.Environment;

public class FaceRec2 {
	private FisherSvmJava faceRecognizer;
	private static final String TAG 			= "FaceRec";
	private static final String imgPath 		= Environment.DIRECTORY_PICTURES;
	private ArrayList<Integer> seenIds;
	private boolean emptyTrainingSet;
	private boolean singularTrainingSet;
	
	public FaceRec2() {
		faceRecognizer = new FisherSvmJava();
		emptyTrainingSet = false;
		singularTrainingSet = false;
	}
	
	public void train() {
		File f = new File(Environment.getExternalStoragePublicDirectory(imgPath), "Nosco");
		if (!f.isDirectory()) {
			f.mkdirs();
			emptyTrainingSet = true;
			return;
		}

		FilenameFilter imgFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				name = name.toLowerCase(Locale.ENGLISH);
				return name.endsWith(".jpg") || name.endsWith(".pgm")
						|| name.endsWith(".png");
			}
		};

		File[] imageFiles = f.listFiles(imgFilter);
		
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
		
		// Can't do LDA with only one subject
		if (seenIds.size() == 1) {
			singularTrainingSet = true;
			return;
		}
		faceRecognizer.train(trainingImgs, labels);
	}
	
	public int numSubjects() {
		return seenIds.size();
	}
	
	public ArrayList<Integer> getSeenIds() {
		return seenIds;
	}
	
	public boolean trainingSetEmpty() {
		return emptyTrainingSet;
	}
	
	// Returns true if only one subject in training set
	public boolean isSingularTrainingSet() {
		return singularTrainingSet;
	}
	
	public Prediction predict(Mat face) {
		int classNum = seenIds.get(faceRecognizer.predict(face));
		Prediction p = new Prediction(classNum, -1);
		return p;
	}
	
	public void release() {
		faceRecognizer.release();
	}
}
