package com.henrywarhurst.facerecog;

import java.util.ArrayList;

public class Hmm {
	// Note num_subjects includes 'unknown class'
	public Hmm (int num_subjects) {
		mHandle = nativeConstruct(num_subjects);
	}
	
	public int get_likely_subject(ArrayList<Integer> sequence) {
		int[] sequence_arr = Utility.convertIntegers(sequence);
		return getSequenceMode(mHandle, sequence_arr);
	}
	
	public void release() {
		nativeDestroy(mHandle);
		mHandle = 0;
	}
	
	private long mHandle = 0;
	private native long nativeConstruct(int num_subjects);
	private native void nativeDestroy(long thiz);
	private native int getSequenceMode(long thiz, int[] sequence);
}
