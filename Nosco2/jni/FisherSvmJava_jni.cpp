// ------- User Defined ---------- //
#include <fisher_svm.h>
// ------- Built-in -------------- //
#include <vector>
#include <jni.h>
// ------- OpenCV Includes ------- //
#include <opencv2/core/core.hpp>
#include <opencv2/ml/ml.hpp>

using namespace cv;

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jlong JNICALL Java_com_henrywarhurst_facerecog_FisherSvmJava_nativeConstruct
  (JNIEnv * jenv, jobject)
{
    jlong obj_ptr = (jlong) new FisherSvm();
    return obj_ptr;
}

JNIEXPORT void JNICALL Java_com_henrywarhurst_facerecog_FisherSvmJava_nativeDestroy
  (JNIEnv * jenv, jobject, jlong thiz)
{
	// Clean up garbage
	if (thiz != 0)
		delete (FisherSvm*) thiz;
}

JNIEXPORT void JNICALL Java_com_henrywarhurst_facerecog_FisherSvmJava_nativeTrain
  (JNIEnv * jenv, jobject, jlong thiz, jlongArray training_imgs_addr, jobject labels)
{
	// Class objects
	jclass ArrayList_class 			= jenv->FindClass("java/util/ArrayList");
	jclass Integer_class			= jenv->FindClass("java/lang/Integer");
	// Method objects
	jmethodID Get_method 			= jenv->GetMethodID(ArrayList_class, "get", "(I)Ljava/lang/Object;");
	jmethodID Size_method			= jenv->GetMethodID(ArrayList_class, "size", "()I");
	jmethodID IntValue_method		= jenv->GetMethodID(Integer_class, "intValue", "()I");
	// Convert the ArrayList<Integer> to a std::vector<int>
	int n_labels 					= jenv->CallIntMethod(labels, Size_method);
	std::vector<int> labels_vec;
	jvalue args;
	for (int i=0; i<n_labels; ++i) {
		args.i = i;
		jobject cur_val_obj = jenv->CallObjectMethodA(labels, Get_method, &args);
		jint cur_val = jenv->CallIntMethod(cur_val_obj, IntValue_method, &args);
		labels_vec.push_back(cur_val);
	}
	// Extract the training images
	std::vector<Mat> training_imgs;
	jsize n_examples = jenv->GetArrayLength(training_imgs_addr);
	jlong *training_data = jenv->GetLongArrayElements(training_imgs_addr, 0);

	for (int i=0; i<n_examples; ++i) {
		Mat &new_image = *(Mat*) training_data[i];
		training_imgs.push_back(new_image);
	}
	jenv->ReleaseLongArrayElements(training_imgs_addr, training_data, 0);

	// ------------------- Train model ------------------------------ //
	((FisherSvm*)thiz)->train(training_imgs, labels_vec, -1);
}

JNIEXPORT jint JNICALL Java_com_henrywarhurst_facerecog_FisherSvmJava_nativePredict
  (JNIEnv * jenv, jobject, jlong thiz, jlong test_img_addr)
{
	// test_img is simply a mat, just have to extract it
	Mat& test_img = *(Mat*)test_img_addr;
	// Predict and return
	jint result = (jint) ((FisherSvm*) thiz)->predict(test_img);
	return result;
}

#ifdef __cplusplus
}
#endif
