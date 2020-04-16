package com.iit.eazytranslate.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iit.eazytranslate.database.model.Language;

import java.util.List;

@Dao
public interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Language language);

    @Query("SELECT * FROM language ")
    LiveData<List<Language>> getAll();
}
