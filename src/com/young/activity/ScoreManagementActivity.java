package com.young.activity;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import com.young.R;
import com.young.adapter.AdapterForScore;
import com.young.business.HBUT;
import com.young.entry.SubjectScore;

public class ScoreManagementActivity extends Activity{

	private TextView textTitle;
	private ListView listScore;
	private String title;
	private List<SubjectScore> score;
	private ProgressDialog mpDialog;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_management);
		mpDialog = new ProgressDialog(ScoreManagementActivity.this);
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
		mpDialog.setTitle("");// 设置标题
		mpDialog.setMessage("正在玩命加载中，请稍候....");
		mpDialog.setIndeterminate(false);// 设置进度条是否为不明确
		mpDialog.setCancelable(true);// 设置进度条是否可以按退回键取消
		mpDialog.show();
		textTitle = (TextView) findViewById(R.id.score_title_text);
		listScore = (ListView) findViewById(R.id.score_list);
		title = this.getIntent().getStringExtra(ChoseActivity.SEMESTER);
		textTitle.setText(title.substring(0, 4)+"年第"+title.substring(4, 5)+"学期");
		handler = new Handler();
		new Thread(new Runnable() {

			@Override
			public void run() {
				HBUT hbut = HBUT.getInstance();
				try {
					score = hbut.semesterScore(title);
				} catch (IOException e) {
					e.printStackTrace();
				}
				handler.post(runnableUi);
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score_management, menu);
		return true;
	}

	Runnable runnableUi = new Runnable() {
		@Override
		public void run() {
			AdapterForScore scoreAdapter = new AdapterForScore(ScoreManagementActivity.this, score);
			listScore.setAdapter(scoreAdapter);
			mpDialog.dismiss();
		};
	};

}
