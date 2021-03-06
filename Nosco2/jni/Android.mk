LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

#OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on
#OPENCV_LIB_TYPE:=SHARED
include /Users/hwar/Documents/UniWork/2015/info3911/OpenCV3-android-sdk/sdk/native/jni/OpenCV.mk

LOCAL_SRC_FILES  := DetectionBasedTracker_jni.cpp
LOCAL_C_INCLUDES += $(LOCAL_PATH)
LOCAL_LDLIBS     += -llog -ldl
LOCAL_MODULE     := detection_based_tracker

include $(BUILD_SHARED_LIBRARY)

# --------- CLAHE STUFF ----------
# --------------------------------

include $(CLEAR_VARS)

include /Users/hwar/Documents/UniWork/2015/info3911/OpenCV3-android-sdk/sdk/native/jni/OpenCV.mk

LOCAL_SRC_FILES  := AdaptiveHistogram_jni.cpp
#LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)
LOCAL_LDLIBS     += -llog -ldl
LOCAL_MODULE     := adaptive_histogram

include $(BUILD_SHARED_LIBRARY)

# -------- FACEREC STUFF ---------
# --------------------------------

include $(CLEAR_VARS)

include /Users/hwar/Documents/UniWork/2015/info3911/OpenCV3-android-sdk/sdk/native/jni/OpenCV.mk

LOCAL_SRC_FILES  := FisherSvmJava_jni.cpp fisher_svm.cpp
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)
LOCAL_LDLIBS     += -llog -ldl
LOCAL_MODULE     := face_recog_native

include $(BUILD_SHARED_LIBRARY)

# ---------- HMM STUFF -----------
# --------------------------------

include $(CLEAR_VARS)

include /Users/hwar/Documents/UniWork/2015/info3911/OpenCV3-android-sdk/sdk/native/jni/OpenCV.mk

LOCAL_SRC_FILES  := Hmm_jni.cpp custom_hmm.cpp
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)
LOCAL_LDLIBS     += -llog -ldl
LOCAL_MODULE     := hmm_native

include $(BUILD_SHARED_LIBRARY)

# -------- JAVACV STUFFF ---------
# --------------------------------

#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_calib3d
#LOCAL_SRC_FILES := libjniopencv_calib3d.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_contrib
#LOCAL_SRC_FILES := libjniopencv_contrib.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_core
#LOCAL_SRC_FILES := libjniopencv_core.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_features2d
#LOCAL_SRC_FILES := libjniopencv_features2d.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_flann
#LOCAL_SRC_FILES := libjniopencv_flann.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_highgui
#LOCAL_SRC_FILES := libjniopencv_highgui.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_imgproc
#LOCAL_SRC_FILES := libjniopencv_imgproc.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_legacy
#LOCAL_SRC_FILES := libjniopencv_legacy.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_ml
#LOCAL_SRC_FILES := libjniopencv_ml.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_nonfree
#LOCAL_SRC_FILES := libjniopencv_nonfree.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_objdetect
#LOCAL_SRC_FILES := libjniopencv_objdetect.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_photo
#LOCAL_SRC_FILES := libjniopencv_photo.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_video
#LOCAL_SRC_FILES := libjniopencv_video.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libjniopencv_videostab
#LOCAL_SRC_FILES := libjniopencv_videostab.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_calib3d
#LOCAL_SRC_FILES := libopencv_calib3d.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_contrib
#LOCAL_SRC_FILES := libopencv_contrib.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_core
#LOCAL_SRC_FILES := libopencv_core.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_features2d
#LOCAL_SRC_FILES := libopencv_features2d.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_flann
#LOCAL_SRC_FILES := libopencv_flann.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_gpu
#LOCAL_SRC_FILES := libopencv_gpu.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_highgui
#LOCAL_SRC_FILES := libopencv_highgui.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_imgproc
#LOCAL_SRC_FILES := libopencv_imgproc.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_legacy
#LOCAL_SRC_FILES := libopencv_legacy.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_ml
#LOCAL_SRC_FILES := libopencv_ml.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_nonfree
#LOCAL_SRC_FILES := libopencv_nonfree.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_objdetect
#LOCAL_SRC_FILES := libopencv_objdetect.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_photo
#LOCAL_SRC_FILES := libopencv_photo.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libopencv_video
#LOCAL_SRC_FILES := libopencv_video.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libpostproc
#LOCAL_SRC_FILES := libpostproc.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := libtbb
#LOCAL_SRC_FILES := libtbb.so
#include $(PREBUILT_SHARED_LIBRARY)
