package com.iit.eazytranslate.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibm.watson.language_translator.v3.model.Translation;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.iit.eazytranslate.R;
import com.iit.eazytranslate.adapter.TranslateLanguageDropDownAdapter;
import com.iit.eazytranslate.adapter.TranslationAdapter;
import com.iit.eazytranslate.database.model.LangTranslate;
import com.iit.eazytranslate.database.model.LanguageSubscription;
import com.iit.eazytranslate.database.model.Phrase;
import com.iit.eazytranslate.database.model.Translate;
import com.iit.eazytranslate.database.viewModel.LanguageSubscriptionViewModel;
import com.iit.eazytranslate.database.viewModel.PhraseViewModel;
import com.iit.eazytranslate.database.viewModel.TranslationViewModel;
import com.iit.eazytranslate.service.LanguageTranslatorService;
import com.iit.eazytranslate.service.TextToSpeechService;
import com.iit.eazytranslate.util.TextToSpeechImpl;
import com.iit.eazytranslate.util.TranslatorServiceTranslateImpl;
import com.iit.eazytranslate.util.Util;

import java.util.ArrayList;
import java.util.List;

public class TranslateActivity extends AppCompatActivity implements TranslatorServiceTranslateImpl, TextToSpeechImpl {
    private RecyclerView recyclerView;
    private Phrase selectedPhrase;
    private TranslationAdapter translationAdapter;
    private TextView txtSelectedPhrase;
    private TranslateLanguageDropDownAdapter dataAdapter = null;
    private Spinner spinner;
    private Button btnPronounce;
    private Button btnTranslate;
    private TextView translatedPhase;
    private LangTranslate langTranslate = new LangTranslate();
    private List<LanguageSubscription> subscription;
    private CardView selectPhraseCardView;
    private PhraseViewModel phraseViewModel;
    private LanguageSubscriptionViewModel languageSubscriptionViewModel;
    private TranslationViewModel translationViewModel;
    private ConstraintLayout conError;
    private TextView conErrorTxt;
    private Button translateAll;
    private List<Phrase> phraseList;

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            int selectedIndex = bundle.getInt("selected");

            if (phraseList.size() <= selectedIndex)
                return;

