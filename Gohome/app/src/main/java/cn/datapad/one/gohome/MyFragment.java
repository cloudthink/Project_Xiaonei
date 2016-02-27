package cn.datapad.one.gohome;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by think on 2016/2/16.
 */
public class MyFragment extends Fragment implements View.OnClickListener{

    private String content;
    private TextView txt_title;
    private FrameLayout myfragment_contet;
    private Context mContext;

    private FragmentManager fManager = null;
    private long exitTime = 0;

    private MyFragment fg1,fg2,fg3,fg4;
    private FragmentManager fManger;
    private Button btn_one;
    private Button btn_onee;

    public MyFragment(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // View view1 = inflater.inflate(R.layout.myfg_message,container,false);

        if (content == "first1") {

            View view = inflater.inflate(R.layout.myfg_message, container, false);
            btn_one = (Button) view.findViewById(R.id.button1);
            btn_one.setOnClickListener(this);
            return view;
        } else {
            View view = inflater.inflate(R.layout.myfragment_content, container, false);
            btn_onee = (Button) view.findViewById(R.id.button2);
            btn_onee.setOnClickListener(this);
            TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
            txt_content.setText(content);
            return view;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                WebView tab_menu_channel_num = (WebView) getActivity().findViewById(R.id.webView1);
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
