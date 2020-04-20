package com.iit.eazytranslate.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.model.LanguageSubscription;
import com.iit.eazytranslate.database.repository.LanguageSubscriptionRepository;

import java.util.List;

/*
 * Codelabs.developers.google.com. 2020. Android Fundamentals 10.1 Part A: Room, Livedata, And Viewmodel. [online]
 * Available at: <https://codelabs.developers.google.com/codelabs/android-training-livedata-viewmodel/#0>
 *[Accessed 17 April 2020].
 * */
public class LanguageSubscriptionViewModel extends AndroidViewModel {

    private LanguageSubscriptionRepository languageSubscriptionRepository;

    public LanguageSubscriptionViewModel(@NonNull Application application) {
        super(application);
        languageSubscriptionRepository = new LanguageSubscriptionRepository(application);
    }

    public LiveData<List<LanguageSubscription>> getSubscribedLanguages() {
        return languageSubscriptionRepository.getAllSubscriptions();
    }

    public void add(List<LanguageSubscription> languageSubscription) {
        languageSubscriptionRepository.add(languageSubscription);
    }
}

