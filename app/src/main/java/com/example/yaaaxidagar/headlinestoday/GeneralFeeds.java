package com.example.yaaaxidagar.headlinestoday;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GeneralFeeds extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsData>>{

    NewsAdapter newsAdapter;
    ListView listView;
    String imageUrl;
    String contentUrl;
    ProgressBar progressBar;
    TextView emptyView;

    private static final String apiUrl="https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=eb4855160b2c4bf5b70211ca90a0f7a8";

    @Override
    protected void onPause() {
        super.onPause();
        unregisterForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextual_floating_menu,menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general_menu,menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.watchimage:
                Intent int1=new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                startActivity(int1);
                break;

            case R.id.watchnews:
                Intent int2=new Intent(Intent.ACTION_VIEW, Uri.parse(contentUrl));
                startActivity(int2);
                break;

            case R.id.sharenews:
                Intent int3=new Intent(Intent.ACTION_SEND);
                int3.setType("text/plain");
                int3.putExtra(Intent.EXTRA_TEXT,contentUrl);
                startActivity(Intent.createChooser(int3,"News link"));

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_feeds);

        progressBar=(ProgressBar)findViewById(R.id.progress);

        newsAdapter=new NewsAdapter(this,R.layout.single_view_layout,new ArrayList<NewsData>(),R.color.general_background,R.color.general_layout_background);

        listView=(ListView)findViewById(R.id.lv);

        listView.setAdapter(newsAdapter);

        emptyView=(TextView)findViewById(R.id.empty);
        listView.setEmptyView(emptyView);

        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected()){
            getLoaderManager().initLoader(1,null,this);
        }

        else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No Internet Connection");
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                registerForContextMenu(listView);
                NewsData newsData=newsAdapter.getItem(i);
                imageUrl=newsData.getImageUrl();
                contentUrl=newsData.getContentUrl();
                return false;
            }
        });
    }

    @Override
    public Loader<List<NewsData>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this,apiUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsData>> loader, List<NewsData> newsDatas) {

        progressBar.setVisibility(View.GONE);
        emptyView.setText("No news to show!");
        newsAdapter.clear();


        if(!newsDatas.isEmpty() && newsDatas!=null){
            newsAdapter.addAll(newsDatas);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<NewsData>> loader) {
        newsAdapter.clear();
    }
}
