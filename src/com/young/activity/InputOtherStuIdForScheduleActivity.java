package com.young.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.young.R;

/**
 * Created by VERYYOUNG on 14-3-9.
 */
public class InputOtherStuIdForScheduleActivity extends BaseActivity {

    private EditText stuIdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_stu_for_schedule);
        Button okButton = (Button) findViewById(R.id.input_button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stuIdView = (EditText) findViewById(R.id.stu_id_input);
                String stuId = stuIdView.getText().toString();
                if ("".equals(stuId) || stuId.length() < 10) {
                    Toast.makeText(getApplication(), "请输入10位学号", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(InputOtherStuIdForScheduleActivity.this ,ScheduleActivity.class);
                    intent.putExtra("STUID",stuId);
                    startActivity(intent);
                }
            }
        });
    }


}
