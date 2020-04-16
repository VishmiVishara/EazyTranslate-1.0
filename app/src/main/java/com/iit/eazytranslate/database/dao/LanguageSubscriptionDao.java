package com.iit.eazytranslate.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iit.eazytranslate.database.model.LanguageSubscription;

import java.util.List;

@Dao
public interface LanguageSubscriptionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(LanguageSubscription languageSubscription);

    @Query("SELECT * FROM language_subscription")
    LiveData<List<LanguageSubscription>> getAll();

    @Query("DELETE from language_subscription")
    int deleteAll();
}
