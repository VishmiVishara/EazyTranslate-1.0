package com.iit.eazytranslate.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iit.eazytranslate.R;
import com.iit.eazytranslate.adapter.LanguageSubscriptionAdapter;
import com.iit.eazytranslate.database.model.DisplayLanguage;
import com.iit.eazytranslate.database.model.Language;
import com.iit.eazytranslate.database.model.LanguageSubscription;
import com.iit.eazytranslate.database.viewModel.LanguageSubscriptionViewModel;
import com.iit.eazytranslate.database.viewModel.LanguageViewModel;

import java.util.ArrayList;
import java.util.List;

public class LanguageSubscriptionActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLanguages;
    private List<DisplayLanguage> displayLanguageList = new ArrayList<>();
    private Button btnUpdate;
    private List<Language> languages;
    private LanguageSubscriptionAdapter languageSubscriptionAdapter = null;
    private LanguageViewModel languageViewModel;
    private LanguageSubscriptionViewModel languageSubscriptionViewModel;
    private List<LanguageSubscription> subscriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_subscription);

        setupActivity();
    }

    private void setupActivity(){
        recyclerViewLanguages = findViewById(R.id.recyclerViewLanguages);

        languageViewModel = new ViewModelProvider(this).get(LanguageViewModel.class);
        languageViewModel.getLanguages().observe(this, languageList -> {
            this.languages = languageList;
            for (Language language : languages){
                DisplayLanguage displayLanguage = new DisplayLanguage();
                displayLanguage.setLanguageName(language.getLanguage());
                displayLanguage.setSubscribe(false);
                displayLanguageList.add(displayLanguage);
            }

            languageSubscriptionViewModel = new ViewModelProvider(this).get(LanguageSubscriptionViewModel.class);
            final LiveData<List<LanguageSubscription>>subscribedLanguagesObservable = languageSubscriptionViewModel.getSubscribedLanguages();
            subscribedLanguagesObservable.observe(this, new Observer<List<LanguageSubscription>>() {
                @Override
                public void onChanged(List<LanguageSubscription> subscriptionList) {
                    subscribedLanguagesObservable.removeObserver(this);
                    LanguageSubscriptionActivity.this.subscriptions = subscriptionList;
                    System.out.println(subscriptionList);
                    for(LanguageSubscription languageSubscription : subscriptionList){
                        DisplayLanguage findObj = findObjectFormDisplayList(languageSubscription.getLang_name());
                        if (findObj != null){
                            System.out.println("hello" + findObj.getLanguageName());
                            findObj.setSubscribe(true);

                        }
                    }
                    System.out.println(subscriptionList);
                    languageSubscriptionAdapter = new LanguageSubscriptionAdapter(displayLanguageList);
                    recyclerViewLanguages.setAdapter(languageSubscriptionAdapter);
                }
            });
        });


        recyclerViewLanguages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewLanguages.getContext(), LinearLayoutManager.VERTICAL);
        recyclerViewLanguages.addItemDecoration(dividerItemDecoration);

        initializeUIComponents();
        initializeListeners();

    }

    private void initializeUIComponents() {
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    private void initializeListeners() {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<DisplayLanguage> langList = languageSubscriptionAdapter.getDisplayLanguages();
                languageSubscriptionViewModel.delete();

                for (DisplayLanguage lang : langList) {
                    if(lang.isSubscribe()) {
                        LanguageSubscription languageSubscription = new LanguageSubscription();
                        Language language = findObject(lang.getLanguageName(), false);

                        languageSubscription.setLang_code(language.getLang_code());
                        languageSubscription.setLang_name(lang.getLanguageName());
                        languageSubscriptionViewModel.add(languageSubscription);
                        System.out.println(lang.getLanguageName());
                    }
                }

                languageSubscriptionViewModel.getSubscribedLanguages().observe(LanguageSubscriptionActivity.this, subscriptionList -> {
                    System.out.println(subscriptionList);
                });
                Toast.makeText(LanguageSubscriptionActivity.this, "Updated Subscribed Languages!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private Language findObject(String lang, Boolean isCode) {

        Language foundObject = null;

        for(Language language : languages) {

            if (isCode) {
                if (language.getLang_code().equals(lang)){
                    foundObject = language;
                    break;
                }
            }else {
                if (language.getLanguage().equals(lang)){
                    foundObject = language;
                    break;
                }
            }
            }


        return  foundObject;
    }


    private DisplayLanguage findObjectFormDisplayList(String lang) {

        DisplayLanguage foundObject = null;

        for(DisplayLanguage language : displayLanguageList) {
            if (language.getLanguageName().equals(lang)){
                foundObject = language;
                break;
            }
        }

        return  foundObject;
    }
}
