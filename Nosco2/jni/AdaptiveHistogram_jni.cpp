#include <AdaptiveHistogram_jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/opencv.hpp>
//#include <fisher_svm.h>

using namespace cv;

JNIEXPORT void JNICALL Java_com_henrywarhurst_facerecog_AdaptiveHistogram_doClahe
					(JNIEnv *, jobject, jlong addrInMat, jlong addrOutMat)
{
    //FisherSvm fsvm;
	Mat& inMat = *(Mat*)addrInMat;
	Mat& outMat = *(Mat*)addrOutMat;
	Ptr<CLAHE> clahe = createCLAHE();
	clahe->setClipLimit(8.0);
	clahe->setTilesGridSize(Size(30.0,30.0));
	clahe->apply(inMat, outMat);
	clahe->collectGarbage();
}

