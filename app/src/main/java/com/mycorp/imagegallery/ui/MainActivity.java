package com.mycorp.imagegallery.ui;

import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.mycorp.imagegallery.api.MyApi;
import com.mycorp.imagegallery.R;
import com.mycorp.imagegallery.model.ItemPicture;
import com.mycorp.imagegallery.model.ItemPicturesResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String PICTURE_LIST_KEY = "PICTURE_LIST_KEY";
    public static final String RECYCLER_VIEW_STATE_KEY = "RECYCLER_VIEW_STATE_KEY";
    public static final int NUMBER_OF_COLUMNS = 2;

    @BindView(R.id.swipeLayout) SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rvPictures) RecyclerView rvPictures;

    private PicturesAdapter adapter;
    private Parcelable recyclerViewState;

    private List<ItemPicture> pictureList;

    private MyApi myApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        createApi();
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelable(RECYCLER_VIEW_STATE_KEY) != null) {
                recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE_KEY);
            }

            if (savedInstanceState.getParcelableArrayList(PICTURE_LIST_KEY) != null) {
                pictureList = savedInstanceState.getParcelableArrayList(PICTURE_LIST_KEY);
                adapter.setData(pictureList);
                restoreRecyclerViewState();
            }
        } else {
            loadImages();
        }
    }

    private void init() {
        adapter = new PicturesAdapter(this);
        GridLayoutManager glm = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        rvPictures.setLayoutManager(glm);
        rvPictures.setHasFixedSize(true);
        rvPictures.setAdapter(adapter);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadImages();
            }
        });
    }

    private void createApi() { myApi = MyApi.Creator.createMyApi(); }

    private void loadImages() {
        swipeLayout.setRefreshing(true);
        myApi.getPictures()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ItemPicturesResponse>() {
                    @Override
                    public void onCompleted() {
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeLayout.setRefreshing(false);
                        showMessage(e.getMessage());
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(ItemPicturesResponse itemPicturesResponse) {
                        pictureList = itemPicturesResponse.getEmbeddedData().getPictureList();
                        adapter.setData(pictureList);
                        restoreRecyclerViewState();
                    }
                });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void restoreRecyclerViewState() {
        if (recyclerViewState != null) {
            rvPictures.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (pictureList != null && !pictureList.isEmpty()) {
            outState.putParcelableArrayList(PICTURE_LIST_KEY, new ArrayList<Parcelable>(pictureList));
        }

        if (rvPictures != null) {
            outState.putParcelable(RECYCLER_VIEW_STATE_KEY, rvPictures.getLayoutManager().onSaveInstanceState());
        }
    }
}
