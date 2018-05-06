package com.mycorp.imagegallery.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mycorp.imagegallery.R;
import com.mycorp.imagegallery.model.ItemPicture;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_PICTURE = "PictureActivity.ITEM_PICTURE";
    @BindView(R.id.ivFullScreenPicture) ImageView ivFullScreenPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        ItemPicture itemPicture = getIntent().getParcelableExtra(EXTRA_ITEM_PICTURE);

        Glide.with(this)
                .load(itemPicture.getFile())
                .error(R.drawable.ic_cloud_off)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(ivFullScreenPicture);
    }
}
