package com.iit.eazytranslate.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.DbManager;
import com.iit.eazytranslate.database.dao.LanguageSubscriptionDao;
import com.iit.eazytranslate.database.model.LanguageSubscription;

import java.util.List;

public class LanguageSubscriptionRepository {
    private LanguageSubscriptionDao languageSubscriptionDao;

    public LanguageSubscriptionRepository(Application application) {
        languageSubscriptionDao = DbManager.getDbInstance(application).languageSubscriptionDao();
    }

    public LiveData<List<LanguageSubscription>> getAllSubscriptions() {
        return  languageSubscriptionDao.getAll();
    }

    public void insert(LanguageSubscription languageSubscription) {
        DbManager.databaseWriteExecutor.execute(() -> {
            languageSubscriptionDao.insert(languageSubscription);
        });
    }

    public void delete() {
        DbManager.databaseWriteExecutor.execute(() -> {
            languageSubscriptionDao.deleteAll();
        });
    }
}
