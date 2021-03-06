package com.henrywarhurst.facerecog;

import java.util.ArrayList;
import org.opencv.core.Mat;

public class FisherSvmJava {
	public FisherSvmJava() {
		mHandle = nativeConstruct();
	}
	
	public void release() {
		nativeDestroy(mHandle);
		mHandle = 0;
	}

	public void train(ArrayList<Mat> trainingImgs, ArrayList<Integer> labels) {
		// First we convert the trainingImgs list to a long array of
		// native addresses.
		int nExamples = trainingImgs.size();
		long[] trainingImgsAddr = new long[nExamples];
		for (int i=0; i<nExamples; ++i) {
			Mat tempAddr = trainingImgs.get(i);
			trainingImgsAddr[i] = tempAddr.getNativeObjAddr();
		}
		nativeTrain(mHandle, trainingImgsAddr, labels);
	}

	public int predict(Mat testImg) {
		return nativePredict(mHandle, testImg.getNativeObjAddr());
	}
	
	private long mHandle = 0;
	private native long nativeConstruct();
	private native void nativeDestroy(long thiz);
	private native void nativeTrain(long thiz, long[] trainingImgs, ArrayList<Integer> labels);
	private native int nativePredict(long thiz, long testImg);
}
