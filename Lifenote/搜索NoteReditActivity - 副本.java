package cn.datapad.lifenote.one.lifenote;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteReditActivity  extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_note_redit);
/*
        Intent intent_redit = getIntent();
        //从Intent当中根据key取得value
        int note_id = intent_redit.getExtras().getInt("Intent_note_id_redit");
        Toast.makeText(mContext, "成功"+note_id, Toast.LENGTH_SHORT).show();
       */
        //初始化数据库
        int note_id = 1;
        mContext = NoteReditActivity.this;
        myDBHelper = new DbOpenHelper(mContext, "note.db",null,1);//初次打开程序创建数据库，调用自定义类继承DbOpenHelper.java类

        searchiDb(note_id);//显示要编辑的数据
        bindViews();//绑定UI
        //showFenleiList();//显示分类列表选择
        //showDate();//显示时间选择

    }
    private void searchiDb(int note_id){
        note_id = note_id - 1;
        String note_id_string = String.valueOf(note_id);
        //note_id_string = String.format();
        //Toast.makeText(note_id, Toast.LENGTH_SHORT).show();

        //参数依次是:表名，列名，where约束条件，where中占位符提供具体的值，指定group by的列，进一步约束
        // Cursor cursor = mydb.query("note", new String[]{"noteid"}, "noteid like ?", new String[]{note_id}, null, null, null);
        Cursor cursor = mydb.rawQuery("SELECT * FROM note WHERE noteid = ?", new String[]{note_id_string});
        //存在数据才返回true
        int noteid = 1;
        String title = null;
        String date = null;
        String fenlei = null;
        int tianshu = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date_start = new Date(System.currentTimeMillis());//获取当前时间
        Date date_end = null;
        if (cursor.moveToFirst()) {
            do {
                noteid = cursor.getInt(cursor.getColumnIndex("noteid"));
                title = cursor.getString(cursor.getColumnIndex("title"));
                fenlei = cursor.getString(cursor.getColumnIndex("fenlei"));
                date = cursor.getString(cursor.getColumnIndex("date"));
                try {
                    date_end = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        String cha = Integer.toString(tianshu);//将int转化为stirng

        //int fenlei_id = Arrays.asList(leibie_list).indexOf(fenlei);//获取图标分类索引
        list.add(fenlei);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);

        EditText note_title_redit = (EditText) findViewById(R.id.editText_title_redit);
        Spinner note_fenlei_redit = (Spinner) findViewById(R.id.spinner_fenlei_redit);
        TextView note_date_redit = (TextView) findViewById(R.id.textView_date_redit);

        note_title_redit.setText(title);
        note_fenlei_redit.setAdapter(adapter);
        note_date_redit.setText(date);
    }
    private void showDate(){
        showdate=(TextView) this.findViewById(R.id.textView_date_redit);

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
                DatePickerDialog dpd=new DatePickerDialog(NoteReditActivity.this,Datelistener,year,month,day);
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
        button_submit = (Button) findViewById(R.id.button_submit_redit);

        button_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        mydb = myDBHelper.getWritableDatabase();
        switch (v.getId()){
            case R.id.button_submit_redit:
                ContentValues myaddcontent = new ContentValues();

                EditText title = (EditText) findViewById(R.id.editText_title_redit);
                String title_str = title.getText().toString();
                // Spinner fenlei = (Spinner) findViewById(R.id.spinner_fenlei);
                String fenlei_str =  myitem; //fenlei.get.toString();
                TextView date = (TextView) findViewById(R.id.textView_date_redit);
                String date_str = date.getText().toString();
                //获取自定义内容
                myaddcontent.put("title",title_str);
                myaddcontent.put("fenlei",fenlei_str);
                myaddcontent.put("date",date_str);
                //插入数据库
                mydb.insert("note", null, myaddcontent);
                Toast.makeText(mContext, "添加成功！", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, NoteDetailActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                //NoteReditActivity.this.finish();
                break;
        }
    }


}
