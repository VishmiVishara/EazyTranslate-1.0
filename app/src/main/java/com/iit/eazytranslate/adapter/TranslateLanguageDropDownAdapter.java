package com.iit.eazytranslate.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * One, H. and Kuijpers, P., 2020.
 * How To Make An Android Spinner With Initial Text "Select One"?. [online] Stack Overflow.
 * Available at: <https://stackoverflow.com/questions/867518/how-to-make-an-android-spinner-with-initial-text-select-one>
 * Accessed 9 March 2020].
 */
public class TranslateLanguageDropDownAdapter extends ArrayAdapter<String> {

    public TranslateLanguageDropDownAdapter(Context context, int r, List<String> strings) {
        super(context, r, strings);
    }

    @Override
    public int getCount() {
        // not to display last item since it's the hint
        int count = super.getCount();
//        System.out.println(count);
//        System.out.println(count - 1);
        return count > 0 ? count - 1 : count;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setTextColor(Color.parseColor("#1E88E5"));
        // sent font size
        textView.setTextSize(18);
        return textView;

    }
}

