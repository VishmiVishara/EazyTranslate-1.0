package com.iit.eazytranslate.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.DbManager;
import com.iit.eazytranslate.database.dao.PhraseDao;
import com.iit.eazytranslate.database.model.Phrase;

import java.util.List;

/**
 * Codelabs.developers.google.com. 2020. Android Fundamentals 10.1 Part A: Room, Livedata, And Viewmodel. [online]
 * Available at: <https://codelabs.developers.google.com/codelabs/android-training-livedata-viewmodel/#0>
 *[Accessed 17 April 2020].
 * */
public class PhraseRepository {
    private PhraseDao phraseDao;

    public PhraseRepository(Application application) {
        phraseDao = DbManager.getDbInstance(application).phraseDao();

    }

    public LiveData<List<Phrase>> getAllPhrases() {
        return  phraseDao.getAll();
    }

    public void add(final Phrase phrase) {
        DbManager.databaseWriteExecutor.execute(() -> {
            phraseDao.save(phrase);
        });
    }

    public LiveData<Phrase> isExistsPhrase(String phrase) {
        return  phraseDao.isExists(phrase);
    }

    public void update(final Phrase phrase) {
        DbManager.databaseWriteExecutor.execute(() -> {
            phraseDao.update(phrase.getPhrase(), phrase.getPid());
        });
    }
}
