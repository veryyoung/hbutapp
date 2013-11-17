package com.young.activity;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
//import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.young.R;
import com.young.adapter.AdapterForScore;
import com.young.business.HBUT;
import com.young.entry.SubjectScore;
//import java.util.logging.Handler;
//import com.young.R.layout;
//import com.young.R.menu;

public class ScoreManagementActivity extends Activity implements Runnable{
	
	private TextView textTitle;
	private ListView listScore;
	private String title;
	private List<SubjectScore> score;
	
	private ProgressDialog  pd;
	
	private final Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			pd.dismiss();
			super.handleMessage(msg);
		}
		

	};
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_management);
		textTitle = (TextView)findViewById(R.id.score_title_text);
		listScore = (ListView)findViewById(R.id.score_list);
		title = this.getIntent().getStringExtra(ChoseActivity.SEMESTER);
		
		
		Log.v("ScoreManagementActivity","56 is ok");
		
		
		Thread thread = new Thread(this);
		pd = ProgressDialog.show(ScoreManagementActivity.this, "请等待", "加载中，请等待...");
		thread.start();
		//非要这个东西，不然会报错
		while(score==null){
			
		}

		textTitle.setText(title);
		Log.v("ScoreManagementActivity","63 is ok ");
		AdapterForScore scoreAdapter = new AdapterForScore(this,score);
		listScore.setAdapter(scoreAdapter);
		Log.v("ScoreManagementActivity","66 is ok ");
		
		
	}
	
	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score_management, menu);
		return true;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			HBUT hbut = HBUT.getInstance();
			score = hbut.semesterScore(title);
			System.out.println(score.get(0).getScore());
			for (SubjectScore score1 : score) {
				System.out.println(score1.getScore());
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		handler.sendEmptyMessage(0);
	}

}
