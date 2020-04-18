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

    @Query("SELECT * FROM language_subscription where isSubscribed=1")
    LiveData<List<LanguageSubscription>> getSubscribedLanguages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(List<LanguageSubscription> languageSubscriptionList);
}
