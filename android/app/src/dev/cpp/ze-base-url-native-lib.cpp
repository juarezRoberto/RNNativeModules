#include <jni.h>
#include <string>

#include "ze-environment-native-lib.cpp"

#define ZEUS_BASE_URL "https://ptk65jxc50.execute-api.us-east-1.amazonaws.com/v1/"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_upax_zcinterceptor_environment_ZCIEnvironment_getBaseUrl(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(ZEUS_BASE_URL);
}