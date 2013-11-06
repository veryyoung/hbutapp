package com.young.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.young.R;
import com.young.adapter.AdapterForMain;




public class MainActivity extends BaseActivity {
	
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Log.v("this","this is ok1");
		listView = (ListView)findViewById(R.id.main_list_title);
//		Log.v("this","this is ok2");
		AdapterForMain adapter = new AdapterForMain(this);
//		Log.v("this","this is ok3");
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch(arg2){
				case 0:Intent intent =  new Intent(MainActivity.this,ChoseActivity.class);
						MainActivity.this.startActivity(intent);break;
				default:break;
					
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}
