package com.young.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.young.R;

/**
 * Created by VERYYOUNG on 14-3-8.
 */
public class ChoseScheduleTypeActitity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_schedule_type);
        //网络课表
        ImageView netScheduleView = (ImageView) findViewById(R.id.net_schedule);
        netScheduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoseScheduleTypeActitity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });
        //小伙伴课表
        ImageView othersScheduleView = (ImageView) findViewById(R.id.others_schedule);
        othersScheduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoseScheduleTypeActitity.this,InputOtherStuIdForScheduleActivity.class);
                startActivity(intent);
            }
        });
        //本地课表
        ImageView localScheduleView = (ImageView) findViewById(R.id.local_schedule);
        localScheduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoseScheduleTypeActitity.this,LocalScheduleActivity.class);
                startActivity(intent);
            }
        });
    }
}
