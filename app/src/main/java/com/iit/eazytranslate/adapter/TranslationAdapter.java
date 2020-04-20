package com.iit.eazytranslate.adapter;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.eazytranslate.R;
import com.iit.eazytranslate.database.model.Phrase;

import java.util.ArrayList;
import java.util.List;

/** https://developer.android.com. 2020. Create A List With Recyclerview.
 * [online] Available at: <https://developer.android.com/guide/topics/ui/layout/recyclerview#customizing>
 *[Accessed 03 April 2020].
 * */
public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.MyViewHolder> {
    private List<Phrase> phrasesDataset;
    private List<View> viewArrayList = new ArrayList<>();

    private AdapterView.OnItemClickListener onItemClickListener;
    private int selectedPosition = -1;

    public TranslationAdapter(List<Phrase> phrasesDataset) {
        this.phrasesDataset = phrasesDataset;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public Phrase getSelectedPhrase() {
        System.out.println(selectedPosition + " #######");
        return phrasesDataset.get(selectedPosition);
    }


    @NonNull
    @Override
    public TranslationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_translate_phrase_row, parent, false);

        TranslationAdapter.MyViewHolder vh = new TranslationAdapter.MyViewHolder(v, this);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull TranslationAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(phrasesDataset.get(position).getPhrase());

        if (!viewArrayList.contains(holder.itemView)) {
            viewArrayList.add(holder.itemView);
        }

    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemHolderClick(TranslationAdapter.MyViewHolder holder) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, holder.itemView,
                    holder.getAdapterPosition(), holder.getItemId());
        }
 
    }

    @Override
    public int getItemCount() {
        return phrasesDataset.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView textView;
        private TranslationAdapter mAdapter;
        public View viwBackground;

        public MyViewHolder(View v, TranslationAdapter adapter) {
            super(v);
            mAdapter = adapter;
            itemView.setOnClickListener(this);
            this.viwBackground = v;
            textView = v.findViewById(R.id.translatePhraseRowText);

        }

        /**
         * Joseph, J., 2020. How To Pass Values From Recycleadapter To Mainactivity Or Other Activities.
         * [online] Stack Overflow.
         * Available at: <https://stackoverflow.com/questions/35008860/how-to-pass-values-from-recycleadapter-to-mainactivity-or-other-activities>
         * [Accessed 4 April 2020].
         * */
        @Override
        public void onClick(View v) {
            selectedPosition = getAdapterPosition();
            for(View viw : viewArrayList){
                viw.setBackground(new ColorDrawable(0xffffffff));
            }
            viwBackground.setBackground(new ColorDrawable(0xffb9f4f6));
            mAdapter.onItemHolderClick(TranslationAdapter.MyViewHolder.this);
            Intent intent = new Intent("selected_phrase_intent");
            intent.putExtra("selected_index", selectedPosition);
            System.out.println(phrasesDataset.get(selectedPosition) );
            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);

        }
    }
}

