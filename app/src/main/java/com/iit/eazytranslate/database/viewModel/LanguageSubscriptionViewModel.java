package com.iit.eazytranslate.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.model.LanguageSubscription;
import com.iit.eazytranslate.database.repository.LanguageSubscriptionRepository;

import java.util.List;

public class LanguageSubscriptionViewModel extends AndroidViewModel {

    private LanguageSubscriptionRepository languageSubscriptionRepository;

    public LanguageSubscriptionViewModel(@NonNull Application application) {
        super(application);
        languageSubscriptionRepository = new LanguageSubscriptionRepository(application);
    }

    public LiveData<List<LanguageSubscription>> getSubscribedLanguages() {
        return languageSubscriptionRepository.getAllSubscriptions();
    }

    public void add(LanguageSubscription languageSubscription) {
        languageSubscriptionRepository.insert(languageSubscription);
    }

    public void delete() {
        languageSubscriptionRepository.delete();
    }
}

