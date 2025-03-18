#include <jni.h>
#include <string>

#define APP_DEFAULT_FIREBASE_CONFIG "{\"api_key\":\"AIzaSyBL1eJd8crC2BqjO-JCegAtGXSFyGcX774\",\"database_url\":\"https://zeus-externos-dev.firebaseio.com\",\"project_id\":\"zeus-externos-dev\",\"storage_bucket\":\"zeus-externos-dev.appspot.com\",\"gcm_sender_id\":\"445168811485\",\"application_id\":\"1:445168811485:android:eeef1d05ca1c55b0254a34\"}"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_awesomeproject_ZEAppEnvironment_getAppFirebaseConfig(JNIEnv *env, jobject thiz) {
    std::string config = APP_DEFAULT_FIREBASE_CONFIG;
    return env->NewStringUTF(config.c_str());
}