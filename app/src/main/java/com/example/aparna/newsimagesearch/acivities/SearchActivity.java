package com.example.aparna.newsimagesearch.acivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aparna.newsimagesearch.Adapters.ArticlesAdapter;
import com.example.aparna.newsimagesearch.Dialog.FiltersFragment;
import com.example.aparna.newsimagesearch.EndlessRecyclerViewScrollListener;
import com.example.aparna.newsimagesearch.Models.Article;
import com.example.aparna.newsimagesearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements FiltersFragment.OnFragmentInteractionListener{
//    @Bind(R.id.etQuery) EditText edtQuery;
    @Bind(R.id.rvResults) RecyclerView rvResults;
    @Bind(R.id.divNetworkError) RelativeLayout divNetworkError;
    @Bind(R.id.tvANetworkError) TextView tvErrorText;
//    @Bind(R.id.btnSearch) Button btnSearch;

    ArrayList<Article> articles;
    ArticlesAdapter adapter;
    String query;
    final static String apiKey = "f306189c1f5f78fe86775fd533f26965:13:74339278";
    private ShareActionProvider miShareAction;
    private Intent shareIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        articles = new ArrayList<>();
        adapter = new ArticlesAdapter(this, articles);
        rvResults.setAdapter(adapter);

        final StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        rvResults.setLayoutManager(gridLayoutManager);

        rvResults.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchMoreArticlesFromSharedQuery(page, query);
            }
        });
        fetchMoreArticlesFromSharedQuery(0, "news");
        adapter.setOnItemClickListener(new ArticlesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //create an intent to display the article
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                //get the article to display
                Article article = articles.get(position);
                //[pass in the article
                i.putExtra("article", article);
                startActivity(i);
            }
        });
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                fetchMoreArticlesFromSharedQuery(0, query);

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            Network[] networks = cm.getAllNetworks();
            NetworkInfo netInfo = cm.getNetworkInfo(networks[0]);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(networks[1]);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }
    public void onArticleSearch(View view) {
//        query = edtQuery.getText().toString();
//        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
//
//        RequestParams params = new RequestParams();
//        params.put("api-key", apiKey);
//        params.put("page", 0);
//        params.put("q", query);
//
//        getClientData(url, params);
//        fetchMoreArticlesFromSharedQuery(0);

    }
    public void fetchMoreArticles(int page) {
//        query = edtQuery.getText().toString();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key", apiKey);
        params.put("page", page);
        params.put("q", query);

        getClientData(url, params);
    }
    public void fetchMoreArticlesFromSharedQuery(int page, String query) {
//        query = edtQuery.getText().toString();
        if(query != null) {
            this.query = query;
        }
        SharedPreferences preferences = this.getSharedPreferences("filters", Context.MODE_PRIVATE);
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", apiKey);
        params.put("page", page);
        params.put("q", query);

        String beingDateString = preferences.getString("beginDate","");
        if (!beingDateString.equals("")) {
            String[] separated = beingDateString.split("/");
            String queryString = separated[2] + separated[0] + separated[1];
            params.add("begin_date",queryString);
        }

        String endDateString = preferences.getString("endDate","");
        if (!endDateString.equals("")) {
            String[] separated = endDateString.split("/");
            String queryString = separated[2] + separated[0] + separated[1];
            params.add("end_date",queryString);
        }
        Resources r = getResources();
        String[] sortOderArray = r.getStringArray(R.array.sort_options);
        int selectedOrder = preferences.getInt("order", 0);
        String sortValue = sortOderArray[selectedOrder];
        params.add("sort", sortValue);

        String[] newsDeskValues = r.getStringArray(R.array.news_desk_values);
        int selectedValue = preferences.getInt("news_desk_values", 0);
        String newsDeskValue = newsDeskValues[selectedValue];
        params.add("fq", "news_desk:(\"" + newsDeskValue + "\")");

        getClientData(url, params);

    }
    private void getClientData(String url, RequestParams params) {
        if(isNetworkOnline()) {
            divNetworkError.setVisibility(View.GONE);
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("DEBUG", response.toString());
                    JSONArray articleJsonResults = null;

                    try {
                        articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                        adapter.addAll(Article.fromJSONArray(articleJsonResults));
                        int curSize = adapter.getItemCount();
                        adapter.notifyItemRangeInserted(curSize, articles.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else {
            setAlertToNetworkConnectionError();
        }

    }
    private void showFiltersDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FiltersFragment filtersFragment = FiltersFragment.newInstance();
        filtersFragment.show(fm, "fragment_filters");
    }
    //Shows alert if not connected to internet
    private void setAlertToNetworkConnectionError() {
//        tvAlertText.setText("Network Connection Error");
        tvErrorText.setVisibility(View.VISIBLE);
        divNetworkError.setVisibility(View.VISIBLE);
    }

    public void onClickSettings(MenuItem item) {
        showFiltersDialog();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
