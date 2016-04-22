package com.commojun.xwalktest;

import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xwalk.core.JavascriptInterface;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

public class MainActivity extends AppCompatActivity {

    public XWalkView xWalkWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Viewの設定
        xWalkWebView = (XWalkView)findViewById(R.id.xwalkWebView);

        //URLの読み込み
        //xWalkWebView.load("http://commojun.com", null);

        //端末内のファイルを読み込みたい時には下記のとおりにする．
        xWalkWebView.load("file:///android_asset/index.html", null);

        //JavaScriptInterfaceを追加
        //これで，Web側から操作できるオブジェクトを渡してあげる！
        xWalkWebView.addJavascriptInterface(new MyJSInterface(), "android");

        //ネイティブ側からWeb側へデータを送りたいときはこのようにする．
        //要するに，ネイティブ側から下記のようにJSが実行できるということ．
        String hoge = "こんにちは！";
        xWalkWebView.evaluateJavascript("var hoge = '" + hoge + "';", null);


        // turn on debugging
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);

    }

    private class MyJSInterface {
         @JavascriptInterface
        public void showDialog(String name){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            // アラートダイアログのタイトルを設定します
            alertDialogBuilder.setTitle("お名前！！！");
            // アラートダイアログのメッセージを設定します
            alertDialogBuilder.setMessage("これはAndroidネイティブ側で生成されたダイアログです！" +
                    "そして，下にあなたが入力した名前が表示されていれば，Web側からネイティブ側への" +
                    "データ送信に成功したということ…!!!!\n" +
                    "お名前：" + name);
            // アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialogBuilder.create().show();
        }
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
