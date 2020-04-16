package com.iit.eazytranslate.util;

import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.iit.eazytranslate.database.model.LangTranslate;

public interface TranslatorServiceTranslateImpl {
    void translateLanguages(TranslationResult value);
    void translateAll(LangTranslate value);
}
