#include <FisherSvmJava_jni.h>

#include <fisher_svm.h>

#include <opencv2/core/core.hpp>

JNIEXPORT jlong JNICALL Java_com_henrywarhurst_facerecog_FisherSvmJava_nativeCreateObject
  (JNIEnv *, jclass)
{
	result = new FisherSvm();
	return result;
}

JNIEXPORT void JNICALL Java_com_henrywarhurst_facerecog_FisherSvmJava_nativeDestroyObject
  (JNIEnv * jenv, jclass, jlong thiz)
{
	// Clean up garbage
	if (thiz != 0)
		delete (FisherSvm*) thiz;
}


JNIEXPORT void JNICALL Java_com_henrywarhurst_facerecog_FisherSvmJava_nativeTrain
  (JNIEnv * jenv, jclass, jlong thiz, jobject training_imgs, jobject labels)
{
	// Extract the training images and labels from 'trainingImgs' and 'labels'
}

JNIEXPORT jint JNICALL Java_com_henrywarhurst_facerecog_FisherSvmJava_nativePredict
  (JNIEnv *, jclass, jlong thiz, jlong test_img)
{

}