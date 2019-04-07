package com.example.dawid.visitwroclove.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.RecyclerAdapter;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.view.activity.PlacesView;

import java.util.List;

public class PlacesPresenter extends BasePresenter<PlacesView> {
    private ObjectDAOImpl mRepoObjects;
    private List<ObjectDTO> list;
    private RecyclerAdapter adapter;
    private Context context;
    private RecyclerView recyclerView;

    public PlacesPresenter(ObjectDAOImpl mRepoObjects) {
        this.mRepoObjects = mRepoObjects;
    }

    public void sort(int itemId) {
        if (itemId == R.id.visit) {
            // list = mRepoObjects.getByType("zwiedzanie");
            list = mRepoObjects.sortByNameAsc();
        } else if (itemId == R.id.eat) {
            //  list = mRepoObjects.getByType("gastronomia");
            list = mRepoObjects.sortByNameDesc();
        } else if (itemId == R.id.hotel) {
            //list = mRepoObjects.getByType("nocleg");
            list = mRepoObjects.sortByRankAsc();
        } else {
            list = mRepoObjects.sortByRankDesc();
        }
        sortWithCategory(list);
    }

    private void sortWithCategory(List<ObjectDTO> list) {
        adapter.setData(list);
        this.list = list;
        adapter.notifyDataSetChanged();
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void iniziallizeAllView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(context);
        list = mRepoObjects.getAll();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                getView().onClickOnAdapter(list.get(position).getId(),view);
            }

            @Override
            public void onFavClick(int position, int isFav) {
                ObjectDTO baseDTO = list.get(position);
                if (isFav == 1)
                    baseDTO.setFavourite(0);
                else
                    baseDTO.setFavourite(1);

                mRepoObjects.add(baseDTO);
                adapter.notifyDataSetChanged();
            }
        });
    }
}

