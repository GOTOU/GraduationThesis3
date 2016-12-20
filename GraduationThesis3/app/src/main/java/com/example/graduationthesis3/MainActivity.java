package com.example.graduationthesis3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.view.inputmethod.InputMethodManager;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.lang.String;



public class MainActivity extends AppCompatActivity {

    WebView myWebView;
    ProgressBar progress;
    EditText editText;
    String str;
    String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progress = (ProgressBar)findViewById(R.id.progress);
        progress.setMax(100); // 水平プログレスバーの最大値を設定
        progress.setProgress(20); // 水平プログレスバーの値を設定
        progress.setSecondaryProgress(60); // 水平プログレスバーのセカンダリ値を設定
        setProgressBarVisibility(true);


        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setVerticalScrollbarOverlay(true);
        myWebView.setHorizontalScrollbarOverlay(true);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.loadUrl("https://www.google.co.jp/");
        //JavaScriptを許可する
//
//        Param param = new Param();
//        myWebView.loadUrl(param.url);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(true);


        WebSettings settings = myWebView.getSettings();
        settings.setSupportMultipleWindows(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportZoom(true);
        settings.setLightTouchEnabled(true);

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack() == true) {
            returnPage();
            return true;

        } else {
            finish();
        }

        return false;

    }

    public void onClickGoBtn(View v){
        editText = (EditText)findViewById(R.id.urlbar);
        str = "http";
        searchText = editText.getText().toString();

        onEnter();
        progress.setVisibility(View.VISIBLE);
        setOnFocusChanged();
        onSearch();

    }

    public void setOnFocusChanged(){
        //キーボードの非表示
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus){
                    InputMethodManager inputMethodMgr = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodMgr.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

    public void onClickHome(View v){
        myWebView.loadUrl("https://www.google.co.jp/");
        if(editText != null) {
            editText.getEditableText().clear();
        }
    }

    public void onClickMoreMenu(View v){

    }

    public void onSearch(){
        if(searchText.startsWith(str)){
            try {
                //               Document urlBefore = Jsoup.connect(searchText).get();  //HTMLのドキュメントを取得

                Document url = Jsoup.parse(searchText, "UTF-8");


                //               Document urlAfter = Jsoup.

                Elements scripts = url.getElementsByTag("script");  //scriptタグを取得

                for(Element script : scripts){
                    String ad= script.attr("Ad");

                    if(ad.indexOf("Ad") != -1){
                        script.remove();
                    }
                }




            } catch (Exception e){
                e.printStackTrace();
            }
            myWebView.loadUrl(searchText);
        } else {
            myWebView.loadUrl("https://www.google.co.jp/#q="+ searchText);
        }
    }

    public void onEnter(){
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //enterが押されたかどうか判断
                if(event.getAction() ==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER ){
                    //キーボードを閉じる
                    InputMethodManager inputMethodMgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodMgr.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    editText = (EditText)findViewById(R.id.urlbar);
                    str = "http";
                    searchText = editText.getText().toString();
                    onSearch();

                    return true;
                }
                return false;
            }
        });
    }

    public void returnPage(){
        myWebView.goBack();
    }

}