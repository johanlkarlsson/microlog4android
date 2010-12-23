The microlog4android project brings the microlog logging library to Android. This gives the developer a powerful set of logging tools, such as logging to file or server.

#####################
# PROJECT SETUP
#####################
1. Download source code from github: git clone git@github.com:johanlkarlsson/microlog4android.git
2. In the root directory of the project (microlog4android folder) edit the android-sdk-path.properties file.
   Change the value of the androidSdkPath-property to point to the Android SDK on you local machine.
3. Run "gradle -b build-android_lib.gradle". This will generate modified version of the android.jar file in the lib-folder.
   This task can get an OutOfMemory exception. To fix this simply add set: JAVA_OPTS -Xmx512m
4. Run "gradle eclipse", still from the root project. This will generate the Eclipse project files.
5. Import the other modules into Eclipse as existing projects.  If you see compile errors at @Override tags please set your
Java compiler compliance level to 1.6 (Project->Properties->Java Compiler->Compiler compliance level:)
6. To run a complete build of the microlog4android project simply run "gradle" in the root project.
7. Integration tests are executed in the emulator. Simply run the microlog4android-integration-tests as an Android application from Eclipse.
   Remember to run the "gradle" command in the root project before starting the integration tests.
   The integration-tests project needs to be converted to an Android project after you have imported it into Eclipse.
8. When you have done a complete build of the microlog4android project the jar-file will be located in the build/distributions folder.

If you run any of the gradle commands several times and it is not working as intended, you can try "gradle -C rebuild" to rebuild the gradle cache.
This can be useful if you for example run the build-android_lib.gradle script before remembering to change the path to your local SDK.


#####################
# REQUIREMENTS
#####################
Gradle:
- Installation: http://www.gradle.org/installation.html
- User guide: http://www.gradle.org/0.9-preview-1/docs/userguide/userguide.pdf

Android SDK:
- http://developer.android.com/sdk

Eclipse:
- Android plugin: http://developer.android.com/guide/developing/eclipse-adt.html

Git:
- git-osx-installer: http://code.google.com/p/git-osx-installer/
