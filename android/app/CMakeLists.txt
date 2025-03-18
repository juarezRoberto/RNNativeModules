# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)
project(cmake_secureapi)

set(ENVIRONMENT_LIBRARY "ze-environment-native-lib" CACHE STRING "constant for environment-lib")
set(BASE_URL_LIBRARY "ze-base-url-native-lib" CACHE STRING "constant for base-url-lib")
set(FIREBASE_LIBRARY "ze-firebase-native-lib" CACHE STRING "constant for firebase-lib")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        ${ENVIRONMENT_LIBRARY}

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/${VERSION}/cpp/${ENVIRONMENT_LIBRARY}.cpp)

add_library( # Sets the name of the library.
        ${BASE_URL_LIBRARY}

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/${VERSION}/cpp/${BASE_URL_LIBRARY}.cpp)

add_library( # Sets the name of the library.
        ${FIREBASE_LIBRARY}

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/${VERSION}/cpp/${FIREBASE_LIBRARY}.cpp)


# Set the header files directory.
include_directories(src/${VERSION}/cpp/include/)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

find_library( # Sets the name of the path variable.
        ${ENVIRONMENT_LIBRARY}

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

find_library( # Sets the name of the path variable.
        ${BASE_URL_LIBRARY}

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

find_library( # Sets the name of the path variable.
        ${FIREBASE_LIBRARY}

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        ${ENVIRONMENT_LIBRARY}
        ${BASE_URL_LIBRARY}
        ${FIREBASE_LIBRARY}
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})
