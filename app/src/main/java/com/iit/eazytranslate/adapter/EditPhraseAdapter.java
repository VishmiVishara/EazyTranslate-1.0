package com.iit.eazytranslate.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.eazytranslate.R;
import com.iit.eazytranslate.database.model.Phrase;

import java.util.List;

public class EditPhraseAdapter extends RecyclerView.Adapter<EditPhraseAdapter.MyViewHolder> {
    private List<Phrase> phrasesDataset;
    private AdapterView.OnItemClickListener onItemClickListener;
    private int selectedPosition = -1;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public Phrase getSelectedPhrase() {
            return phrasesDataset.get(selectedPosition);
    }


    public EditPhraseAdapter(List<Phrase> phrasesDataset) {
        this.phrasesDataset = phrasesDataset;
    }

    @NonNull
    @Override
    public EditPhraseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_edit_phrase_row, parent, false);

        EditPhraseAdapter.MyViewHolder vh = new EditPhraseAdapter.MyViewHolder(v, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(phrasesDataset.get(position).getPhrase().toString());
        holder.radioButton.setChecked(position == selectedPosition);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemHolderClick(MyViewHolder holder) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(null, holder.itemView,
                    holder.getAdapterPosition(), holder.getItemId());
    }

    @Override
    public int getItemCount() {
        return phrasesDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView textView;
        public RadioButton radioButton;
        private EditPhraseAdapter mAdapter;

        public MyViewHolder(View v, EditPhraseAdapter adapter) {
            super(v);
            mAdapter = adapter;
            textView = v.findViewById(R.id.editPhraseRowText);
            radioButton = v.findViewById(R.id.radioButton);
            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            selectedPosition = getAdapterPosition();
            notifyItemRangeChanged(0, phrasesDataset.size());
            mAdapter.onItemHolderClick(MyViewHolder.this);

            Intent intent = new Intent("message_subject_intent");
            intent.putExtra("selected_index", selectedPosition);
            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
        }
    }
}
