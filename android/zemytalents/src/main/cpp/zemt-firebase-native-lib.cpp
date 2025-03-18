#include <jni.h>
#include <string>

#define ENVIRONMENT_DEV "DEV"
#define ENVIRONMENT_QA "QA"
#define ENVIRONMENT_PROD "PROD"

#define FIREBASE_INSTANCE "surveys-firebase-dev"
#define FIREBASE_DEV "{\"api_key\":\"AIzaSyC7sV__n8g3SeWo5gaakdaPcS-xZYkUxmo\",\"database_url\":\"https://zeusdev-cbbe5.firebaseio.com\",\"project_id\":\"zeusdev-cbbe5\",\"storage_bucket\":\"zeusdev-cbbe5.appspot.com\",\"gcm_sender_id\":\"859143222740\",\"application_id\":\"1:859143222740:android:48faeba8107b8246\"}"
#define FIREBASE_QA ""
#define FIREBASE_PROD "{\"api_key\":\"AIzaSyB4f1gUyfFYwRMC1k1bMaHIOgqQHJ_WTM4\",\"database_url\":\"https://zeus-prod.firebaseio.com\",\"project_id\":\"zeus-prod\",\"storage_bucket\":\"zeus-prod.appspot.com\",\"gcm_sender_id\":\"850954746796\",\"application_id\":\"1:850954746796:android:48faeba8107b8246\"}"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_upax_zemytalents_common_ZEMTEnvironment_getFirebaseName(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(FIREBASE_INSTANCE);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_upax_zemytalents_common_ZEMTEnvironment_getFirebaseParamsConfig(JNIEnv *env,
                                                                         jobject /* this */,
                                                                         jstring input) {
    const char *inputStr = env->GetStringUTFChars(input, nullptr);
    std::string environment(inputStr);

    std::string baseUrl;
    if (environment == ENVIRONMENT_DEV) {
        baseUrl = FIREBASE_DEV;
    } else if (environment == ENVIRONMENT_QA) {
        baseUrl = FIREBASE_QA;
    } else {
        baseUrl = FIREBASE_PROD;
    }
    return env->NewStringUTF(baseUrl.c_str());
}