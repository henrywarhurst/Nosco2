package com.henrywarhurst.facerecog;

import java.util.List;

import org.opencv.core.Mat;

public class FisherSvmJava {
	public FisherSvmJava() {
		mNativeObj = nativeCreateObject();
	}
	
	public void train(List<Mat> trainingImgs, List<Integer> labels) {
		nativeTrain(mNativeObj, trainingImgs, labels);
	}
	
	public int predict(Mat testImg) {
		return nativePredict(mNativeObj, testImg.getNativeObjAddr());
	}
	
	public void release() {
		nativeDestroyObject(mNativeObj);
		mNativeObj = 0;
	}
	
    private long mNativeObj = 0;
    
    private static native long nativeCreateObject();
    private static native void nativeDestroyObject(long thiz);
    private static native void nativeTrain(long thiz, List<Mat> trainingImgs, List<Integer> labels);
    private static native int nativePredict(long thiz, long testImg);
}