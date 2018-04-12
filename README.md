# SpeechToText and TextToSpeech Sample App:



<p align="center">
  <img src="https://user-images.githubusercontent.com/30336005/35771314-60d7826a-0933-11e8-9201-2e996eae0ffd.png"/>
</p>

This sample project use RecognizerIntent to make Speec To Text (STT) for Six Langaues (En,Ar,Fr,De,Es,Ru), you could read more about RecognizerIntent from here:

https://developer.android.com/reference/android/speech/RecognizerIntent.html

You could use Google TextToSpeech to convert the result to voice or type your text, you could read more about TextToSpeech from here:

https://developer.android.com/reference/android/speech/tts/TextToSpeech.html

If you need more powerfull solution for SpeechToText, you might use Google Cloud Speech API:

https://cloud.google.com/speech/

##### From my expericne with RecognizerIntent, it give an accurate result in Online Mode if your voice is clear.

In offline it have many cons:
------

1. The result is not accurate.

2. Can't recognize all languases.


##### Google Text Speech doesn't support Arabic language, so you might use library like iSpeech:

https://www.ispeech.org/developers/android

Useful links:
-------

1. [Google Text to Speech](https://en.wikipedia.org/wiki/Google_Text-to-Speech)
2. [Android Hive](https://www.androidhive.info/2014/07/android-speech-to-text-tutorial/)
3. [Table of lable culture names](https://msdn.microsoft.com/en-us/library/ee825488%28v=cs.20%29.aspx?f=255&MSPPError=-2147217396)

