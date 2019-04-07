package com.example.dawid.visitwroclove.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.RecyclerAdapter;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsActivity extends BaseActivity{
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private List<EventDTO> list;

    @BindView(R.id.ap_t_toolbar) public Toolbar toolbar;
    @BindView(R.id.ap_rv_recycler) public RecyclerView recyclerView;
    @Inject public EventDAOImpl mRepo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        initPage();
        setToolbar();
    }

    private void setToolbar() {
        toolbar.setTitle(getString(R.string.events));
        toolbar.setTitleTextColor(getColor(R.color.secondaryToolbar));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initPage() {
        list = mRepo.getAll();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(list);
        adapter.setOnClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {

            }

            @Override
            public void onFavClick(int position, int isFav) {

            }
        });
    }
}