            selectedPhrase = phraseList.get(selectedIndex);
            txtSelectedPhrase.setText(selectedPhrase.getPhrase());
            btnTranslate.setVisibility(View.VISIBLE);
            btnPronounce.setVisibility(View.INVISIBLE);
            btnTranslate.setEnabled(true);
            translatedPhase.setText("");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        setupActivity();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("selected_phrase_intent"));

    }

    private void setupActivity() {
        recyclerView = findViewById(R.id.recyclearTranslate);
        txtSelectedPhrase = findViewById(R.id.txtSelectedPhrase);
        spinner = findViewById(R.id.spinnerTranslate);
        btnPronounce = findViewById(R.id.btnPronounce);
        translatedPhase = findViewById(R.id.txtTranslated);
        btnTranslate = findViewById(R.id.btnTranslater);
        selectPhraseCardView = findViewById(R.id.card_viewSelectPhrase);
        conError = findViewById(R.id.conError);
        conErrorTxt = findViewById(R.id.conErrorTxt);
        translateAll = findViewById(R.id.btnTranAll);

        btnPronounce.setVisibility(View.INVISIBLE);
        selectPhraseCardView.setVisibility(View.INVISIBLE);

        phraseViewModel = new ViewModelProvider(this).get(PhraseViewModel.class);
        translationViewModel = new ViewModelProvider(TranslateActivity.this).get(TranslationViewModel.class);

        phraseViewModel.getAllPhrases().observe(this, phrases -> {
            this.phraseList = phrases;
            if (phrases.size() <= 0) {
                conError.setVisibility(View.VISIBLE);
                conErrorTxt.setText("You haven't add any phrases..");
                return;
            }
            translationAdapter = new TranslationAdapter(phrases);
            recyclerView.setAdapter(translationAdapter);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        createDropDownList();
        initListeners();

    }

    private void initListeners() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {

                if (spinner.getSelectedItemPosition() < 0 || spinner.getSelectedItemPosition() >= spinner.getCount())
                    return;
                System.out.println(spinner.getSelectedItemPosition());
                System.out.println(subscription.get(spinner.getSelectedItemPosition()).getLang_code());
                langTranslate.setLang_code(subscription.get(spinner.getSelectedItemPosition()).getLang_code());
                langTranslate.setLanguageName(subscription.get(spinner.getSelectedItemPosition()).getLang_name());
                btnPronounce.setVisibility(View.INVISIBLE);
                btnTranslate.setVisibility(View.VISIBLE);
                btnTranslate.setEnabled(true);
                translatedPhase.setText(null);
                selectPhraseCardView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedPhrase == null || selectedPhrase.getPhrase().equals("")) {
                    Toast.makeText(TranslateActivity.this, "Please select a word or phrase",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                btnTranslate.setEnabled(false);
                langTranslate.setPhrase(selectedPhrase.getPhrase());

                final LiveData<Translate> isAlreadyTranslated = translationViewModel.isAlreadyTranslated(selectedPhrase.getPid(), langTranslate.getLang_code());

                isAlreadyTranslated.observe(TranslateActivity.this, new Observer<Translate>() {
                    @Override
                    public void onChanged(Translate translate) {
                        isAlreadyTranslated.removeObserver(this);

                        if (translate != null) {
                            translatedPhase.setText(translate.getTranslatePhrase());
                            btnTranslate.setVisibility(View.INVISIBLE);
                            btnPronounce.setVisibility(View.VISIBLE);
                            btnTranslate.setEnabled(true);
                        } else {

                            if (!Util.isConnectedToNetwork(TranslateActivity.this)) {
                                Toast.makeText(TranslateActivity.this, "Your internet connection is not available",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                            LanguageTranslatorService.getLanguageTranslatorServiceInstance().translatorServiceTranslate = TranslateActivity.this;
                            LanguageTranslatorService.getLanguageTranslatorServiceInstance().getTranslateResult(langTranslate);
                        }
                    }
                });
            }
        });

        btnPronounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Util.isConnectedToNetwork(TranslateActivity.this)) {
                    Toast.makeText(TranslateActivity.this, "Your internet connection not available",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                btnPronounce.setEnabled(false);
                TextToSpeechService.getTextToSpeechService().textSpeechServiceImpl = TranslateActivity.this;
                TextToSpeechService.getTextToSpeechService().setSelectedLanguageCode(langTranslate.getLang_code());
                try {
                    TextToSpeechService.getTextToSpeechService().getTranslateResult(translatedPhase.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        translateAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TranslateActivity.this, TranslateAllActivity.class);
                intent.putExtra("lan_code", langTranslate.getLang_code());
                intent.putExtra("language_name", langTranslate.getLanguageName());
                startActivity(intent);
            }
        });
    }

    // creating dropdown
    protected void createDropDownList() {

        languageSubscriptionViewModel = new ViewModelProvider(this).get(LanguageSubscriptionViewModel.class);
        final LiveData<List<LanguageSubscription>> subscribedLanguagesObservable = languageSubscriptionViewModel.getSubscribedLanguages();

        subscribedLanguagesObservable.observe(this, new Observer<List<LanguageSubscription>>() {
            @Override
            public void onChanged(List<LanguageSubscription> subscriptionList) {
                subscribedLanguagesObservable.removeObserver(this);
                TranslateActivity.this.subscription = subscriptionList;

                if (subscription.size() <= 0) {
                    conError.setVisibility(View.VISIBLE);
                    conErrorTxt.setText("You Haven't Subscribed any languages..");
                    return;
                }

                List<String> languageNames = new ArrayList<>();

                for (LanguageSubscription languageSubscription : subscriptionList) {
                    languageNames.add(languageSubscription.getLang_name());
                    System.out.println(languageSubscription);
                }

                languageNames.add("Select a language");

                dataAdapter = new TranslateLanguageDropDownAdapter(TranslateActivity.this,
                        android.R.layout.simple_spinner_item, languageNames);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
                spinner.setSelection(dataAdapter.getCount());
            }
        });

    }

    @Override
    public void translateLanguages(TranslationResult translation) {
        btnTranslate.setEnabled(true);
        if (translation != null) {
            System.out.println(translation.getTranslations());
            if (translation.getWordCount() > 0) {
                btnPronounce.setVisibility(View.VISIBLE);
                for (Translation translationResult : translation.getTranslations()) {
                    translatedPhase.setText(translatedPhase.getText() + translationResult.getTranslation());
                    btnTranslate.setVisibility(View.INVISIBLE);
                }

                Translate translate = new Translate();
                translate.setTranslatePhrase(translatedPhase.getText().toString());
                translate.setPid(selectedPhrase.getPid());
                translate.setLanguageCode(langTranslate.getLang_code());
                System.out.println(translate);
                translationViewModel.add(translate);

            } else {
                Toast.makeText(TranslateActivity.this, "Sorry! Translation Failed",
                        Toast.LENGTH_LONG).show();

                btnPronounce.setVisibility(View.INVISIBLE);
            }
        } else {
            Toast.makeText(TranslateActivity.this, "Sorry! Translation Failed",
                    Toast.LENGTH_LONG).show();
            btnPronounce.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void translateAll(LangTranslate value) {

    }

    @Override
    public void isSpeechDone(Integer isSuccess) {
        btnPronounce.setEnabled(true);
    }
}
