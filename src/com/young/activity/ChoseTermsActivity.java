package com.young.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.young.R;
import com.young.business.HBUT;
import com.young.entry.Score;
import com.young.sqlite.DatabaseHelper;
import com.young.sqlite.SQLiteHelper;
import com.young.util.DropDownListView;
import com.young.util.NetworkUtil;

public class ChoseTermsActivity extends BaseActivity{

    private DropDownListView listView;
    private ArrayList<String> data;
    private DatabaseHelper helper;
    private String stuId;
    private String myStuId; //我的学号，也就是登陆的学号
    private String password;
    private ProgressDialog proDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_term);
        listView = (DropDownListView) findViewById(R.id.list_terms);
        @SuppressWarnings("unused")
		Button buttonOthersScore = (Button) findViewById(R.id.button_others_score);
        helper = new DatabaseHelper(this);
        // 得到登陆学号和密码
        getUserIdAndPassWord();
        // 下拉刷新
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
                Intent intent = new Intent(ChoseTermsActivity.this,ScoreManagementActivity.class);
                intent.putExtra("term", data.get(position-1));
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
		protected void onPreExecute() {
			// TODO Auto-generated method stub
        	proDialog = ProgressDialog.show(ChoseTermsActivity.this, "加载中", "加载中...");
			super.onPreExecute();
		}

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
                    hbut.login(myStuId, password);
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
            proDialog.dismiss();
        }
    }

    private void getUserIdAndPassWord() {
        @SuppressWarnings("deprecation")
        SharedPreferences sp = ChoseTermsActivity.this.getSharedPreferences(
                "userInfo", Context.MODE_WORLD_READABLE);
        stuId = sp.getString("USER_NAME", "");
        myStuId = stuId;
        password = sp.getString("PASSWORD", "");
    }
    //按钮响应
    public void ButtonOthersScore(View view){
    	final View dialogView = LayoutInflater.from(ChoseTermsActivity.this).inflate(R.layout.dialog_input_stu_id, null);
    	new AlertDialog.Builder(ChoseTermsActivity.this)
    	.setTitle("小伙伴学号")
    	.setView(dialogView)
    	.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				EditText editId = (EditText)dialogView.findViewById(R.id.input_stu_id);
				String oStuId = editId.getText().toString().trim();
				if("".equals(oStuId) || oStuId.length() != 10){
					Toast.makeText(ChoseTermsActivity.this, "学号错误！", Toast.LENGTH_SHORT).show();
				}else{
					stuId = oStuId;
					if (helper.isEmpty(SQLiteHelper.TABLE_SCORE, stuId)) {
		                new GetScoreFromNetWork().execute();
		            } else {
		                data = helper.getTerms(stuId);
		            }
				}
			}
		})
		.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.show();
    }
    
    

}
