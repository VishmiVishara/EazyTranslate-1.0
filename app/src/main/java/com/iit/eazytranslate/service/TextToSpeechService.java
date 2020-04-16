package com.iit.eazytranslate.service;

import android.os.AsyncTask;

import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.text_to_speech.v1.model.Voice;
import com.ibm.watson.text_to_speech.v1.model.Voices;
import com.iit.eazytranslate.util.TextToSpeechImpl;

public class TextToSpeechService {

    private static TextToSpeechService textToSpeechService = new TextToSpeechService();
    private String selectedLanguageCode                     = "";

    public StreamPlayer streamPlayer = new StreamPlayer();
    public TextToSpeechImpl textSpeechServiceImpl;

    private TextToSpeechService() {

    }

    public static TextToSpeechService getTextToSpeechService() {
        return textToSpeechService;
    }

    public void getTranslateResult(String phrase){
        new TextToSpeechTask().execute(phrase);
    }

    public String getSelectedLanguageCode() {
        return selectedLanguageCode;
    }

    public void setSelectedLanguageCode(String selectedLanguageCode) {
        this.selectedLanguageCode = selectedLanguageCode;
    }
}
    class TextToSpeechTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... phrases) {

            try {
//                GetPronunciationOptions getPronunciationOptions =
//                        new GetPronunciationOptions.Builder()
//                                .text(phrases[0])
//                                .voice("ja-JP_EmiV3Voice")
//                                .build();
//
//                Pronunciation pronunciation =
//                        SDKManager.getSdkManager().getTextToSpeechService().getPronunciation(getPronunciationOptions).execute().getResult();
//                System.out.println("pronounce" + pronunciation.getPronunciation());

//                SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder().text( pronunciation.getPronunciation())
//                        .voice(SynthesizeOptions.Voice.EN_US_LISAVOICE)
//                        .accept(HttpMediaType.AUDIO_WAV).build();

                String lauguageCode = TextToSpeechService.getTextToSpeechService().getSelectedLanguageCode();
                String voiceName        = SynthesizeOptions.Voice.EN_US_LISAVOICE;

                Voices voices = SDKManager.getSdkManager().getTextToSpeechService().listVoices().execute().getResult();

                for(Voice voice: voices.getVoices()){
                    String[] code = voice.getLanguage().split("-");

                    if (code.length > 0){
                        if(code[0].equals(lauguageCode)){
                            voiceName = voice.getName();
                            break;
                        }
                    }
                }

                System.out.println(voiceName);
                SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder().text(phrases[0])
                        .voice(voiceName)
                        .accept(HttpMediaType.AUDIO_WAV).build();

                TextToSpeechService.getTextToSpeechService()
                        .streamPlayer.playStream(SDKManager.getSdkManager()
                        .getTextToSpeechService()
                        .synthesize(synthesizeOptions).execute().getResult());

            }catch (Exception e){
                return 0;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (TextToSpeechService.getTextToSpeechService().textSpeechServiceImpl != null)
                TextToSpeechService.getTextToSpeechService().textSpeechServiceImpl.isSpeechDone(integer);
        }
    }

