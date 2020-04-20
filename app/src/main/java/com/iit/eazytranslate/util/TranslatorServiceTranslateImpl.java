package com.iit.eazytranslate.util;

import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.iit.eazytranslate.database.model.LangTranslate;
/**
 * Joseph, J., 2020. How To Pass Values From Recycleadapter To Mainactivity Or Other Activities.
 * [online] Stack Overflow.
 * Available at: <https://stackoverflow.com/questions/35008860/how-to-pass-values-from-recycleadapter-to-mainactivity-or-other-activities>
 * [Accessed 4 April 2020].
 * */
public interface TranslatorServiceTranslateImpl {
    void translateLanguages(TranslationResult value);
    void translateAll(LangTranslate value);
}
