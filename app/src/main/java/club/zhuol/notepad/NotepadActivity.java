package club.zhuol.notepad;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import club.zhuol.notepad.adapter.NotePadAdapter;
import club.zhuol.notepad.bean.NotePadBean;
import club.zhuol.notepad.database.SQLiteHelper;

public class NotepadActivity extends AppCompatActivity {

    ListView listView;
    List<NotePadBean> list;
    SQLiteHelper mSqLiteHelper;
    NotePadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        //用于显示记录的列表
        listView = findViewById(R.id.listView);
        ImageView add = findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(NotepadActivity.this, RecordActivity.class),
                        1);
            }
        });
        initData();
    }

    protected void initData() {
        mSqLiteHelper = new SQLiteHelper(this); //create database
        showQueryData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NotePadBean notePadBean = list.get(position);
                Intent intent = new Intent(NotepadActivity.this,RecordActivity.class);
                //记录的内容
                intent.putExtra("time",notePadBean.getNotpadContent());
                //跳转到修改记录界面
                NotepadActivity.this.startActivityForResult(intent,1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(NotepadActivity.this)
                        .setMessage("是否删除记录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NotePadBean notePadBean = list.get(position);
                                if (mSqLiteHelper.deletetData(notePadBean.getId())){
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(NotepadActivity.this,"删除成功",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    private void showQueryData() {
        if (list != null) {
            list.clear();
        }
        //query data from the database(records kept)
        list = mSqLiteHelper.query();
        //bug
        adapter = new NotePadAdapter(NotepadActivity.this, list);
        listView.setAdapter(adapter);
    }

    /**
     * when closed activity of add records
     * the program calls back to the method
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            showQueryData();
        }
    }
}
