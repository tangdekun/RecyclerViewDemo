package com.view.john.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.view.john.recyclerviewdemo.R;

import java.util.List;

/**
 * Created by John on 2016/12/11.
 */

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.ViewHolder> {

    Context context;
    List<String> mList;

    public LinearAdapter(Context context, List<String> mList){
        this.context = context;
        if (this.mList != null && this.mList.size()>0)
            this.mList.clear();
        this.mList = mList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_textview,parent,false);
        ViewHolder viewHolder =  new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
