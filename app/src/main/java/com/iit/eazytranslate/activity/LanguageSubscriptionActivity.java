package com.iit.eazytranslate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibm.watson.language_translator.v3.model.IdentifiableLanguage;
import com.ibm.watson.language_translator.v3.model.IdentifiableLanguages;
import com.iit.eazytranslate.R;
import com.iit.eazytranslate.adapter.LanguageSubscriptionAdapter;
import com.iit.eazytranslate.database.model.ViewLanguage;
import com.iit.eazytranslate.database.model.Language;
import com.iit.eazytranslate.database.model.LanguageSubscription;
import com.iit.eazytranslate.database.viewModel.LanguageSubscriptionViewModel;
import com.iit.eazytranslate.database.viewModel.LanguageViewModel;
import com.iit.eazytranslate.service.LanguageTranslatorService;
import com.iit.eazytranslate.util.TranslatorServiceLoadLanguagesImpl;
import com.iit.eazytranslate.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 *  <h1> Language Subscription Activity</h1>
 *  Subscribe Languages
 */
public class LanguageSubscriptionActivity extends AppCompatActivity implements TranslatorServiceLoadLanguagesImpl {

    private static final String TAG = "LanguageSubscription";

    private RecyclerView recyclerViewLanguages;
    private ConstraintLayout layoutError;
    private Button btnDownload;
    private List<ViewLanguage> displayLanguageList = new ArrayList<>();
    private Button btnUpdate;
    private List<Language> languages;
    private LanguageSubscriptionAdapter languageSubscriptionAdapter = null;
    private LanguageViewModel languageViewModel;
    private LanguageSubscriptionViewModel languageSubscriptionViewModel;
    private List<LanguageSubscription> subscriptions = new ArrayList<>();
    private List<Language>languageListDownload = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_subscription);

        setupActivity();
    }

    private void setupActivity(){

        initializeUIComponents();
        initializeListeners();

        layoutError.setVisibility(View.INVISIBLE);

        LanguageTranslatorService.getLanguageTranslatorServiceInstance().translatorServiceLoadLanguages = this;

        Log.v(TAG, "-------------------Loading language List---------------------");
        languageViewModel = new ViewModelProvider(this).get(LanguageViewModel.class);
        languageViewModel.getLanguages().observe(this, languageList -> {
            this.languages = languageList;

            if(languages.size() <= 0){
                Log.v(TAG, "------- Language List is Empty -----");
                layoutError.setVisibility(View.VISIBLE);
                return;
            }

            for (Language language : languages){
                ViewLanguage viewLanguage = new ViewLanguage();
                viewLanguage.setLanguageName(language.getLanguage());
                viewLanguage.setSubscribe(false);
                displayLanguageList.add(viewLanguage);
            }

            Log.v(TAG, "------------------- Loading language Subscriptions ---------------------");
            languageSubscriptionViewModel = new ViewModelProvider(this)
                    .get(LanguageSubscriptionViewModel.class);
            final LiveData<List<LanguageSubscription>>subscribedLanguagesObservable =
                    languageSubscriptionViewModel.getSubscribedLanguages();
            subscribedLanguagesObservable.observe
                    (this, new Observer<List<LanguageSubscription>>() {
                @Override
                public void onChanged(List<LanguageSubscription> subscriptionList) {
                    subscribedLanguagesObservable.removeObserver(this); //removing observer to avoid repeating

                    LanguageSubscriptionActivity.this.subscriptions = subscriptionList;
                    System.out.println(subscriptionList);
                    for(LanguageSubscription languageSubscription : subscriptionList){
                        ViewLanguage findObj = findLangFormDisplayList(languageSubscription.getLang_name());
                        if (findObj != null){
                            Log.v(TAG, "----- Lang Name : " + findObj.getLanguageName());
                            //System.out.println("hello" + findObj.getLanguageName());
                            findObj.setSubscribe(languageSubscription.getSubscribed());

                        }
                    }
                    Log.v(TAG, "----- Subscription List : " + subscriptionList.toString());
                    //System.out.println(subscriptionList);
                    languageSubscriptionAdapter = new LanguageSubscriptionAdapter(displayLanguageList);
                    recyclerViewLanguages.setAdapter(languageSubscriptionAdapter);
                }
            });
        });

        // connect recycler view to the layout manager
        recyclerViewLanguages.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                (recyclerViewLanguages.getContext(), LinearLayoutManager.VERTICAL);
        recyclerViewLanguages.addItemDecoration(dividerItemDecoration);
    }

    // init UI components
    private void initializeUIComponents() {
        btnUpdate = findViewById(R.id.btnUpdate);
        recyclerViewLanguages = findViewById(R.id.recyclerViewLanguages);
        layoutError  = findViewById(R.id.layoutError);
        btnDownload = findViewById(R.id.btnDownload);
    }


    // init listeners
    private void initializeListeners() {

        // updating subscribed languages in the db
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ViewLanguage> langList = languageSubscriptionAdapter.getDisplayLanguages();

                List<LanguageSubscription> languageSubscriptionList = new ArrayList<>();
                for (ViewLanguage lang : langList) {
                    if(lang.isSubscribe()) {
                        LanguageSubscription languageSubscription = new LanguageSubscription();
                        Language language = find(lang.getLanguageName(), false);

                        languageSubscription.setLang_code(language.getLang_code());
                        languageSubscription.setLang_name(lang.getLanguageName());
                        languageSubscription.setSubscribed(lang.isSubscribe());
                        System.out.println(lang.getLanguageName());
                        languageSubscriptionList.add(languageSubscription);
                    }
                }

                languageSubscriptionViewModel.add(languageSubscriptionList);
                Log.v(TAG, "----- Updating Subscribed Languages -----" );
                Toast toast = Toast.makeText(LanguageSubscriptionActivity.this,
                        "Updated Subscribed Languages!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        // btn download listener -  if the language list not available
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(TAG, "----- Checking for internet Connectivity -----");
                if (!Util.isConnectedToNetwork(LanguageSubscriptionActivity.this)){
                    Toast.makeText(LanguageSubscriptionActivity.this,
                            "Internet connection not available.!", Toast.LENGTH_LONG).show();
                    return;
                }
                LanguageTranslatorService.getLanguageTranslatorServiceInstance().getLanguageList();

            }
        });
    }

    /**
     *
     * @param languageList downloaded language list from IBM api
     */
    private void setupDownloadRecycleView(List<Language> languageList){

        this.languages = languageList;
        for (Language language : languages){
            ViewLanguage displayLanguage = new ViewLanguage();
            displayLanguage.setLanguageName(language.getLanguage());
            displayLanguage.setSubscribe(false);
            displayLanguageList.add(displayLanguage);
        }

        layoutError.setVisibility(View.INVISIBLE); //setting language list null error invisible
        languageSubscriptionAdapter = new LanguageSubscriptionAdapter(displayLanguageList);
        recyclerViewLanguages.setAdapter(languageSubscriptionAdapter);

        recyclerViewLanguages.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                (recyclerViewLanguages.getContext(), LinearLayoutManager.VERTICAL);
        recyclerViewLanguages.addItemDecoration(dividerItemDecoration);
    }

    // find languages
    private Language find(String lang, Boolean isCode) {
        Language foundObject = null;
        for (Language language : languages) {

            if (isCode) {
                if (language.getLang_code().equals(lang)) {
                    foundObject = language;
                    break;
                }
            } else {
                if (language.getLanguage().equals(lang)) {
                    foundObject = language;
                    break;
                }
            }
        }
        return  foundObject;
    }

    /**
     *
     * @param lang language name
     * @return Viewlanguage object to display in the view
     */
    private ViewLanguage findLangFormDisplayList(String lang) {
        ViewLanguage foundObject = null;
        for(ViewLanguage language : displayLanguageList) {
            if (language.getLanguageName().equals(lang)){
                foundObject = language;
                break;
            }
        }
        return  foundObject;
    }

    /**
     *
     * @param value IdentifiableLanguages list from IBM api
     */
    @Override
    public void loadLanguages(IdentifiableLanguages value) {
        if (languages == null) {
            Log.v(TAG, "----- Languages list is NULL -----");
            return;
        }
        int index = 0;
        for (IdentifiableLanguage language : value.getLanguages()) {
            Language lang = new Language();
            lang.setLang_code(language.getLanguage());
            lang.setLanguage(language.getName());
            languageViewModel.add(lang);
            languageListDownload.add(lang);
            index++;

            if(index == value.getLanguages().size()){
                setupDownloadRecycleView(languageListDownload);
            }
        }
    }
}
