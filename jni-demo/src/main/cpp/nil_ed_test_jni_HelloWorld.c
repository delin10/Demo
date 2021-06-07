#include "nil_ed_test_jni_HelloWorld.h"

#ifdef __cplusplus
extern "C" {
#endif
// gcc -shared -include /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/include nil_ed_test_jni_HelloWorld.c -o test.so
jstring Java_nil_ed_test_jni_HelloWorld_hello(JNIEnv *env, jobject this_ref, jobject dateTime) {
    jclass dateTimeFormatterClass = env->FindClass("java/time/format/DateTimeFormatter");
    jmethodID ofPattern = env->GetStaticMethodID(dateTimeFormatterClass, "ofPattern", "(Ljava/lang/String;)Ljava/time/format/DateTimeFormatter;");
    printf("%d", ofPattern);
    jstring localPtn = env->NewStringUTF("yyyy-MM-dd");
    jobject formatter = env->CallStaticObjectMethod(dateTimeFormatterClass, ofPattern, localPtn);
    // 下面这种调用formatter为NULL
    // jobject formatter = env->CallStaticObjectMethod(dateTimeFormatterClass, ofPattern, "yyyy-MM-dd");
    jclass localDateTimeClass = env->FindClass("java/time/LocalDateTime");
    jmethodID formatMid = env->GetMethodID(dateTimeFormatterClass, "format", "(Ljava/time/temporal/TemporalAccessor;)Ljava/lang/String;");
    if (formatter == NULL) {
        printf("NULL");
    }
    jstring result = (jstring) env->CallObjectMethod(formatter, formatMid, dateTime);
    env->DeleteLocalRef(formatter);
    env->DeleteLocalRef(dateTimeFormatterClass);
    env->DeleteLocalRef(localDateTimeClass);
    return result;
}
#ifdef __cplusplus
}
#endif