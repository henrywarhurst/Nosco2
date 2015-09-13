package com.henrywarhurst.facerecog;

import java.util.ArrayList;
import org.opencv.core.Mat;

public class FisherSvmJava {
	public FisherSvmJava() {
		mNativeObj = nativeCreateObject();
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
		nativeTrain(mNativeObj, trainingImgsAddr, labels);
	}
	
	public int predict(Mat testImg) {
		return nativePredict(mNativeObj, testImg.getNativeObjAddr());
	}
	
	public void release() {
		nativeDestroyObject(mNativeObj);
		mNativeObj = 0;
	}
	
    private long mNativeObj = 0;
    
    
    // TODO: COULD BE AN ISSUE WITH THE STATIC. Static causes JNI to create jclass parameters but we actually want jobject...
    // Hopefully this fixes it. Fingers crossed!
    private native long nativeCreateObject();
    //private native void nativeDestroyObject(long thiz);
    //private native void nativeTrain(long thiz, long[] trainingImgs, ArrayList<Integer> labels);
    //private native int nativePredict(long thiz, long testImg);
}
