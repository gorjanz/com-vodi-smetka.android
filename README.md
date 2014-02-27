com-vodi-smetka.android
=======================

- Public repository for the "vodi smetka" android application

- This application is used to digitize supermarket receipts printed in macedonian language, so a special tesseract training file was created, if you want to use the tess-two library to recognize a different language, you need to choose a training file from <a href=https://code.google.com/p/tesseract-ocr/downloads/list> Tesseract language training files</a> or build one yourself, for your language by following the instructions at <a href="https://code.google.com/p/tesseract-ocr/wiki/TrainingTesseract3">Training Tesseract</a>


- To be able to run the application and have it working successfully, first you need to set up the tess-two library, as an android library project... the steps you need to follow are listed next:

first things first, I'm developing under windows 8, and using Eclipse IDE, Android SDK, Android NDK and the standard ADT plug-in for eclipse.

If you have all the tools installed, you can proceed to setup the library for development, if not, go get them:

>  - Android SDK - http://developer.android.com/sdk/index.html
>  - Android NDK - http://developer.android.com/tools/sdk/ndk/index.html#Installing
>  - Ant download - http://ant.apache.org/bindownload.cgi
>  - Ant installation - http://ant.apache.org/manual/install.html#setup

 - example tutorial application using this library - <a href="http://gaut.am/making-an-ocr-android-app-using-tesseract/">Simple OCR APP</a>

also, you might want to set the PATH environment variable on your machine, to enable using all of the commands necessary for building the native library, and the way you do that is by: right-click My Computer -> Properties -> Advanced System Settings -> under the Advanced tab, you'll see Environment Variables -> User Variables for <user> and edit the PATH variable, to include all the destination folders for the scripts that you'll be using below.

These are the steps, that I took, which enabled me to use the tesseract library in my project:

 - go to https://github.com/rmtheis/tess-two - it is a fork of the tesseract tools for android, with some additional features.

 - clone the git repository using git bash: 
**git clone git://github.com/rmtheis/tess-two <your directory>**

 - go to the directory holding the files (<your directory>), then go to the "tess-two" folder inside it and issue the command:
**ndk-build** (which will build a .jar file, and .so libraries)

 - if you get successful build, while in the "tess-two" folder, issue the command: **android update project --path .** (which will update the project with your machine specific settings, also, the dot in the command is not there by accident, it means to build the files in the current working directory)

 - then if all is well so far, you issue the command: **ant release**

 - if all is good, you turn on eclipse IDE -> File -> Import... -> (under general category) Existing Project into workspace -> choose the <your directory>\tess-two folder, and let eclipse do it's magic

 - right click the project -> Android Tools -> Fix Project Properties

 - right click the project -> Properties -> Android -> (choose android target to be Android 2.2, and check the check-box "is library")

 - right click the project -> Android Tools -> Add Native Support -> write the name of the .so library you're using, without the prefix "lib" and the extension ".so", in my case (and probably your case - "tess")

 - Create new project to build your own app, (the library project is open in your workspace, and your project is being developed in the same workspace), right-click your project -> Properties -> Android -> (at the bottom) Add.. (meaning to add new library) -> choose the tess-two.jar

 - go trough the sample app referenced above

 - also, go to http://tesseract-ocr.googlecode.com/files/ and download the files needed for OCR in your language, and download them in a folder

 - issue the following commands while in the folder where the training files are:

**adb shell mkdir /mnt/sdcard/tesseract**

**adb shell mkdir /mnt/sdcard/tesseract/tessdata**

**adb push eng.traineddata /mnt/sdcard/tesseract/tessdata**

 which will put the training data on your sd card in your emulator (if you're using a device, put them manually in the same directory (important)

 - when you write the code: `baseAPI.init("the path should end with the parent directory of the tessdata folder`, "language parametar (3 letters, the same ones from the training file)


the last thing I want to share is some sample applications that have been developed using this library
- <a href="http://gaut.am/making-an-ocr-android-app-using-tesseract/">Simple OCR APP</a>
- <a href="http://rmtheis.wordpress.com/2011/08/06/using-tesseract-tools-for-android-to-create-a-basic-ocr-app/">Using Tesseract tools</a>

also some code repositories that will come handy:
- <a href="https://github.com/rmtheis/tess-two">The library itself</a>
- <a href="https://github.com/rmtheis/android-ocr">Experimental app</a>
- <a href="https://github.com/GautamGupta/Simple-Android-OCR">Simple OCR App</a>
 


 
