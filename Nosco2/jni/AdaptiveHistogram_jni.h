/*
 * AdaptiveHistogram_jni.h
 *
 *  Created on: 12 Aug 2015
 *      Author: hwar
 */
#include<jni.h>

#ifndef ADAPTIVEHISTOGRAM_JNI_H_
#define ADAPTIVEHISTOGRAM_JNI_H_

extern "C" {
	JNIEXPORT void JNICALL Java_com_henrywarhurst_facerecog_AdaptiveHistogram_doClahe
						(JNIEnv *, jobject, jlong addrInMat, jlong addrOutMat);
}
#endif /* ADAPTIVEHISTOGRAM_JNI_H_ */
