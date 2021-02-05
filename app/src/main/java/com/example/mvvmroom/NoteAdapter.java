package com.example.mvvmroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//list adapter base class for presenting List data in a RecyclerView, including computing diffs between Lists on a background thread.
public class NoteAdapter extends ListAdapter< Note, NoteAdapter.NoteHolder> {
//    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.textviewtitle.setText(currentNote.getTitle());
        holder.textviewdescription.setText(currentNote.getDescription());
        holder.textviewpriority.setText(String.valueOf(currentNote.getPriority()));
    }

    /*public void setNotes(List<Note> notes) {
        this.notes = notes;
        //Notify any registered observers that the data set has changed
        notifyDataSetChanged();
    }*/

    //for position it will delete
    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView textviewtitle;
        private TextView textviewdescription;
        private TextView textviewpriority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textviewtitle = itemView.findViewById(R.id.text_view_title);
            textviewdescription = itemView.findViewById(R.id.text_view_description);
            textviewpriority = itemView.findViewById(R.id.text_view_priority);

            //make the click listener work on itemview
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        //setting listenr on itemclick
                        listener.OnItemClick(getItem(position));

                    }
                }
            });
        }
    }

    //interface created for recyclerview item click
    public interface OnItemClickListener {
        void OnItemClick(Note note);
    }

    //reference to call the above interface and the OnItemClick method
    public void setOnItemClickListener(OnItemClickListener listener) {
        //this is where we catch the click
        this.listener = listener;
    }


}
