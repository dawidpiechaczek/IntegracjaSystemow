package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.presenter.DetailsPresenter;
import com.example.dawid.visitwroclove.utils.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity implements DetailsView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ad_im_image)
    ImageView image;
    @BindView(R.id.ad_btn_favourite)
    FloatingActionButton favourite;
    @BindView(R.id.ad_ll_event_details)
    LinearLayout linearLayout;
    @BindView(R.id.ad_tv_description)
    TextView description;
    @BindView(R.id.ad_tv_address)
    TextView address;
    @BindView(R.id.ad_tv_prize)
    TextView prize;
    @BindView(R.id.ad_tv_date)
    TextView date;
    @BindView(R.id.rating_bar)
    RatingBar rate;
    @Inject
    ObjectDAOImpl objectsRepo;
    @Inject
    EventDAOImpl eventsRepo;
    public DetailsPresenter presenter;
    private String activityType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        presenter = new DetailsPresenter(eventsRepo, objectsRepo);
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                double currentRank = presenter.getBaseDTO().getRank();
                presenter.addNewRate((currentRank + v) / 2.0);
                Toast.makeText(DetailsActivity.this, "Wystawiłeś ocenę: " + Math.round(v), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        presenter.attachView(this);
        getExtras();
        super.onResume();
    }

    @Override
    public void onPause() {
        presenter.detachView();
        super.onPause();
    }

    @Override
    public void setFavourite(int isFavourite) {
        if (isFavourite == 1) {
            favourite.setImageResource(R.drawable.ic_heart_clicked);
        } else {
            favourite.setImageResource(R.drawable.ic_action_name);
        }
    }

    @OnClick(R.id.ad_btn_favourite)
    public void onClickSetFavourite() {
        boolean isFavourite = presenter.getBaseDTO().isFavourite() == 1;
        if (isFavourite) {
            presenter.setFavourite(0);
        } else {
            presenter.setFavourite(1);
        }

    }

    @OnClick(R.id.show_on_map)
    public void showObjectOnMap() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra(activityType, presenter.getBaseDTO().getId());
        intent.putExtra("own_route_mode", true);
        startActivity(intent);
    }

    private void loadObject() {
        BaseDTO baseDTO = presenter.getBaseDTO();
        setToolbarTitle(baseDTO.getName());
        description.setText(baseDTO.getDescription());
        address.setText(baseDTO.getAddress().getStreet() + " " + baseDTO.getAddress().getHomeNumber()
                + ", " + baseDTO.getAddress().getZipCode() + " " + baseDTO.getAddress().getCity());
        if (presenter.getActivityType().equals(Constants.ACTIVITY_VALUE_EVENT)) {
            date.setText(getString(R.string.date) + ": " + getDate(((EventDTO) baseDTO).getStartDate()));
            prize.setText(getString(R.string.price) + ": " + ((EventDTO) baseDTO).getPrice() + " zł");
        }

        String imageUrl = baseDTO.getImage();
        setImage(imageUrl);
        presenter.setFavourite(baseDTO.isFavourite());
    }

    private String getDate(String date) {
        return date.substring(0, 10);
    }

    private void setImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .dontAnimate()
                .centerCrop()
                .into(image);
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        int itemId = extras.getInt(Constants.EXTRA_POSIOTION);
        activityType = extras.getString(Constants.EXTRA_ACTIVITY);
        presenter.setLinearLayoutVisibility(activityType, itemId);
        loadObject();
    }

    public void setVisibility(boolean visibility) {
        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void setToolbarTitle(String name) {
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
