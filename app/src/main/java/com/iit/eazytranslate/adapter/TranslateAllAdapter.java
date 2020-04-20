package com.iit.eazytranslate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.eazytranslate.R;
import com.iit.eazytranslate.database.model.LangTranslate;
import com.iit.eazytranslate.util.TranslateAllCellTap;


/** https://developer.android.com. 2020. Create A List With Recyclerview.
 * [online] Available at: <https://developer.android.com/guide/topics/ui/layout/recyclerview#customizing>
 *[Accessed 03 April 2020].
 * */
public class TranslateAllAdapter extends RecyclerView.Adapter<TranslateAllAdapter.MyViewHolder>{
    private LangTranslate phrasesList;
    private TranslateAllCellTap translateAllCellTap;
    public TranslateAllAdapter(LangTranslate phrasesDataset, TranslateAllCellTap translateAllCellTap) {
        this.phrasesList = phrasesDataset;
        this.translateAllCellTap = translateAllCellTap;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_translate_all_row_new, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewTranslate.setText(phrasesList.getTranslations().get(position));
        holder.textViewPhrase.setText(phrasesList.getPhraseList().get(position));

        holder.btnPronounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateAllCellTap.cellTap(phrasesList.getTranslations().get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return phrasesList.getPhraseList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textViewTranslate;
        public TextView textViewPhrase;
        public ImageButton btnPronounce;


        public MyViewHolder(View v) {
            super(v);
            textViewTranslate = v.findViewById(R.id.txtTransAll);
            textViewPhrase = v.findViewById(R.id.txtPhraseTransAll);
            btnPronounce = v.findViewById(R.id.btnPronounce);
        }
    }
}


