package com.example.ashish.n3wsfeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnRecyclerItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRAS_URL = "imageUrlFromMainActivity";
    public static final String EXTRAS_TITLE = "newsTitleFromMainActivity";
    public static final String EXTRAS_DESCRIPTION = "newsDescriptionFromMainActivity";

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RequestQueue mRequestQueue;
    private NewsAdapter mNewsAdapter;
    private ArrayList<NewsItem> mNewsList;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.app_name);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(MainActivity.this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNewsList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        connectionCheckForData();

    }

    private void connectionCheckForData() {
        if (isDataOnOrNot()) parseJsonFromFile();
        else
            Snackbar.make(findViewById(R.id.mainActivityId), "Network Problem Please try again!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            connectionCheckForData();
                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorAccent)).show();
    }


    protected boolean isDataOnOrNot(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected());

    }

    private void parseJsonFromFile() {

        // Set progressdialog title
        mProgressDialog.setTitle("RSS N3WS FEED");
        // Set progressdialog message
        mProgressDialog.setMessage("Fetching news...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        // Show progressdialog
        mProgressDialog.show();

        String url = "https://dummymail1017.000webhostapp.com/news_json_file/newsFeed.json";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url
                , null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response == null) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Couldn't fetch the response! Site is under maintenance.", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    JSONArray jsonArray = response.getJSONArray("todays_news");

                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.optJSONObject(i);

                        String titleOfNews = jsonObject.getString("title");
                        String imageURl = jsonObject.getString("image");
                        String descriptionOfNews = jsonObject.getString("description");

                        mNewsList.add(new NewsItem(imageURl, titleOfNews, descriptionOfNews));

                    }

                    mNewsAdapter = new NewsAdapter(MainActivity.this, mNewsList);
                    mRecyclerView.setAdapter(mNewsAdapter);
                    mNewsAdapter.setOnItemClickListener(MainActivity.this);
                    mProgressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // error in getting json
                        Log.d(TAG, "Error: " + error.getMessage());
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage() + "The server is under maintenance. Please try again after an hour.", Toast.LENGTH_SHORT).show();
                    }
                });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailedNewsActivity.class);
        NewsItem clickedItem = mNewsList.get(position);
        detailIntent.putExtra(EXTRAS_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRAS_TITLE, clickedItem.getTitleOfNews());
        detailIntent.putExtra(EXTRAS_DESCRIPTION, clickedItem.getDescriptionOfNews());
        startActivity(detailIntent);

    }
}
