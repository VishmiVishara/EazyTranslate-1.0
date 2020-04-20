package com.iit.eazytranslate.service;

import android.os.AsyncTask;
import android.util.Log;

import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.text_to_speech.v1.model.Voice;
import com.ibm.watson.text_to_speech.v1.model.Voices;
import com.iit.eazytranslate.util.TextToSpeechImpl;

/* Cloud.ibm.com. 2020. Text To Speech - IBM Cloud API Docs. [online]
 * Available at: <https://cloud.ibm.com/apidocs/text-to-speech/text-to-speech?code=java#get-pronunciation>
 * [Accessed 20 April 2020].
 */
public class TextToSpeechService {

    // creating singleton object
    static TextToSpeechService textToSpeechService = new TextToSpeechService();
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

        private static final String TAG = "TextToSpeechTask";

        /**
         * @param phrases phrase to speak
         * @return 0 or 1 ti indicate success or false
         */
        @Override
        protected Integer doInBackground(String... phrases) {

            try {
//                 //get pronunciation
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

                String selectedLanguageCode = TextToSpeechService.getTextToSpeechService().getSelectedLanguageCode();
                String voiceName = SynthesizeOptions.Voice.EN_US_LISAVOICE;

                /*
                 * Cloud.ibm.com. 2020. Text To Speech - IBM Cloud API Docs. [online]
                 * Available at: <https://cloud.ibm.com/apidocs/text-to-speech/text-to-speech?code=java#list-voices>
                 [Accessed 20 April 2020].
                 * */
                // get list of voice models
                Voices voices = SDKManager.getSdkManager().getTextToSpeechService().listVoices().execute().getResult();

                // select voice model according to the language
                for(Voice voice: voices.getVoices()){
                    String[] code = voice.getLanguage().split("-");

                    if (code.length > 0){
                        if(code[0].equals(selectedLanguageCode)){
                            voiceName = voice.getName(); // set voice model if voice model is available for that model
                            break;
                        }
                    }
                }

                Log.v(TAG, "------------------- Voice model : " + voiceName );
                //System.out.println(voiceName);
                SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder().text(phrases[0])
                        .voice(voiceName)
                        .accept(HttpMediaType.AUDIO_WAV).build();

                TextToSpeechService.getTextToSpeechService()
                        .streamPlayer.playStream(SDKManager.getSdkManager()
                        .getTextToSpeechService()
                        .synthesize(synthesizeOptions).execute().getResult());

            }catch (Exception e){
                // log error
                Log.e(TAG, "[ERROR] " + e.getMessage());
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

