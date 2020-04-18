package com.iit.eazytranslate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.eazytranslate.R;
import com.iit.eazytranslate.database.model.LangTranslate;


//https://developer.android.com/guide/topics/ui/layout/recyclerview#customizing
public class OfflineTranslatePhrasesAdapter extends RecyclerView.Adapter<OfflineTranslatePhrasesAdapter.MyViewHolder>{
    private LangTranslate phrasesList;

    public OfflineTranslatePhrasesAdapter(LangTranslate phrasesDataset) {
        this.phrasesList = phrasesDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_translate_all_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewTranslate.setText(phrasesList.getTranslations().get(position));
        holder.textViewPhrase.setText(phrasesList.getPhraseList().get(position));
    }

    @Override
    public int getItemCount() {
        return phrasesList.getPhraseList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textViewTranslate;
        public TextView textViewPhrase;


        public MyViewHolder(View v) {
            super(v);
            textViewTranslate = v.findViewById(R.id.translate);
            textViewPhrase = v.findViewById(R.id.phrase);
        }
    }
}


