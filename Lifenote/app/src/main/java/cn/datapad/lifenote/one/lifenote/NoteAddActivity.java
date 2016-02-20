package cn.datapad.lifenote.one.lifenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteAddActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private SQLiteDatabase mydb;
    private DbOpenHelper myDBhelper;
    private int i = 1;
    private Button button_submit;
    private StringBuilder mydbcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        mContext = NoteAddActivity.this;
        myDBhelper = new DbOpenHelper(mContext,"note.db",null,1);//初次打开程序创建数据库，调用自定义类继承DbOpenHelper.java类
        bindViews();//绑定UI
    }
    private void bindViews(){
        button_submit = (Button) findViewById(R.id.button_submit);

        button_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        mydb = myDBhelper.getWritableDatabase();
        switch (v.getId()){
            case R.id.button_submit:
                ContentValues chushihua_string = new ContentValues();
                EditText edit = (EditText) findViewById(R.id.editText1);
                String str = edit.getText().toString();
                chushihua_string.put("title",str);
                mydb.insert("note", null, chushihua_string);
                Toast.makeText(mContext,"添加成功！",Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
