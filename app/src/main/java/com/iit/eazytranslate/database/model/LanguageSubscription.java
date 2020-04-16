package com.iit.eazytranslate.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "language_subscription")
public class LanguageSubscription {

    @PrimaryKey
    @ColumnInfo(name = "lang_code")
    @NonNull
    private String lang_code;

    @ColumnInfo(name = "lang_name")
    private String lang_name;

    @NonNull
    public String getLang_code() {
        return lang_code;
    }

    public void setLang_code(@NonNull String lang_code) {
        this.lang_code = lang_code;
    }

    public String getLang_name() {
        return lang_name;
    }

    public void setLang_name(String lang_name) {
        this.lang_name = lang_name;
    }

    @Override
    public String toString() {
        return "LanguageSubscription{" +
                "lang_code='" + lang_code + '\'' +
                ", lang_name='" + lang_name + '\'' +
                '}';
    }
}
