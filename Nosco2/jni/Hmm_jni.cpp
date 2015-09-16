#include <Hmm_jni.h>
#include <custom_hmm.h>

JNIEXPORT jlong JNICALL Java_com_henrywarhurst_facerecog_Hmm_nativeConstruct
(JNIEnv *, jobject, jint n_subjects)
{
    jlong obj_ptr = (jlong) new custom_hmm((int) n_subjects);
    return obj_ptr;
}

JNIEXPORT void JNICALL Java_com_henrywarhurst_facerecog_Hmm_nativeDestroy
(JNIEnv *, jobject, jlong thiz)
{
    if (thiz != 0)
        delete (custom_hmm*) thiz;
}

JNIEXPORT jint JNICALL Java_com_henrywarhurst_facerecog_Hmm_getSequenceMode
(JNIEnv * jenv, jobject, jlong thiz, jintArray sequence)
{
    jsize len = jenv->GetArrayLength(sequence);
    jint *body = jenv->GetIntArrayElements(sequence, 0);
    jint mode = ((custom_hmm*) thiz)->get_likely_subject(body, len);
    return mode;
}