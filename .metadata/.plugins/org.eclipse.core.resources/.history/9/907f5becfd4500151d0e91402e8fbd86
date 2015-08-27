package com.henrywarhurst.facerecog;

import java.util.List;

import org.opencv.core.Mat;

public class SparseFaceRec {
	private byte [][] A;
	int nTrainingExamples;
	int nFeatures;
	
	public SparseFaceRec(List<Mat> training) {
		nTrainingExamples = training.size();
		Mat temp = training.get(0);
		nFeatures = (int) temp.total()*temp.channels();
		A = new byte[nTrainingExamples][nFeatures];
		for (int i=0; i<nTrainingExamples; ++i) {
			temp = training.get(i);
			// Push current image into data matrix
			temp.get(0, 0, A[i]);
		}
	}
	
}
