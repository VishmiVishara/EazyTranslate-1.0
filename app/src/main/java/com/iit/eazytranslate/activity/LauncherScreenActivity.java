package com.iit.eazytranslate.activity;

import android.content.Intent;
import android.os.Bundle;

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

        //User user = DbManager.getAppDatabase(getApplicationContext()).userDao().getLoggedInUser();

        LanguageTranslatorService.getLanguageTranslatorServiceInstance().translatorServiceLoadLanguages = this;
        LanguageTranslatorService.getLanguageTranslatorServiceInstance().getLanguageList();


        Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
        startActivity(intent);
        finish();

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
