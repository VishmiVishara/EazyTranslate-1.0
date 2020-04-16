package com.iit.eazytranslate.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.DbManager;
import com.iit.eazytranslate.database.dao.TranslationDao;
import com.iit.eazytranslate.database.model.LangTranslate;
import com.iit.eazytranslate.database.model.OfflineData;
import com.iit.eazytranslate.database.model.Translate;

import java.util.List;

public class TranslationRepository {
    private TranslationDao translationDao;

    public TranslationRepository(Application application) {
        translationDao = DbManager.getDbInstance(application).translateDao();
    }

    public void add(Translate translate) {
        DbManager.databaseWriteExecutor.execute(() -> {
            translationDao.add(translate);
        });
    }

    public LiveData<List<Translate>> getAllTranslations() {
        return  translationDao.getAll();
    }

    public LiveData<Translate> isAlreadyTranslated(int pid, String lang_code) {
        return  translationDao.isAlreadyTranslated(pid, lang_code);
    }

    public LiveData<List<OfflineData>>getTranslateWord(String lang_code) {
        return translationDao.getTranslateWord(lang_code);
    }
}
