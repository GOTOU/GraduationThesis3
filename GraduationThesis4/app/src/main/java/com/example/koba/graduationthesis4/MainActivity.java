package com.example.koba.graduationthesis4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import java.lang.String;

/*import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
*/import java.lang.String;



public class MainActivity extends AppCompatActivity {

    WebView myWebView;
    EditText editText;
    final String str = "http";
    String searchText;
    ProgressBar progressBar;
    private SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        myWebView = (WebView)findViewById(R.id.webview);
        myWebView.setVerticalScrollbarOverlay(true);
        myWebView.setHorizontalScrollbarOverlay(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("https://www.google.co.jp/");

        //JavaScriptの許可
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(true);

        WebSettings settings = myWebView.getSettings();
        settings.setSupportMultipleWindows(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLightTouchEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //SearchViewを取得するSearchView searchView= (SearchView)findViewById(R.id.searchView);
        MenuItem menuItem = menu.findItem(R.id.searchView);

        //SearchViewを取得する
        searchView = (SearchView)MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();
                if (query.startsWith(str)){
                    myWebView.loadUrl(query);
                } else {
                    myWebView.loadUrl("https://www.google.co.jp/#q="+ query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  //アクションバーのメニューの処理
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_settings:
                //設定の処理
                break;

            case R.id.reload:
                myWebView.reload();
                break;

            case R.id.page_back:
                if (myWebView.canGoBack()){
                    myWebView.goBack();
                }

                break;

            case R.id.page_progress:
                if (myWebView.canGoForward()){
                    myWebView.goForward();
                }

                break;

            case R.id.new_tab:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;

        } else {
            finish();
        }

        return false;

    }



}
