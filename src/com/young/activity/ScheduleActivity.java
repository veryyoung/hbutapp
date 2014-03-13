package com.young.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.young.R;
import com.young.adapter.AdapterForSchedule;
import com.young.business.HBUT;
import com.young.entry.Schedule;
import com.young.sqlite.DatabaseHelper;
import com.young.util.DropDownListView;
import com.young.util.NetworkUtil;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScheduleActivity extends BaseActivity implements OnTouchListener,
        OnGestureListener {

    private TextView textView;
    private DropDownListView listView;
    private String text = "星期一";
    private BaseAdapter adapter;
    private int n = 1;
    private GestureDetector mDetector;
    private static final int FLING_MIN_DISTANCE = 100;
    private static final int FLING_MIN_VELOCITY = 0;
    private DatabaseHelper databaseHelper;
    // 用来存课表的变化
    private ArrayList<Integer> isOneLine;// = new ArrayList<Integer>();
    private List<Schedule> schedules;
    private ArrayList<HashMap<String, String>> data;
    private String stuId;
    private String loginStuId;
    private String password;
    private ProgressDialog mpDialog;
    private SharedPreferences sp;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mpDialog = new ProgressDialog(ScheduleActivity.this);
        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
        mpDialog.setTitle("");//设置标题
        mpDialog.setMessage("正在玩命加载中，请稍候....");
        mpDialog.setIndeterminate(false);//设置进度条是否为不明确
        mpDialog.setCancelable(true);//设置进度条是否可以按退回键取消
        mpDialog.show();
        sp = this.getSharedPreferences("userInfo",
                Context.MODE_WORLD_READABLE);
        stuId = getIntent().getStringExtra("STUID");
        loginStuId = sp.getString("USER_NAME", "");
        password = sp.getString("PASSWORD", "");
        if ("".equals(stuId) || null == stuId) {
            stuId = loginStuId;
        }
        textView = (TextView) this.findViewById(R.id.text_schedule_title);
        listView = (DropDownListView) this.findViewById(R.id.list_schedule_course);
        listView.setDividerHeight(0);
        mDetector = new GestureDetector(this, this);
        mDetector.setIsLongpressEnabled(true);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Schedule_relative_layout);
        layout.setOnTouchListener(this);
        layout.setLongClickable(true);
        layout.setFocusable(true);
        layout.setClickable(true);
        layout.setLongClickable(true);
        databaseHelper = new DatabaseHelper(ScheduleActivity.this);
        upDateUI();
        listView.setonRefreshListener(new DropDownListView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                databaseHelper.clearTableSchedule(stuId, false);
                new GetSchedualFromNetWork().execute("");
                upDateUI();
                adapter.notifyDataSetChanged();
                listView.onRefreshComplete();

            }
        });
    }

    public void upDateUI() {
        data = getDataFromDatabase(n);
        adapter = new AdapterForSchedule(ScheduleActivity.this, isOneLine, data);
        textView.setText(text);
        listView.setAdapter(adapter);
        mpDialog.dismiss();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        // fling to left
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            n = (n == 7) ? 1 : n + 1;
            setDate();
            upDateUI();
        }// fling to right
        else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            n = (n == 1) ? 7 : n - 1;
            setDate();
            upDateUI();
        }

        return false;
    }

    private void setDate() {
        switch (n) {
            case 1:
                text = "星期一";
                break;
            case 2:
                text = "星期二";
                break;
            case 3:
                text = "星期三";
                break;
            case 4:
                text = "星期四";
                break;
            case 5:
                text = "星期五";
                break;
            case 6:
                text = "星期六";
                break;
            case 7:
                text = "星期天";
                break;
        }
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * 查询一天的课程 将数据库中数据读出并且用于创建adapter
     *
     * @return
     */
    public ArrayList<HashMap<String, String>> getDataFromDatabase(int day) {
        if (databaseHelper.isEmpty("schedule", stuId)) {
            new GetSchedualFromNetWork().execute("");
        }
        ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
        isOneLine = new ArrayList<Integer>();
        isOneLine.add(0);
        isOneLine.add(1);
        List<Schedule> cou = databaseHelper.getClassByDay(day, stuId, false);
        //start
        int listCount = 0, daytime = 1;
        while (daytime <= 5) {
            if (listCount < cou.size() && cou.get(listCount).getDayTime() == daytime) {//如果是有课
                while (listCount < cou.size() && cou.get(listCount).getDayTime() == daytime) {//判断是不是同一次课
                    HashMap<String, String> course = new HashMap<String, String>();
                    course.put("name", cou.get(listCount).getCurName());
                    course.put("time", cou.get(listCount).getPlace() + " " + cou.get(listCount).getWeek());
                    course.put("teacher", cou.get(listCount).getTeacher());
                    course.put("daytime", "" + daytime);
                    listCount++;
                    myList.add(course);
                }
                daytime++;
            } else {//如果没课
                HashMap<String, String> course = new HashMap<String, String>();
                course.put("name", "");
                course.put("time", "");
                course.put("teacher", "");
                course.put("daytime", "" + daytime);
                daytime++;
                myList.add(course);
            }
        }
        //给isOneLine赋值
        for (int x = 0; x < myList.size() - 1; x++) {
            if (myList.get(x).get("daytime").equals(myList.get(x + 1).get("daytime"))) {
                isOneLine.add(isOneLine.get(isOneLine.size() - 1));
            } else {
                isOneLine.add(isOneLine.get(isOneLine.size() - 1) == 1 ? 0 : 1);
            }
        }
        if (myList.get(myList.size() - 1).get("daytime").equals(myList.get(myList.size() - 2).get("daytime"))) {
            isOneLine.add(isOneLine.size() - 1);
        } else {
            isOneLine.add(isOneLine.get(isOneLine.size() - 1) == 1 ? 0 : 1);
        }
        //end
        return myList;
    }

    private class GetSchedualFromNetWork extends
            AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg0) {
            HBUT hbut = HBUT.getInstance();
            try {
                if (!NetworkUtil.isOpenNetwork()) {
                    finish();
                    return "无网络连接";
                } else {
                    hbut.login(loginStuId, password);
                    schedules = hbut.getSchedule(stuId);
                    for (Schedule schedule : schedules) {
                        databaseHelper.addSchedule(schedule, false);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "课表更新完毕";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                    .show();
            if ("课表更新完毕".equals(result)) {
                upDateUI();
            }
        }

    }
}