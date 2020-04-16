package com.iit.eazytranslate.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "language")
public class Language {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "lang_code")
    private String lang_code;

    @ColumnInfo(name = "language")
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLang_code() {
        return lang_code;
    }

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    @Override
    public String toString() {
        return "Language{" +
                "lang_code='" + lang_code + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
