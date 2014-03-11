package com.young.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.young.R;
import com.young.entry.Schedule;
import com.young.sqlite.DatabaseHelper;

/**
 * Created by VERYYOUNG on 14-3-11.
 */
public class InsertScheduleActivity extends Activity {
    private EditText curNameView;
    private EditText weekView;
    private EditText placeView;
    private EditText teacherView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiactivity_insert_schedule);
        TextView titleView = (TextView) findViewById(R.id.insert_schedule_title);
        Button submitButton = (Button) findViewById(R.id.insert_schedule_button);
        Boolean isModify = getIntent().getBooleanExtra("ISMODIFY", false);
        if (isModify) {
            titleView.setText("修改课表");
            submitButton.setText("修改");
        } else {
            titleView.setText("插入课表");
            submitButton.setText("提交");
        }
        curNameView = (EditText) findViewById(R.id.insert_schedule_name);
        teacherView = (EditText) findViewById(R.id.insert_schedule_teacher);
        placeView = (EditText) findViewById(R.id.insert_schedule_place);
        weekView = (EditText) findViewById(R.id.insert_schedule_week);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Schedule schedule = new Schedule();
                schedule.setDayTime(getIntent().getIntExtra("DAYTIME", 0));
                schedule.setDay(getIntent().getIntExtra("DAY", 0));
                schedule.setCurName(curNameView.getText().toString());
                schedule.setPlace(placeView.getText().toString());
                schedule.setWeek(weekView.getText().toString());
                schedule.setTeacher(teacherView.getText().toString());
                final SharedPreferences sp = InsertScheduleActivity.this.getSharedPreferences("userInfo",
                        Context.MODE_WORLD_READABLE);
                schedule.setStuId(sp.getString("USER_NAME", ""));
                DatabaseHelper databaseHelper = new DatabaseHelper(InsertScheduleActivity.this);
                databaseHelper.addSchedule(schedule, true);

                Intent intent = new Intent(InsertScheduleActivity.this, LocalScheduleActivity.class);
                InsertScheduleActivity.this.startActivity(intent);

            }
        });


    }
}
