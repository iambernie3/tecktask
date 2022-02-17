#include <jni.h>
#include "pokemonlover.h"

#include <string>
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("pokemonlover");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("pokemonlover")
//      }
//    }
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_pokemonlover_apputil_NativePref_getNative1(JNIEnv *env, jobject thiz) {
    std::string url = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=20";
            return env->NewStringUTF(url.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_pokemonlover_apputil_NativePref_getNative2(JNIEnv *env, jobject thiz) {
    std::string url = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=20";
    return env->NewStringUTF(url.c_str());
}