package com.view.john.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.view.john.recyclerviewdemo.adapter.GridAdapter;
import com.view.john.recyclerviewdemo.adapter.LinearAdapter;
import com.view.john.recyclerviewdemo.decoration.DefaultItemDecoration;
import com.view.john.recyclerviewdemo.decoration.GridItemDerocation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements GridAdapter.onItemClickListener,GridAdapter.onItemLongClickListener {
    RecyclerView  recyclerView;
    RecyclerView  gridRecyclerView;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    List<String> datas;
    List<String> datas2;
    LinearAdapter mLinearAdapter;
    GridAdapter mLinearAdapter1;
    RecyclerView.ItemDecoration mItemDecoration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        gridRecyclerView = (RecyclerView) findViewById(R.id.recycler_grid);
        initDatas(100);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mLinearAdapter =  new LinearAdapter(this,datas);
        mItemDecoration = new DefaultItemDecoration(this,LinearLayoutManager.HORIZONTAL);
        recyclerView.addItemDecoration(mItemDecoration);
        recyclerView.setAdapter(mLinearAdapter);
        initDatas2(100);
        mLinearAdapter1 = new GridAdapter(this,datas2);
        gridLayoutManager = new GridLayoutManager(this,3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
//        GridItemDerocation gridItemDerocation = new GridItemDerocation(this);
//        gridRecyclerView.setLayoutManager(gridLayoutManager);
//        gridRecyclerView.addItemDecoration(gridItemDerocation);
//        gridRecyclerView.setAdapter(mLinearAdapter1);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        gridRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        GridItemDerocation gridItemDerocation = new GridItemDerocation(this);
//        gridRecyclerView.setLayoutManager(gridLayoutManager);
        gridRecyclerView.addItemDecoration(gridItemDerocation);
        gridRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLinearAdapter1.setOnItemClickListener(this);
        mLinearAdapter1.setOnItemLongClickListener(this);
        gridRecyclerView.setAdapter(mLinearAdapter1);
    }

    private void initDatas(int size) {
        datas = new ArrayList<String>();
        for (int i = 0; i< size; i++){
            datas.add("Recyclerview item "+i);
        }
    }
    private void initDatas2(int size) {
        datas2 = new ArrayList<String>();
        for (int i = 0; i< size; i++){
            StringBuilder sb  = new StringBuilder("Animal");
            for (int j = 0;j<(int)(Math.random()*5);j++){
                sb.append("\n"+"Animal"+j);
            }
            datas2.add(sb.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete:
                mLinearAdapter1.removeData(1);
                break;
            case R.id.add:
                mLinearAdapter1.addData(1);
                break;
        }
        return true;

    }

    @Override
    public void onItemClick(View v, int position) {
        Toast.makeText(this, position+"被点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View v, int position) {
        mLinearAdapter1.removeData(position);
    }
}
