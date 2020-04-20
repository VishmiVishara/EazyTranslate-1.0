package com.iit.eazytranslate.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.DbManager;
import com.iit.eazytranslate.database.dao.LanguageSubscriptionDao;
import com.iit.eazytranslate.database.model.LanguageSubscription;

import java.util.List;

/*
 * Codelabs.developers.google.com. 2020. Android Fundamentals 10.1 Part A: Room, Livedata, And Viewmodel. [online]
 * Available at: <https://codelabs.developers.google.com/codelabs/android-training-livedata-viewmodel/#0>
 *[Accessed 17 April 2020].
 * */
public class LanguageSubscriptionRepository {
    private LanguageSubscriptionDao languageSubscriptionDao;

    public LanguageSubscriptionRepository(Application application) {
        languageSubscriptionDao = DbManager.getDbInstance(application).languageSubscriptionDao();
    }

    public LiveData<List<LanguageSubscription>> getAllSubscriptions() {
        return  languageSubscriptionDao.getSubscribedLanguages();
    }

    public void add(List<LanguageSubscription> languageSubscription) {
        DbManager.databaseWriteExecutor.execute(() -> {
            languageSubscriptionDao.add(languageSubscription);
        });
    }

}
