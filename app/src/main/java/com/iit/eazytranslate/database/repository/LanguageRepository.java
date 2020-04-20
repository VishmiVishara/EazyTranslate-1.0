package com.iit.eazytranslate.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.DbManager;
import com.iit.eazytranslate.database.dao.LanguageDao;
import com.iit.eazytranslate.database.model.Language;

import java.util.List;

/**
 * Codelabs.developers.google.com. 2020. Android Fundamentals 10.1 Part A: Room, Livedata, And Viewmodel. [online]
 * Available at: <https://codelabs.developers.google.com/codelabs/android-training-livedata-viewmodel/#0>
 *[Accessed 17 April 2020].
 * */
public class LanguageRepository {

    private LanguageDao languageDao;

    public LanguageRepository(Application application) {
        languageDao = DbManager.getDbInstance(application).languageDao();

    }

    public LiveData<List<Language>> getAllWords() {
        return  languageDao.getAll();
    }

    public void insert(Language language) {
        DbManager.databaseWriteExecutor.execute(() -> {
            languageDao.insert(language);
        });
    }
}
