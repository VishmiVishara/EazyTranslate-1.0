package com.iit.eazytranslate.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.iit.eazytranslate.database.dao.LanguageDao;
import com.iit.eazytranslate.database.dao.LanguageSubscriptionDao;
import com.iit.eazytranslate.database.dao.PhraseDao;
import com.iit.eazytranslate.database.dao.TranslationDao;
import com.iit.eazytranslate.database.model.Language;
import com.iit.eazytranslate.database.model.LanguageSubscription;
import com.iit.eazytranslate.database.model.Phrase;
import com.iit.eazytranslate.database.model.Translate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phrase.class, Language.class, LanguageSubscription.class, Translate.class}, version = 1)
public abstract class DbManager extends RoomDatabase {

    private static DbManager DB_INSTANCE;

    public abstract PhraseDao phraseDao();
    public abstract LanguageDao languageDao();
    public abstract LanguageSubscriptionDao languageSubscriptionDao();
    public abstract TranslationDao translateDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DbManager getDbInstance(Context context) {
        if (DB_INSTANCE == null) {
            DB_INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), DbManager.class, "eazytranslate-database")
                            .build();
        }
        return DB_INSTANCE;
    }

    public static void destroyInstance() {
        DB_INSTANCE = null;
    }
}
