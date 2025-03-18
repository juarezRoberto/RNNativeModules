#include <jni.h>
#include <string>

std::string getEnvironment() {
    return "DEV";
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_upax_zcinterceptor_environment_ZCIEnvironment_getAppEnvironment(JNIEnv *env,
                                                                         jobject thiz) {
    return env->NewStringUTF(getEnvironment().c_str());
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_upax_zcinterceptor_environment_ZCIEnvironment_encryptDatabase(JNIEnv *env, jobject thiz) {
    return JNI_FALSE;
}

// Encryption flag for SessionInfo database
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_upax_zcsessioninfo_common_ZCSIEnvironment_encryptDatabase(JNIEnv *env, jobject thiz) {
    return JNI_FALSE;
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_awesomeproject_ZEAppEnvironment_getSomething(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(getEnvironment().c_str());
}