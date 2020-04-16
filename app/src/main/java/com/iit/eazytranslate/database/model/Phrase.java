package com.iit.eazytranslate.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "phrase")
public class Phrase {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "pid")
    public int pid;

    @ColumnInfo(name = "phrase")
    public String phrase;
    @Ignore
    private Boolean isSelected;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "pid=" + pid +
                ", phrase='" + phrase + '\'' +
                '}';
    }
}