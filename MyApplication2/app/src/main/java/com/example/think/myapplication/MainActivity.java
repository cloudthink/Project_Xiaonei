package com.example.think.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity {
    // 未读消息textview
    private TextView unreadLabel;
    // 未读通讯录textview
    TextView unreadAddressLable;
    protected static final String TAG = "MainActivity";

    private Fragment[] fragments;

    private ImageView[] imagebuttons;
    private TextView[] textviews;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;

    private AlertDialog.Builder conflictBuilder;
    private AlertDialog.Builder accountRemovedBuilder;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    // 账号在别处登录
    public boolean isConflict = false;
    // 账号被移除
    private boolean isCurrentAccountRemoved = false;


    private ImageView iv_add;
    private ImageView iv_search;
    final private int RED = 110;
    final private int GREEN = 120;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //根据id获得imageView对象
        /*
        ImageView logo = (ImageView) findViewById(R.id.imageView);
        //设置资源
        logo.setImageResource(R.mipmap.a_1);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.example.think.myapplication.DetailActivity"));
            }
        });
        Button button01 = (Button) findViewById(R.id.button);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出对话框
                new AlertDialog.Builder(MainActivity.this).setTitle("hi")
                        .setMessage("欢迎")
                        .setPositiveButton("ok",null).show();
            }
        });
         */
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //创建右上角菜单
        menu.add(1, RED, 0, "红色");
        menu.add(1, GREEN, 1, "绿色");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case RED:
                new AlertDialog.Builder(MainActivity.this).setTitle("hi")
                        .setMessage("red")
                        .setPositiveButton("ok", null).show();
                Toast.makeText(MainActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                break;
            case GREEN:
                new AlertDialog.Builder(MainActivity.this).setTitle("hi")
                        .setMessage("green")
                        .setPositiveButton("ok", null).show();
                Toast.makeText(MainActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;

    }


    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.re_weixin:
                index = 0;
                startActivity(new Intent("com.example.think.myapplication.XiaoxiActivity"));
                break;
            case R.id.re_contact_list:
                index = 1;
                startActivity(new Intent("com.example.think.myapplication.FriendsActivity"));
                break;
            case R.id.re_find:
                index = 2;
                startActivity(new Intent("com.example.think.myapplication.FindActivity"));
                break;
            case R.id.re_profile:
                index = 3;
                break;

        }

        /*
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF999999);
        textviews[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;
        */
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.think.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.think.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
