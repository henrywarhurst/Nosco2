#include <AdaptiveHistogram_jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/opencv.hpp>
#include <opencv2/face.hpp>

using namespace cv;
using namespace cv::face;

JNIEXPORT void JNICALL Java_com_henrywarhurst_facerecog_AdaptiveHistogram_doClahe
					(JNIEnv *, jobject, jlong addrInMat, jlong addrOutMat)
{
	Mat& inMat = *(Mat*)addrInMat;
	Mat& outMat = *(Mat*)addrOutMat;
	Ptr<CLAHE> clahe = createCLAHE();
	clahe->setClipLimit(8.0);
	clahe->setTilesGridSize(Size(30.0,30.0));
	clahe->apply(inMat, outMat);
	clahe->collectGarbage();
}

