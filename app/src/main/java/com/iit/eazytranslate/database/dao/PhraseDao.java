package com.iit.eazytranslate.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.iit.eazytranslate.database.model.Phrase;

import java.util.List;

@Dao
public interface PhraseDao {
    @Insert
    long save(Phrase phrase);

    @Query("SELECT * FROM phrase WHERE phrase = :phrase")
    LiveData<Phrase> isExists(String phrase);

    @Query("SELECT * FROM phrase ORDER BY phrase ASC")
    LiveData<List<Phrase>> getAll();

    @Query("UPDATE phrase SET phrase = :newPhrase WHERE phrase.pid = :pid")
    void update(String newPhrase, int pid );

}
