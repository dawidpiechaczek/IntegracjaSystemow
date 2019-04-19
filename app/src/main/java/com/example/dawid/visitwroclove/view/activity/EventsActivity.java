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
import android.widget.Toast;

import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.RecyclerAdapter;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsActivity extends BaseActivity {
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private List<EventDTO> list;
    private List<ObjectDTO> list2;
    private List<BaseDTO> list3 = new ArrayList<>();

    @BindView(R.id.ap_t_toolbar)
    public Toolbar toolbar;
    @BindView(R.id.ap_rv_recycler)
    public RecyclerView recyclerView;
    @Inject
    public EventDAOImpl mRepo;
    @Inject
    public ObjectDAOImpl mObjRepo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean isFavourites = extras.getBoolean("favourite");
            if (isFavourites) {
                initFavPage();
                toolbar.setTitle(getString(R.string.favs));
            }
        } else {
            initPage();
            toolbar.setTitle(getString(R.string.events));
        }
        setToolbar();
    }

    private void setToolbar() {

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
                Intent intent = new Intent(EventsActivity.this, DetailsActivity.class);
                intent.putExtra(Constants.EXTRA_POSIOTION, list.get(position).getId());
                intent.putExtra(Constants.EXTRA_ACTIVITY, Constants.ACTIVITY_VALUE_EVENT);
                Pair<View, String> pair1 = Pair.create(view.findViewById(R.id.cl_im_photo), Constants.TRANSITION_IMAGE);
                Pair<View, String> pair2 = Pair.create(view.findViewById(R.id.cl_tv_name), Constants.TRANSITION_NAME);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EventsActivity.this, pair1, pair2);
                startActivity(intent, optionsCompat.toBundle());
            }

            @Override
            public void onFavClick(int position, int isFav) {
                EventDTO baseDTO = list.get(position);
                if (isFav == 1)
                    baseDTO.setFavourite(0);
                else
                    baseDTO.setFavourite(1);

                mRepo.add(baseDTO);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initFavPage() {
        list = mRepo.getFavourites();
        list2 = mObjRepo.getFavourites();
        list3.addAll(list);
        list3.addAll(list2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(list3);

        adapter.setOnClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(EventsActivity.this, DetailsActivity.class);
                intent.putExtra(Constants.EXTRA_POSIOTION, list3.get(position).getId());
                if (list3.get(position) instanceof EventDTO) {
                    intent.putExtra(Constants.EXTRA_ACTIVITY, Constants.ACTIVITY_VALUE_EVENT);
                } else {
                    intent.putExtra(Constants.EXTRA_ACTIVITY, Constants.ACTIVITY_VALUE_OBJECT);
                }
                Pair<View, String> pair1 = Pair.create(view.findViewById(R.id.cl_im_photo), Constants.TRANSITION_IMAGE);
                Pair<View, String> pair2 = Pair.create(view.findViewById(R.id.cl_tv_name), Constants.TRANSITION_NAME);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EventsActivity.this, pair1, pair2);
                startActivity(intent, optionsCompat.toBundle());
            }

            @Override
            public void onFavClick(int position, int isFav) {
                list3.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(EventsActivity.this, "Usunięto z ulubionych", Toast.LENGTH_LONG).show();
            }
        });
    }
}