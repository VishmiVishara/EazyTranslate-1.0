package com.iit.eazytranslate.database.model;

import java.util.ArrayList;
import java.util.List;

// Lang Translate
public class LangTranslate {
    private String lang_code;
    private String phrase;
    private String languageName;
    private int index;
    private List<String> translation  = new ArrayList<>();
    private int pid;
    private List<String> phraseList = new ArrayList<>();
    private List<Integer> pidList = new ArrayList<>();

    public List<Integer> getPidList() {
        return pidList;
    }

    public void setPidList(int index) {
        this.pidList.add(index);
    }

    public List<String> getPhraseList() {
        return phraseList;
    }

    public void setPhraseList(String phrase) {
        this.phraseList.add(phrase);
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getTranslations() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation.add(translation);
    }

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

    public String getLang_code() {
        return lang_code;
    }

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    @Override
    public String toString() {
        return "LangTranslate{" +
                "lang_code='" + lang_code + '\'' +
                ", phrase='" + phrase + '\'' +
                '}';
    }
}
