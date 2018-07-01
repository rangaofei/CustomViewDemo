package com.saka.customviewdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saka.customviewdemo.model.DividerModel;

import java.util.List;

public class DividerAdapter extends RecyclerView.Adapter<DividerAdapter.ViewHolder> {

    private List<DividerModel> modelList;

    public DividerAdapter(List<DividerModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_divier, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(modelList.get(position).getTitle());
        holder.content.setText(modelList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.txt_title);
            this.content = itemView.findViewById(R.id.txt_content);
        }
    }
}
