package com.young.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.young.R;

/**
 * Created by VERYYOUNG on 14-3-8.
 */
public class ChoseScheduleTypeActitity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_schedule_type);

        TextView selfScheduleView = (TextView) findViewById(R.id.self_schedule);
        selfScheduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoseScheduleTypeActitity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });
        TextView othersScheduleView = (TextView) findViewById(R.id.others_schedule);
        othersScheduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoseScheduleTypeActitity.this,InputOtherStuIdForScheduleActivity.class);
                startActivity(intent);
            }
        });
    }
}
