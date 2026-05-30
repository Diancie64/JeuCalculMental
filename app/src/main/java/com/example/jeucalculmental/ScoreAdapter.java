package com.example.jeucalculmental;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private final List<ScoreDatabase.ScoreEntry> scoreList;

    public ScoreAdapter(List<ScoreDatabase.ScoreEntry> scoreList) {
        this.scoreList = scoreList;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_score, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        ScoreDatabase.ScoreEntry entry = scoreList.get(position);
        holder.pseudo.setText(entry.pseudo);
        holder.score.setText(String.valueOf(entry.score));
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView pseudo, score;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            pseudo = itemView.findViewById(R.id.rowPseudo);
            score = itemView.findViewById(R.id.rowScore);
        }
    }
}
