package com.hanjx.samplecode.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanjx.samplecode.R;
import com.hanjx.samplecode.activity.CountDownActivity;
import com.hanjx.samplecode.activity.small_window.FloatWindowsActivity_ApiGuide;
import com.hanjx.samplecode.activity.TestActivity;
import com.hanjx.samplecode.activity.small_window.MyFloatActivity;

import java.util.ArrayList;

public class FunctionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickListener onItemClickListener;

    private static final ArrayList<Class> activityClass = new ArrayList<Class>() {
        {
            add(CountDownActivity.class);
            add(TestActivity.class);
            add(FloatWindowsActivity_ApiGuide.class);
            add(MyFloatActivity.class);
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new FunctionListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FunctionListViewHolder viewHolder = (FunctionListViewHolder) holder;
        viewHolder.textView.setText(activityClass.get(position).getSimpleName());
    }

    @Override
    public int getItemCount() {
        return activityClass.size();
    }

    class FunctionListViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        FunctionListViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
            itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClick(activityClass.get(getAdapterPosition()));
            });
        }
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Class activityClass);
    }
}
