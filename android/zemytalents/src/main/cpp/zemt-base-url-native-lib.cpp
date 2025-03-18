#include <jni.h>
#include <string>

#define ENVIRONMENT_DEV "DEV"
#define ENVIRONMENT_QA "QA"
#define ENVIRONMENT_PROD "PROD"

#define MY_TALENTS_BASE_URL_DEV "https://ptk65jxc50.execute-api.us-east-1.amazonaws.com/v1/"
#define MY_TALENTS_BASE_URL_QA "https://api-comunicacion.talentozeus-qa.com.mx/"
#define MY_TALENTS_BASE_URL_PROD "https://api-comunicacion.talentozeus.com/"

#define ZEUS_BASE_URL_DEV "https://api.talentozeus-dev.com.mx/"
#define ZEUS_BASE_URL_QA "https://api.talentozeus-qa.com.mx/"
#define ZEUS_BASE_URL_PROD "https://api.talentozeus.com/"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_upax_zemytalents_common_ZEMTEnvironment_getCustomTalentsUrl(JNIEnv *env,
                                                                     jobject /* this */,
                                                                     jstring input,
                                                                     jboolean useAlternativeUrl) {
    const char *inputStr = env->GetStringUTFChars(input, nullptr);
    std::string environment(inputStr);

    std::string baseUrl;
    if (environment == ENVIRONMENT_DEV) {
        if (useAlternativeUrl) {
            baseUrl = ZEUS_BASE_URL_DEV;
        } else {
            baseUrl = MY_TALENTS_BASE_URL_DEV;
        }
    } else if (environment == ENVIRONMENT_QA) {
        if (useAlternativeUrl) {
            baseUrl = ZEUS_BASE_URL_QA;
        } else {
            baseUrl = MY_TALENTS_BASE_URL_QA;
        }
    } else {
        if (useAlternativeUrl) {
            baseUrl = ZEUS_BASE_URL_PROD;
        } else {
            baseUrl = MY_TALENTS_BASE_URL_PROD;
        }
    }
    return env->NewStringUTF(baseUrl.c_str());
}