package com.iit.eazytranslate.service;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.iit.eazytranslate.App;
import com.iit.eazytranslate.R;

class SDKManager {
    private static SDKManager sdkManager = new SDKManager();

    private LanguageTranslator languageTranslatorService;
    private TextToSpeech textToSpeechService;

    private SDKManager() {
        setLanguageTranslatorService();
        setTextToSpeech();
    }

    protected static SDKManager getSdkManager() {
        return sdkManager;
    }

    public TextToSpeech getTextToSpeechService() {
        return textToSpeechService;
    }

    protected LanguageTranslator getLanguageTranslatorService() {
        return languageTranslatorService;
    }

    private void setLanguageTranslatorService() {
        IamAuthenticator authenticator = new IamAuthenticator(App.getAppInstance().getResources().getString(R.string.language_translator_apikey));
        LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", authenticator);
        languageTranslator.setServiceUrl(App.getAppInstance().getResources().getString(R.string.language_translator_url));

        this.languageTranslatorService = languageTranslator;
    }

    private void setTextToSpeech() {
        IamAuthenticator authenticator = new IamAuthenticator(App.getAppInstance().getResources().getString(R.string.text_speech_iam_apikey));
        TextToSpeech textToSpeech = new TextToSpeech(authenticator);
        textToSpeech.setServiceUrl(App.getAppInstance().getResources().getString(R.string.text_speech_url));
        this.textToSpeechService = textToSpeech;
    }
}

