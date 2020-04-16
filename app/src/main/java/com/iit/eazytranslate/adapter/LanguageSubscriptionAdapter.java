package com.iit.eazytranslate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.eazytranslate.R;
import com.iit.eazytranslate.database.model.DisplayLanguage;

import java.util.List;

public class LanguageSubscriptionAdapter extends RecyclerView.Adapter<LanguageSubscriptionAdapter.LanguageViewHolder>  {

    private List<DisplayLanguage> displayLanguages;

    public List<DisplayLanguage> getDisplayLanguages() {
        return displayLanguages;
    }

    public LanguageSubscriptionAdapter(List<DisplayLanguage> displayLanguages) {
        this.displayLanguages = displayLanguages;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_lang_subscription_row, parent, false);

        LanguageSubscriptionAdapter.LanguageViewHolder vh = new LanguageSubscriptionAdapter.LanguageViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final LanguageViewHolder holder, int position) {

        final DisplayLanguage displayLanguage =  displayLanguages.get(position);
        holder.textView.setText(displayLanguages.get(position).getLanguageName());
        holder.checkBoxSubscribe.setTag(position);
        holder.checkBoxSubscribe.setChecked(displayLanguage.isSubscribe());

        holder.checkBoxSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox)v;
                int clickedPos = (Integer) cb.getTag();

                if (holder.checkBoxSubscribe.isChecked()){
                    displayLanguages.get(clickedPos).setSubscribe(true);
                }else {
                    displayLanguages.get(clickedPos).setSubscribe(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return displayLanguages.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }

    public class LanguageViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public CheckBox checkBoxSubscribe;

        public LanguageViewHolder(View v) {
            super(v);
            textView           = v.findViewById(R.id.txtDisplayLanguage);
            checkBoxSubscribe = v.findViewById(R.id.checkBoxSubscribe);

        }


    }
}
