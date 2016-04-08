package com.commojun.xwalktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

public class MainActivity extends AppCompatActivity {

    private XWalkView xWalkWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Viewの設定とURLの読み込み
        xWalkWebView = (XWalkView)findViewById(R.id.xwalkWebView);
        //xWalkWebView.load("http://commojun.com", null);

        //端末内のファイルを読み込みたい時には下記のとおりにする．
        xWalkWebView.load("file:///android_asset/index.html", "");

        //ネイティブ側からWeb側へデータを送りたいときはこのようにする．
        //要するに，ネイティブ側から下記のようにJSが実行できるということ．
        xWalkWebView.evaluateJavascript("var hoge = 'こんにちは！'", null);

        // turn on debugging
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (xWalkWebView != null) {
            xWalkWebView.pauseTimers();
            xWalkWebView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xWalkWebView != null) {
            xWalkWebView.resumeTimers();
            xWalkWebView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xWalkWebView != null) {
            xWalkWebView.onDestroy();
        }
    }
}
