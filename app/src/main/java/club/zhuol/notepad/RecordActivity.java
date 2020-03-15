package club.zhuol.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import club.zhuol.notepad.DBUtils.DBUtils;
import club.zhuol.notepad.database.SQLiteHelper;

public class RecordActivity extends AppCompatActivity {
    ImageView iv_delete, iv_save;
    TextView tv_timeRecord, noteName;
    EditText edt_contentRecord;
    SQLiteHelper mSqLiteHelper;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();
        initData();
    }

    private void initData() {
        mSqLiteHelper = new SQLiteHelper(this);
        noteName.setText("添加记录");
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            if (id != null) {
                noteName.setText("修改记录");
                edt_contentRecord.setText(intent.getStringExtra("content"));
                tv_timeRecord.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initView() {
        iv_delete = findViewById(R.id.iv_delete);
        iv_save = findViewById(R.id.iv_save);
        tv_timeRecord = findViewById(R.id.tv_timeRecord);
        noteName = findViewById(R.id.noteName);
        edt_contentRecord = findViewById(R.id.edt_contentRecord);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onSave(View view) {
        String noteContent = edt_contentRecord.getText().toString().trim();
        if (noteContent.length() > 0) {
            if (mSqLiteHelper.updateData(id, noteContent, DBUtils.getTime())) {
                showToast("alter success");
                setResult(2);
                finish();
            } else {
                showToast("alter fail");
            }
        } else {    //向数据库中添加数据
            if (noteContent.length() > 0) {
                if (mSqLiteHelper.insertData(noteContent, DBUtils.getTime())) {
                    showToast("save success");
                    setResult(2);
                    finish();
                } else {
                    showToast("save fail");
                }
            } else {
                showToast("修改内容不能为空");
            }
        }
    }

    public void onDelete(View view) {
        edt_contentRecord.setText("");
    }
}
