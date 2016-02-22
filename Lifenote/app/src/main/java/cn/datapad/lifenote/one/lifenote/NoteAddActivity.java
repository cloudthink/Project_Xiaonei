package cn.datapad.lifenote.one.lifenote;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteAddActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private SQLiteDatabase mydb;
    private DbOpenHelper myDBHelper;
    private int i = 1;
    private Button button_submit;
    private StringBuilder mydbcontent;

    private List<String> list = new ArrayList<String>();
    private Spinner mySpinner;
    private ArrayAdapter<String> adapter;
    public String myitem;

    private TextView showdate;
    private Button setdate;
    private int year;
    private int month;
    private int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        //初始化数据库
        mContext = NoteAddActivity.this;
        myDBHelper = new DbOpenHelper(mContext, "note.db",null,1);//初次打开程序创建数据库，调用自定义类继承DbOpenHelper.java类

        bindViews();//绑定UI
        showFenleiList();//显示分类列表选择
        showDate();//显示时间选择

    }

    private void showDate(){
        showdate=(TextView) this.findViewById(R.id.textView_date);

        //初始化Calendar日历对象
        Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
        Date mydate=new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期

        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        showdate.setText(year+"-"+(month+1)+"-"+day); //显示当前的年月日

        //添加单击事件--设置日期
        showdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                /**
                 * 构造函数原型：
                 * public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack,
                 * int year, int monthOfYear, int dayOfMonth)
                 * content组件运行Activity，
                 * DatePickerDialog.OnDateSetListener：选择日期事件
                 * year：当前组件上显示的年，monthOfYear：当前组件上显示的月，dayOfMonth：当前组件上显示的第几天
                 *
                 */
                //创建DatePickerDialog对象
                DatePickerDialog dpd=new DatePickerDialog(NoteAddActivity.this,Datelistener,year,month,day);
                dpd.show();//显示DatePickerDialog组件
            }
        });
    }

    private void showFenleiList(){
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        list.add("生活");
        list.add("旅游");
        list.add("学习");
        list.add("生日");
        list.add("纪念日");
        // myTextView = (TextView)findViewById(R.id.TextView_city);
        mySpinner = (Spinner)findViewById(R.id.spinner_fenlei);
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        mySpinner.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
                //myTextView.setText("您选择的是："+ adapter.getItem(arg2));
                myitem=adapter.getItem(arg2);
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //myTextView.setText("NONE");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        mySpinner.setOnTouchListener(new Spinner.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });
    }

    private DatePickerDialog.OnDateSetListener Datelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {


            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //更新日期
            updateDate();

        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
            //在TextView上显示日期
            showdate.setText(year+"-"+(month+1)+"-"+day);
        }
    };

    private void bindViews(){
        button_submit = (Button) findViewById(R.id.button_submit);

        button_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        mydb = myDBHelper.getWritableDatabase();
        switch (v.getId()){
            case R.id.button_submit:
                ContentValues myaddcontent = new ContentValues();

                EditText title = (EditText) findViewById(R.id.editText_title);
                String title_str = title.getText().toString();
               // Spinner fenlei = (Spinner) findViewById(R.id.spinner_fenlei);
                String fenlei_str =  myitem; //fenlei.get.toString();
                TextView date = (TextView) findViewById(R.id.textView_date);
                String date_str = date.getText().toString();
                //获取自定义内容
                myaddcontent.put("title",title_str);
                myaddcontent.put("fenlei",fenlei_str);
                myaddcontent.put("date",date_str);
                //插入数据库
                mydb.insert("note", null, myaddcontent);
                Toast.makeText(mContext,"添加成功！",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                NoteAddActivity.this.finish();
                break;
        }
    }


}
