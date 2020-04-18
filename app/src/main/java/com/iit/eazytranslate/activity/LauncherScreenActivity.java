package com.iit.eazytranslate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ibm.watson.language_translator.v3.model.IdentifiableLanguage;
import com.ibm.watson.language_translator.v3.model.IdentifiableLanguages;
import com.iit.eazytranslate.R;
import com.iit.eazytranslate.database.model.Language;
import com.iit.eazytranslate.database.viewModel.LanguageViewModel;
import com.iit.eazytranslate.service.LanguageTranslatorService;
import com.iit.eazytranslate.util.TranslatorServiceLoadLanguagesImpl;

public class LauncherScreenActivity extends AppCompatActivity implements TranslatorServiceLoadLanguagesImpl {

    private LanguageViewModel languageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_screen);

        languageViewModel = new ViewModelProvider(this).get(LanguageViewModel.class);

        LanguageTranslatorService.getLanguageTranslatorServiceInstance().translatorServiceLoadLanguages = this;
        LanguageTranslatorService.getLanguageTranslatorServiceInstance().getLanguageList();

        int secondsDelayed = 4;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
                startActivity(intent);
                finish();
            }
        }, secondsDelayed * 1000);
    }

    @Override
    public void loadLanguages(IdentifiableLanguages languages) {

        if (languages == null)
            return;

        for (IdentifiableLanguage language : languages.getLanguages()) {
            Language lang = new Language();
            lang.setLang_code(language.getLanguage());
            lang.setLanguage(language.getName());
            languageViewModel.add(lang);
        }


    }

}
