package com.view.john.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.view.john.recyclerviewdemo.R;
import com.view.john.recyclerviewdemo.adapter.GridAdapter.ViewHolder;

import java.util.List;

/**
 * Created by John on 2016/12/13.
 */

public class GridAdapter extends RecyclerView.Adapter<ViewHolder> {

    List<String> mList;
    Context context;
    int drawables[] = new int[]{R.mipmap.cat,R.mipmap.cat1,R.mipmap.dog,R.mipmap.pig};

    private onItemClickListener onItemClickListener;
    private onItemLongClickListener onItemLongClickListener;

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    public void setOnItemClickListener(GridAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(GridAdapter.onItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public GridAdapter(Context context , List<String> mList){
        this.context = context;
        if (this.mList != null && this.mList.size()>0)
            this.mList.clear();
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gridview,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(mList.get(position));

        holder.imageView.setBackgroundResource(drawables[(int)(Math.random()*4)]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,position+"",Toast.LENGTH_LONG).show();
                  onItemClickListener.onItemClick(view,position);
            }
        });

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int pos = holder.getLayoutPosition();
                onItemLongClickListener.onItemLongClick(holder.itemView,pos);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            textView = (TextView) itemView.findViewById(R.id.grid_tv);
        }
    }

    public void addData(int position){
        mList.add(position,"Insert One");
        notifyItemInserted(position);
        notifyItemRangeChanged(position,mList.size());
    }

    public void removeData(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mList.size());
    }

    public interface onItemClickListener{
        void onItemClick(View v,int position);
    }

    public interface  onItemLongClickListener{
        void onItemLongClick(View v,int position);

    }
}
