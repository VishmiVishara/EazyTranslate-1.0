package com.iit.eazytranslate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
import com.iit.eazytranslate.adapter.TranslateAllAdapter;
import com.iit.eazytranslate.database.model.LangTranslate;
import com.iit.eazytranslate.database.model.Phrase;
import com.iit.eazytranslate.database.model.Translate;
import com.iit.eazytranslate.database.viewModel.PhraseViewModel;
import com.iit.eazytranslate.database.viewModel.TranslationViewModel;
import com.iit.eazytranslate.service.LanguageTranslatorService;
import com.iit.eazytranslate.service.TextToSpeechService;
import com.iit.eazytranslate.util.TextToSpeechImpl;
import com.iit.eazytranslate.util.TranslateAllCellTap;
import com.iit.eazytranslate.util.TranslatorServiceTranslateImpl;

import java.util.List;

/*
 * Activity for Translate all saved words /phrases
 */
public class TranslateAllActivity extends AppCompatActivity implements
        TranslateAllCellTap, TextToSpeechImpl, TranslatorServiceTranslateImpl {

    private static final String TAG = "TranslateAll";

    private Spinner spinnerLanguage;
    private TextView txtTransLang;
    private RecyclerView recyclerView;
    private ConstraintLayout conOffline;
    private TextView conOfflineTxt;

    private PhraseViewModel phraseViewModel;
    private List<Phrase> phraseList;
    private TranslationViewModel translationViewModel;
    private String languageCode;
    private String languageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_all);

        setupActivity();
    }

    private void setupActivity() {
        txtTransLang = findViewById(R.id.txtTransAll);

        Bundle bundle = getIntent().getExtras();
        languageCode = bundle.getString("lan_code");
        languageName = bundle.getString("language_name");

        txtTransLang.setText(languageName);
        translationViewModel = new ViewModelProvider(TranslateAllActivity.this).get(TranslationViewModel.class);
        phraseViewModel = new ViewModelProvider(this).get(PhraseViewModel.class);
        TextToSpeechService.getTextToSpeechService().textSpeechServiceImpl = this;
        LanguageTranslatorService.getLanguageTranslatorServiceInstance().translatorServiceTranslate = this;


        translateAllPhrases();

    }

    private void translateAllPhrases() {

        Log.v(TAG, "----------------  Getting phrases list --------------- ");

        final LiveData<List<Phrase>> phrasesListObservable = phraseViewModel.getAllPhrases();
        phrasesListObservable.observe(this, new Observer<List<Phrase>>() {
            @Override
            public void onChanged(List<Phrase> phrases) {
                phrasesListObservable.removeObserver(this);
                phraseList = phrases;

                if (phraseList.size() <= 0) {
                    conOffline.setVisibility(View.VISIBLE);
                    conOfflineTxt.setText("You haven't add any words/phrases");
                    return;
                }


                LangTranslate langTranslate = new LangTranslate();
                langTranslate.setLang_code(languageCode);
                for (int i = 0; i < phraseList.size(); i++) {
                    langTranslate.setPhraseList(phrases.get(i).getPhrase());
                    langTranslate.setPidList(phrases.get(i).getPid());
                }

                // calling translate all service
                LanguageTranslatorService.getLanguageTranslatorServiceInstance().translateAll(langTranslate);
            }
        });
    }

    private void setupTranslationView(LangTranslate translates) {
        recyclerView = findViewById(R.id.recyclarViewTransAll);

        for (int i = 0; i < translates.getTranslations().size(); i++) {

            Translate translate = new Translate();
            translate.setTranslatePhrase(translates.getTranslations().get(i));
            translate.setPid(translates.getPidList().get(i));
            translate.setLanguageCode(languageCode);

            final LiveData<Translate> isAlreadyTranslated = translationViewModel.isAlreadyTranslated(translate.getPid(), translates.getLang_code());

            isAlreadyTranslated.observe(TranslateAllActivity.this, new Observer<Translate>() {
                @Override
                public void onChanged(Translate isTranslate) {
                    isAlreadyTranslated.removeObserver(this);

                    if (isTranslate == null) {
                        System.out.println(translate);
                        translationViewModel.add(translate);
                    }
                }
            });
        }

        //setting recycler view
        TranslateAllAdapter translateAllPhrasesAdapter = new TranslateAllAdapter(translates, this);
        recyclerView.setAdapter(translateAllPhrasesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    /**
     *
     * @param word string phrase / word
     */
    @Override
    public void cellTap(String word) {
        TextToSpeechService.getTextToSpeechService().setSelectedLanguageCode(languageCode);
        TextToSpeechService.getTextToSpeechService().getTranslateResult(word);
    }

    @Override
    public void isSpeechDone(Integer isSuccess) {

    }

    @Override
    public void translateLanguages(TranslationResult value) {

    }

    /**
     *
     * @param value langTranslate
     */
    @Override
    public void translateAll(LangTranslate value) {
        Log.v(TAG, "------- Result : "+  value.getTranslations() );
        System.out.println(value.getTranslations());
        if (value.getTranslations().size() > 0) {
            setupTranslationView(value);
        } else {
            value.getPhraseList().clear();
            setupTranslationView(value);
            Toast toast = Toast.makeText(TranslateAllActivity.this, "Sorry! Translation Failed",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
