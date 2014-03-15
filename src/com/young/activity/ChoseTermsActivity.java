package com.young.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.young.R;
import com.young.business.HBUT;
import com.young.entry.Score;
import com.young.sqlite.DatabaseHelper;
import com.young.sqlite.SQLiteHelper;
import com.young.util.DropDownListView;
import com.young.util.NetworkUtil;

public class ChoseTermsActivity extends BaseActivity {

    private DropDownListView listView;
    private ArrayList<String> data;
    private DatabaseHelper helper;
    private String stuId;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_term);
        listView = (DropDownListView) findViewById(R.id.list_terms);
        helper = new DatabaseHelper(this);
        // 得到登陆学号和密码
        getUserIdAndPassWord();
        // 刷新按钮
        listView.setonRefreshListener(new DropDownListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (helper != null) {
                    // 先删除表中的所有数据
                    helper.clearTableScore(stuId);
                    new GetScoreFromNetWork().execute();
                }

            }

        });

        // 得到数据的方法要放到这里，然后给listView设置Adapter可以放到onStart里面
        if (stuId != "") {
            // 判断表中是否有数据，如果有就直接获取，如果没有就重新去网络拿数据
            if (helper.isEmpty(SQLiteHelper.TABLE_SCORE, stuId)) {
                new GetScoreFromNetWork().execute();
            } else {
                data = helper.getTerms(stuId);
            }
        }
        // 给ListView添加单击响应
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long id) {
                Intent intent = new Intent(ChoseTermsActivity.this,
                        ScoreManagementActivity.class);
                intent.putExtra("term", data.get(position));
                intent.putExtra("id", stuId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        if (data != null) {
            ArrayList<String> myData = new ArrayList<String>();
            for (int i = 0; i < data.size(); i++) {
                String str = data.get(i);
                myData.add(str.substring(0, 4) + "学年第" + str.substring(4)
                        + "学期");
            }
            listView.setAdapter(new ArrayAdapter<String>(
                    ChoseTermsActivity.this,
                    android.R.layout.simple_list_item_1, myData));
        }
        super.onStart();
    }

    private class GetScoreFromNetWork extends
            AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg0) {
            HBUT hbut = HBUT.getInstance();
            try {
                if (!NetworkUtil.isOpenNetwork()) {
                    Intent intent = new Intent(ChoseTermsActivity.this,
                            MainActivity.class);
                    ChoseTermsActivity.this.startActivity(intent);
                    finish();
                    return "无网络连接";
                } else {
                    hbut.login(stuId, password);
                    ArrayList<Score> scores = hbut.getScore(stuId);
                    for (Score score : scores) {
                        helper.addScore(score);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "更新完毕";
        }

        // 这个是在后台执行完毕之后执行
        @Override
        protected void onPostExecute(String result) {
            if (("更新完毕").equals(result)) {
                listView.onRefreshComplete();
                // 需要在这里在查一遍，看看有没有数据
                data = helper.getTerms(stuId);
                if (data != null) {
                    ArrayList<String> myData = new ArrayList<String>();
                    for (int i = 0; i < data.size(); i++) {
                        String str = data.get(i);
                        myData.add(str.substring(0, 4) + "学年第"
                                + str.substring(4) + "学期");
                    }
                    listView.setAdapter(new ArrayAdapter<String>(
                            ChoseTermsActivity.this,
                            android.R.layout.simple_list_item_1, myData));
                } else {
                    Toast.makeText(ChoseTermsActivity.this, "你还没有数据",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ChoseTermsActivity.this, result,
                        Toast.LENGTH_LONG).show();
            }

        }
    }

    private void getUserIdAndPassWord() {
        @SuppressWarnings("deprecation")
        SharedPreferences sp = ChoseTermsActivity.this.getSharedPreferences(
                "userInfo", Context.MODE_WORLD_READABLE);
        stuId = sp.getString("USER_NAME", "");
        password = sp.getString("PASSWORD", "");
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
    
    

}
