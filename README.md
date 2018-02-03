# SpeechToText-Sample-App:



<p align="center">
  <img src="https://user-images.githubusercontent.com/30336005/35769519-f0365cb6-0914-11e8-93d2-d170225779b9.png"/>
</p>

This sample project use RecognizerIntent to make Speec To Text (STT) for Six Langaues (En,Ar,Fr,De,Es,Ru), you could read more about RecognizerIntent from here:

https://developer.android.com/reference/android/speech/RecognizerIntent.html

Then The result could be coverted to Speech using TextToSpeech native android support library, you could read more about TextToSpeech from here:

https://developer.android.com/reference/android/speech/tts/TextToSpeech.html

If you need more powerfull solution for SpeechToText, you might use Google Cloud Speech API:

https://cloud.google.com/speech/

##### From my expericne with RecognizerIntent, it give a very accurate result in Online Mode.

In offline it have many cons:
------

1. The result is not accurate.

2. Can't recognize all languases.


##### Google Text Speech doesn't support Arabic language, so you might use library like iSpeech:

https://www.ispeech.org/developers/android

Useful links:
-------
1. Table of lable culture name:
https://msdn.microsoft.com/en-us/library/ee825488%28v=cs.20%29.aspx?f=255&MSPPError=-2147217396
2. Google Text to Speech:
https://en.wikipedia.org/wiki/Google_Text-to-Speech

