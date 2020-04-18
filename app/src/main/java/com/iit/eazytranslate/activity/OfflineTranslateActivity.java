package com.iit.eazytranslate.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.iit.eazytranslate.R;
import com.iit.eazytranslate.adapter.OfflineTranslatePhrasesAdapter;
import com.iit.eazytranslate.adapter.TranslateLanguageDropDownAdapter;
import com.iit.eazytranslate.database.model.LangTranslate;
import com.iit.eazytranslate.database.model.LanguageSubscription;
import com.iit.eazytranslate.database.model.OfflineData;
import com.iit.eazytranslate.database.model.Phrase;
import com.iit.eazytranslate.database.viewModel.LanguageSubscriptionViewModel;
import com.iit.eazytranslate.database.viewModel.PhraseViewModel;
import com.iit.eazytranslate.database.viewModel.TranslationViewModel;
import com.iit.eazytranslate.service.LanguageTranslatorService;
import com.iit.eazytranslate.util.TranslatorServiceTranslateImpl;
import com.iit.eazytranslate.util.Util;

import java.util.ArrayList;
import java.util.List;

public class OfflineTranslateActivity extends AppCompatActivity {

    private Spinner spinnerLanguage;
    private TextView txtTransLang;
    private RecyclerView recyclerView;
    private ConstraintLayout conOffline;
    private TextView conOfflineTxt;

    private TranslateLanguageDropDownAdapter dataAdapter = null;
    private List<LanguageSubscription> subscription;
    private LanguageSubscription languageSubscription;
    private LanguageSubscriptionViewModel languageSubscriptionViewModel;
    private PhraseViewModel phraseViewModel;
    private List<Phrase> phraseList;
    private TranslationViewModel translationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_translate);

        setupActivity();
    }

    private void setupActivity() {
        spinnerLanguage = findViewById(R.id.offlinrSpinner);
        txtTransLang = findViewById(R.id.txtTransLang);
        recyclerView = findViewById(R.id.translateAllRecuclar);
        conOfflineTxt = findViewById(R.id.conOfflineTxt);
        conOffline = findViewById(R.id.conOffline);

        translationViewModel = new ViewModelProvider(OfflineTranslateActivity.this)
                .get(TranslationViewModel.class);
        languageSubscriptionViewModel = new ViewModelProvider(this)
                .get(LanguageSubscriptionViewModel.class);
        phraseViewModel = new ViewModelProvider(this).get(PhraseViewModel.class);

        createDropDownList();


    }

    // creating dropdown
    protected void createDropDownList() {

        final LiveData<List<LanguageSubscription>> subscribedLanguagesObservable
                = languageSubscriptionViewModel.getSubscribedLanguages();
        subscribedLanguagesObservable.observe(this, new Observer<List<LanguageSubscription>>() {
            @Override
            public void onChanged(List<LanguageSubscription> subscriptionList) {
                subscribedLanguagesObservable.removeObserver(this);
                OfflineTranslateActivity.this.subscription = subscriptionList;

                if (subscription.size() <= 0) {
                    conOffline.setVisibility(View.VISIBLE);
                    conOfflineTxt.setText("You haven't subscribed any languages");
                    return;
                }

                List<String> languageNames = new ArrayList<>();

                for (LanguageSubscription languageSubscription : subscriptionList) {
                    languageNames.add(languageSubscription.getLang_name());
                    System.out.println(languageSubscription);
                }

                languageNames.add("Select a language");

                dataAdapter = new TranslateLanguageDropDownAdapter(OfflineTranslateActivity.this,
                        android.R.layout.simple_spinner_item, languageNames);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLanguage.setAdapter(dataAdapter);
                spinnerLanguage.setSelection(dataAdapter.getCount());

                spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {

                        if (spinnerLanguage.getSelectedItemPosition() < 0 ||
                                spinnerLanguage.getSelectedItemPosition() >=
                                        spinnerLanguage.getCount())
                            return;
                        System.out.println(spinnerLanguage.getSelectedItemPosition());
                        languageSubscription = subscription.get(spinnerLanguage.getSelectedItemPosition());
                        txtTransLang.setText(subscription.get(spinnerLanguage.getSelectedItemPosition()).getLang_name());
                        translateAllPhrases();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }

    private void translateAllPhrases() {

        final LiveData<List<OfflineData>> translateWordListObservable = translationViewModel
                .getTranslateWord(languageSubscription.getLang_code());
        translateWordListObservable.observe(this, new Observer<List<OfflineData>>() {
            @Override
            public void onChanged(List<OfflineData> offlineData) {
                translateWordListObservable.removeObserver(this);

                LangTranslate languageTranslator = new LangTranslate();

                for (OfflineData offlineData1 : offlineData) {

                    languageTranslator.setPhraseList(offlineData1.getPhrase());
                    languageTranslator.setTranslation(offlineData1.getTranslate_phrase());
                }

                setupTranslationView(languageTranslator);
            }
        });
    }


    private void setupTranslationView(LangTranslate translates) {
        recyclerView = findViewById(R.id.translateAllRecuclar);

        OfflineTranslatePhrasesAdapter translateAllPhrasesAdapter = new OfflineTranslatePhrasesAdapter(translates);
        recyclerView.setAdapter(translateAllPhrasesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}

