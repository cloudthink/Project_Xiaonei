package cn.datapad.lifenote.one.lifenote;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private StringBuilder mydbcontent;
    private SQLiteDatabase mydb;
    private DbOpenHelper myDBHelper;
    private Context mContext;
    private int fenlei_id = 0;
    private String[] leibie_list = new String[]{"生活","旅游","学习","生日","纪念日"};
    private int[] fenbie_icon_list = new int[]{R.mipmap.iconfont_apartment,R.mipmap.iconfont_gift,
            R.mipmap.iconfont_heart,R.mipmap.iconfont_train,R.mipmap.iconfont_magnifier};

    //private Button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//in app_bar_main.xml
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent_NoteAddActivity = new Intent(MainActivity.this, NoteAddActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent_NoteAddActivity);
                //结束此活动避免异常返回
                MainActivity.this.finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showDbUpdata();

    }
    public void showDbUpdata(){
        mContext = MainActivity.this;
        myDBHelper = new DbOpenHelper(mContext, "note.db", null, 1);

        //TextView note_main_content = (TextView) findViewById(R.id.content_main_textView_1);

        mydb = myDBHelper.getWritableDatabase();
        mydbcontent = new StringBuilder();

        int tianshu = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date_start = new Date(System.currentTimeMillis());//获取当前时间
        Date date_end = null;

        Cursor cursor = mydb.query("note", null, null, null, null, null, null);
        //int columnsSize = cursor.getColumnCount();获取长度
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        if (cursor.moveToFirst()) {
            do {

                int noteid = cursor.getInt(cursor.getColumnIndex("noteid"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String fenlei = cursor.getString(cursor.getColumnIndex("fenlei"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                //mydbcontent.append( noteid + "：" + title+"：" + fenlei+"：" + date + "\n");
                try {
                    date_end = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tianshu=getGapCount(date_start,date_end);//获取时间差
                String cha = Integer.toString(tianshu);//将int转化为stirng

                Map<String, Object> showitem = new HashMap<String, Object>();
                int fenlei_id = Arrays.asList(leibie_list).indexOf(fenlei);//获取图标分类索引
                showitem.put("icon", fenbie_icon_list[fenlei_id]);
                showitem.put("title", title);
                showitem.put("fenlei", fenlei);
                showitem.put("date", date);
                showitem.put("number", cha);
                listitem.add(showitem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.main_list_item, new String[]{"icon","title", "date","number"}, new int[]{R.id.imageView_note_icon,R.id.textView_title, R.id.textView_date,R.id.textView_number});
        ListView main_listView = (ListView) findViewById(R.id.main_note_list);
        main_listView.setAdapter(myAdapter);
        main_listView.setOnItemClickListener(this);


    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(mContext, "你点击了第" + position +view+id+ "项", Toast.LENGTH_SHORT).show();
        showNoteDetial(position);
    }

    private void showNoteDetial(int position) {
        //生成一个Intent对象
        Intent intent = new Intent();
        //在Intent对象当中添加一个键值对
        intent.putExtra("Intent_note_id", position);
        //设置Intent对象要启动的Activity
        //Toast.makeText(mContext, "你点击了第" + position + "项", Toast.LENGTH_SHORT).show();
        intent.setClass(MainActivity.this, NoteDetailActivity.class);
        //通过Intent对象启动另外一个Activity
        MainActivity.this.startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
       // menu.add(1, RED, 0, "红色");
        //menu.add(1, GREEN, 1, "绿色");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //右上角行为菜单控件
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //右上角actionbar
        switch (id) {
            case R.id.action_addnote:
                // app icon in action bar clicked; go home
                Intent intent_NoteAddActivity = new Intent(this, NoteAddActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent_NoteAddActivity);
                //结束此活动避免异常返回
                MainActivity.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_note) {
            // Handle the camera action
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }
}
