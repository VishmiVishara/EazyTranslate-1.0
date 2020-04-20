package com.iit.eazytranslate.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iit.eazytranslate.database.model.OfflineData;
import com.iit.eazytranslate.database.model.Translate;

import java.util.List;

@Dao
public interface TranslationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(Translate translate);

    @Query("SELECT * FROM translate")
    LiveData<List<Translate>> getAll();

    @Query("SELECT * FROM translate where pid = :pid AND lang_code = :lang_code ")
    LiveData<Translate> isAlreadyTranslated(int pid, String lang_code);

    @Query("SELECT * from translate INNER JOIN phrase on translate.pid = phrase.pid WHERE lang_code = :lang_code ")
    LiveData<List<OfflineData>>getTranslateWord(String lang_code);

    @Query("DELETE FROM translate WHERE pid= :pid")
    void deleteById(int pid);
}
