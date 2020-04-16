package com.iit.eazytranslate.service;

import android.os.AsyncTask;

import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.IdentifiableLanguages;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.Translation;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.iit.eazytranslate.database.model.LangTranslate;
import com.iit.eazytranslate.database.model.Translate;
import com.iit.eazytranslate.util.TranslatorServiceLoadLanguagesImpl;
import com.iit.eazytranslate.util.TranslatorServiceTranslateImpl;

public class LanguageTranslatorService {

    private static LanguageTranslatorService languageTranslatorServiceInstance = new LanguageTranslatorService();

    public TranslatorServiceLoadLanguagesImpl translatorServiceLoadLanguages;

    public TranslatorServiceTranslateImpl translatorServiceTranslate;

    private LanguageTranslatorService() {

    }

    public static LanguageTranslatorService getLanguageTranslatorServiceInstance() {
        return languageTranslatorServiceInstance;
    }

    public void getLanguageList() {
        new LanguageListTask().execute();
    }

    public void getTranslateResult(LangTranslate langTranslate ){
        new LanguageTranslateTask().execute(langTranslate);
    }

    public void translateAll(LangTranslate langTranslate){
        new LanguageTranslateListTask().execute(langTranslate);
    }
}

class LanguageListTask extends AsyncTask<Void, Void, IdentifiableLanguages> {
    protected IdentifiableLanguages doInBackground(Void... urls) {

        IdentifiableLanguages languages = null;
        try {
            LanguageTranslator languageTranslator = SDKManager.getSdkManager().getLanguageTranslatorService();
            languages = languageTranslator.listIdentifiableLanguages()
                    .execute().getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return languages;
    }

    protected void onPostExecute(IdentifiableLanguages result) {

        if (LanguageTranslatorService.getLanguageTranslatorServiceInstance().translatorServiceLoadLanguages != null) {
            LanguageTranslatorService.getLanguageTranslatorServiceInstance().translatorServiceLoadLanguages.loadLanguages(result);
        }
    }
}

class LanguageTranslateTask extends AsyncTask<LangTranslate, Integer, TranslationResult> {

    @Override
    protected TranslationResult doInBackground(LangTranslate... langTranslate) {

        TranslationResult translationResult = null;

        TranslateOptions translateOptions = new TranslateOptions.Builder()
                .addText(langTranslate[0].getPhrase())
                .source("en").target(langTranslate[0].getLang_code())
                .build();
        try {
            translationResult = SDKManager.getSdkManager().getLanguageTranslatorService().translate(translateOptions).execute().getResult();
            System.out.println(langTranslate[0].getLang_code() + " Lang ");
            System.out.println(langTranslate[0].getPhrase() + " Phrase ");

        }
        catch (Exception e) {
          e.printStackTrace();
        }

        return translationResult;
    }

    @Override
    protected void onPostExecute(TranslationResult result) {
        super.onPostExecute(result);
        System.out.println(result);
            LanguageTranslatorService.getLanguageTranslatorServiceInstance().translatorServiceTranslate.translateLanguages(result);

    }
}

class LanguageTranslateListTask extends AsyncTask<LangTranslate, Integer, LangTranslate> {

    @Override
    protected LangTranslate doInBackground(LangTranslate... langTranslates) {
        try {
            TranslationResult translationResult = null;

            TranslateOptions translateOptions = new TranslateOptions
                                                            .Builder()
                                                            .text(langTranslates[0].getPhraseList())
                                                            .source("en")
                                                            .target(langTranslates[0].getLang_code())
                                                            .build();

            translationResult = SDKManager.getSdkManager().getLanguageTranslatorService().translate(translateOptions).execute().getResult();
            if (translationResult != null) {
                if (translationResult.getTranslations().size() > 0) {
                    for (Translation translation : translationResult.getTranslations()) {
                        langTranslates[0].setTranslation(translation.getTranslation());
                    }
                }
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return langTranslates[0];
    }

    @Override
    protected void onPostExecute(LangTranslate translate) {
        super.onPostExecute(translate);
        LanguageTranslatorService.getLanguageTranslatorServiceInstance().translatorServiceTranslate.translateAll(translate);
    }
}

