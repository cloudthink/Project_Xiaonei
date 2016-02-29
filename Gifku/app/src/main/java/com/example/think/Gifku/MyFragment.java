package com.example.think.Gifku;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by think on 2016/2/16.
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    private String content;
    private TextView txt_title;
    private FrameLayout myfragment_contet;
    private Context mContext;
    private ArrayList<Data> datas = null;
    private FragmentManager fManager = null;
    private long exitTime = 0;

    private MyFragment fg1, fg2, fg3, fg4;
    private FragmentManager fManger;
    private Button btn_one;
    private Button btn_onee;
    private WebView wView;
    public MyFragment(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (content == "1") {

            View view = inflater.inflate(R.layout.web_ribao, container, false);
            wView = (WebView) view.findViewById(R.id.webView_ribao);
            wView.loadUrl("http://www.baidu.com");
            wView.setWebViewClient(new WebViewClient() {
                //在webview里打开新链接
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            return view;
        }else if (content == "2") {
            View view = inflater.inflate(R.layout.web_doutu, container, false);
            wView = (WebView) view.findViewById(R.id.webView_doutu);
            wView.loadUrl("http://www.baidu.com");
            wView.setWebViewClient(new WebViewClient() {
                //在webview里打开新链接
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            return view;
        }
        else if (content == "3") {
            View view = inflater.inflate(R.layout.web_cangku, container, false);
            wView = (WebView) view.findViewById(R.id.webView_cangku);
            wView.loadUrl("http://www.baidu.com");

            wView.setWebViewClient(new WebViewClient() {
                //在webview里打开新链接
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            return view;
        }
        else {
            View view = inflater.inflate(R.layout.web_me, container, false);
            wView = (WebView) view.findViewById(R.id.webView_me);
            wView.loadUrl("http://www.baidu.com");
            wView.setWebViewClient(new WebViewClient() {
                //在webview里打开新链接
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            return view;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                WebView tab_menu_channel_num = (WebView) getActivity().findViewById(R.id.webView1);
                tab_menu_channel_num.setWebViewClient(new WebViewClient() {
                    //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                tab_menu_channel_num.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
                tab_menu_channel_num.loadUrl("http://www.baidu.com/");          //调用loadView方法为WebView加入链接
                //setContentView(tab_menu_channel_num);
                TextView tab_menu_channel_num2 = (TextView) getActivity ().findViewById(R.id.textView1);
                tab_menu_channel_num2.setText("11");
                tab_menu_channel_num.loadUrl("http://www.baidu.com");
                tab_menu_channel_num.setVisibility(View.VISIBLE);
                tab_menu_channel_num2.setVisibility(View.VISIBLE);
                break;
            case R.id.button2:
                TextView tab_menu_channel_num3 = (TextView) getActivity ().findViewById(R.id.textView2);
                tab_menu_channel_num3.setText("111111111");
                tab_menu_channel_num3.setVisibility(View.VISIBLE);
                break;
        }
    }




}

