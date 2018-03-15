package com.example.ashish.n3wsfeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.ashish.n3wsfeed.MainActivity.EXTRAS_DESCRIPTION;
import static com.example.ashish.n3wsfeed.MainActivity.EXTRAS_TITLE;
import static com.example.ashish.n3wsfeed.MainActivity.EXTRAS_URL;

public class DetailedNewsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView newsTitleTextView, newsDescTextView;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);


        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRAS_URL);
        String newsTitle = intent.getStringExtra(EXTRAS_TITLE);
        String newsDescription = intent.getStringExtra(EXTRAS_DESCRIPTION);


        mToolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        if (getSupportActionBar() != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(newsTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imageView = (ImageView) findViewById(R.id.image_view_news);
        newsTitleTextView = (TextView) findViewById(R.id.news_title_textview);
        newsDescTextView = (TextView) findViewById(R.id.news_desc_textview);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        newsTitleTextView.setText(newsTitle);
        newsDescTextView.setText(newsDescription);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
}
