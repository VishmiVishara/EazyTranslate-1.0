package com.iit.eazytranslate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.eazytranslate.R;
import com.iit.eazytranslate.database.model.Phrase;

import java.util.List;

/* https://developer.android.com. 2020. Create A List With Recyclerview.
  * [online] Available at: <https://developer.android.com/guide/topics/ui/layout/recyclerview#customizing>
  *[Accessed 03 April 2020].
  * */
public class DisplayPhrasesAdapter extends RecyclerView.Adapter<DisplayPhrasesAdapter.MyViewHolder>{
    private List<Phrase> phrasesDataset;

    public DisplayPhrasesAdapter(List<Phrase> phrasesDataset) {
        this.phrasesDataset = phrasesDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_display_phrase_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(phrasesDataset.get(position).getPhrase().toString());
    }

    @Override
    public int getItemCount() {
        return phrasesDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.displayPhraseRowText);
        }
    }
}


