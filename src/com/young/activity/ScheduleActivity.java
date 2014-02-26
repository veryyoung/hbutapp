package com.young.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.young.R;
import com.young.adapter.AdapterForSchedule;
import com.young.adapter.MyBaseAdapter;
import com.young.business.HBUT;
import com.young.entry.Schedule;
import com.young.sqlite.DatabaseHelper;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScheduleActivity extends Activity implements OnTouchListener,
        OnGestureListener {

    private TextView textView;
    private ListView listView;
    private String text = "星期一";
    private MyBaseAdapter adapter;
    private int n = 1;
    private GestureDetector mDetector;
    private static final int FLING_MIN_DISTANCE = 100;
    private static final int FLING_MIN_VELOCITY = 0;
    private DatabaseHelper databaseHelper ;
    // 用来存课表的变化
    private ArrayList<Integer> isOneLine;// = new ArrayList<Integer>();
    private List<Schedule> schedules;
    private ArrayList<HashMap<String, String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        textView = (TextView) this.findViewById(R.id.text_schedule_title);
        listView = (ListView) this.findViewById(R.id.list_schedule_course);
        listView.setDividerHeight(0);
        mDetector = new GestureDetector(this, this);
        mDetector.setIsLongpressEnabled(true);
        // listView.setEnabled(false);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Schedule_relative_layout);
        layout.setOnTouchListener(this);
        layout.setLongClickable(true);
        layout.setFocusable(true);
        layout.setClickable(true);
        layout.setLongClickable(true);
        databaseHelper = new DatabaseHelper(ScheduleActivity.this);
        data = getDataFromDatabase(n);
        adapter = new AdapterForSchedule(ScheduleActivity.this, isOneLine, data);
        upDateUI();
    }

    public void upDateUI() {
        textView.setText(text);
        listView.setAdapter(adapter);
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
            // 如果打开了网络
            if (isOpenNetwork()) {
                data = getByDay(n - 1);
            } else {
                data = getDataFromDatabase(n);
            }
            adapter = new AdapterForSchedule(ScheduleActivity.this, isOneLine,
                    data);
            upDateUI();
        }// fling to right
        else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            n = (n == 1) ? 7 : n - 1;
            setDate();
            // 如果打开了网络
            if (isOpenNetwork()) {
                data = getByDay(n - 1);
            } else {
                data = getDataFromDatabase(n);
            }
            adapter = new AdapterForSchedule(ScheduleActivity.this, isOneLine,
                    data);
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

    // 处理得到的数据
    private ArrayList<HashMap<String, String>> getByDay(int day) {
        data = new ArrayList<HashMap<String, String>>();
        isOneLine = new ArrayList<Integer>();
        isOneLine.add(0);
        List<Schedule> daySchedules = getCourse(day);
        HashMap<String, String> map;
        for (int x = 0; x <= 6; x++) {
            map = new HashMap<String, String>();
            map.put("name", daySchedules.get(x).getCurName());
            map.put("teacher", daySchedules.get(x).getTeacher());
            map.put("time", daySchedules.get(x).getWeek());
            if (x == 0) {
                isOneLine.add(isOneLine.get(isOneLine.size() - 1) == 1 ? 0 : 1);
            } else {
                isOneLine.add(isOneLine.get(isOneLine.size() - 1));
            }
            data.add(map);
        }
        return data;
    }

    /**
     * 根据星期几得到一天的课程
     *
     * @param day 星期几
     * @return
     */
    public List<Schedule> getCourse(int day) {
        return databaseHelper.getClassByDay(day, "1110321229");
    }

    // 判断是否有网
    private boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    /**
     * 查询一天的课程 将数据库中数据读出并且用于创建adapter
     *
     * @return
     */
    public ArrayList<HashMap<String, String>> getDataFromDatabase(int day) {
        if (databaseHelper.isEmpty()) {
            new GetSchedualFromNetWork().execute("");
        }
        ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
        isOneLine = new ArrayList<Integer>();
        isOneLine.add(0);
        isOneLine.add(1);
        List<Schedule> cou = databaseHelper.getClassByDay(day, "1110321229");
        for (int j = 0; j < cou.size(); j++) {
            HashMap<String, String> course = new HashMap<String, String>();
            course.put("name", cou.get(j).getCurName());
            course.put("time", cou.get(j).getWeek());
            course.put("teacher", cou.get(j).getTeacher());
            if (j > 0) {
                if (cou.get(j - 1).getDayTime() != cou.get(j).getDayTime()) {
                    isOneLine.add(isOneLine.get(j - 1) == 0 ? 1 : 0);
                } else {
                    isOneLine.add(isOneLine.get(j - 1) == 0 ? 0 : 1);
                }
            }
            myList.add(course);
        }
        return myList;
    }

    private class GetSchedualFromNetWork extends
            AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg0) {
            HBUT hbut = HBUT.getInstance();
            try {
                schedules = hbut.getSchedule("1110321229");

                for (Schedule schedule : schedules) {
                    databaseHelper.addSchedule(schedule);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
