package com.young.activity;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.young.R;
import com.young.adapter.AdapterForScore;
import com.young.business.HBUT;
import com.young.entry.SubjectScore;
//import com.young.R.layout;
//import com.young.R.menu;

public class ScoreManagementActivity extends Activity implements Runnable{
	
	private TextView textTitle;
	private ListView listScore;
	private String title;
	private ProgressBar progressBar;
	private List<SubjectScore> score;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_management);
		textTitle = (TextView)findViewById(R.id.score_title_text);
		listScore = (ListView)findViewById(R.id.score_list);
		title = this.getIntent().getStringExtra(ChoseActivity.SEMESTER);
		progressBar = (ProgressBar)findViewById(R.id.score_progress);
		///////////////////////////////////
		progressBar.setIndeterminate(true);
		progressBar.setVisibility(View.VISIBLE);
	}
	
	
	
//将setAdapter等放到这里加快速度
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
		new Thread(this).start();
		while(null==score);
		progressBar.setVisibility(View.GONE);
		textTitle.setText(title);
		AdapterForScore scoreAdapter = new AdapterForScore(this,score);
		listScore.setAdapter(scoreAdapter);
		super.onStart();
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
			System.out.println(score);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
