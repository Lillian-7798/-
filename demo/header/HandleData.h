#pragma once
#include<map>
#include <iostream>
#include "com_example_demo_Rule.h"
using namespace std;

bool HandleData(JNIEnv* env, jobject para, map<string, double> &m) {
    if (para == NULL) {
        std::cout << "para is NULL" << std::endl;
        return false;
    }
    // get key set of map
    jclass jmapclass = env->FindClass("java/util/HashMap");
    jmethodID jgetmid = env->GetMethodID(jmapclass, "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
    jmethodID jkeysetmid = env->GetMethodID(jmapclass, "keySet", "()Ljava/util/Set;");
//   std::cout << "keyset id : " << jkeysetmid << std::endl;
    jobject jsetkey = env->CallObjectMethod(para, jkeysetmid);

    // convert the set to array
    jclass jsetclass = env->FindClass("java/util/Set");
    jmethodID jtoArraymid = env->GetMethodID(jsetclass, "toArray", "()[Ljava/lang/Object;");
    jobjectArray jobjArray = (jobjectArray)env->CallObjectMethod(jsetkey, jtoArraymid);
    if (jobjArray == NULL) {
        std::cout << "error: jobjArray" << std::endl;
        return false;
    }
    jsize arraysize = env->GetArrayLength(jobjArray);

    
    // walk through array, get every value
    string key = "";
    double value = 0;
    for (int i = 0; i < arraysize; i++) {
        jobject jkeyobject = env->GetObjectArrayElement(jobjArray, i);
        jstring jkeystring = static_cast<jstring>(jkeyobject);
        const char* str = env->GetStringUTFChars(jkeystring, 0);
        std::cout << "key " << i << ": " << str << std::endl;
        env->ReleaseStringUTFChars(jkeystring, str);

        jobject jvalueobject = (jobject)env->CallObjectMethod(para, jgetmid, jkeyobject);
        jclass jdoubleclass = env->FindClass("java/lang/Double");
        jmethodID jdoubleId = env->GetMethodID(jdoubleclass, "doubleValue", "()D");

        jdouble jvaluedouble = (jdouble)env->CallDoubleMethod(jvalueobject, jdoubleId);
        std::cout << "value " << i << ": " << jvaluedouble << std::endl;

        key = str;
        value = (double)jvaluedouble;
        m[key] = value;
    }
    return true;

}