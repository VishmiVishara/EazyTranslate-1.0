package com.iit.eazytranslate.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "translate", foreignKeys = {
        @ForeignKey(
                entity = Language.class,
                parentColumns = "lang_code",
                childColumns = "lang_code",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Phrase.class,
                parentColumns = "pid",
                childColumns = "pid",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class Translate {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "transId")
    private int transId;

    @ColumnInfo(name = "pid")
    private int pid;

    @ColumnInfo(name = "translate_phrase")
    private String translatePhrase;


    @ColumnInfo(name = "lang_code")
    private String languageCode;

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTranslatePhrase() {
        return translatePhrase;
    }

    public void setTranslatePhrase(String translatePhrase) {
        this.translatePhrase = translatePhrase;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public String toString() {
        return "Translate{" +
                "transId=" + transId +
                ", pid=" + pid +
                ", translatePhrase='" + translatePhrase + '\'' +
                ", languageCode='" + languageCode + '\'' +
                '}';
    }
}
