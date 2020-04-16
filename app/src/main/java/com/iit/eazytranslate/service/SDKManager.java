package com.iit.eazytranslate.service;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;

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
        IamAuthenticator authenticator = new IamAuthenticator("AugRWKS03xGiOyzcAct7Imih_St5_HSfCHpxr6dBheYb");
        LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", authenticator);
        languageTranslator.setServiceUrl("https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/448cfd45-e056-400b-ab49-86a8c14b08ae");
        this.languageTranslatorService = languageTranslator;
    }

    private void setTextToSpeech() {
        IamAuthenticator authenticator = new IamAuthenticator("5Hu7i33tNz4xBdwrqSXWsh1CnAC0Udk0Y29cSJD26KIW");
        TextToSpeech textToSpeech = new TextToSpeech(authenticator);
        textToSpeech.setServiceUrl("https://api.eu-gb.text-to-speech.watson.cloud.ibm.com/instances/b730efe1-da48-42b1-8312-105600c6b51a");
        this.textToSpeechService = textToSpeech;
    }
}

