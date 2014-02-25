//package com.young.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
////import android.view.Menu;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
//
//import com.young.R;
//import com.young.adapter.AdapterForMain;
//
//public class MainActivity extends BaseActivity {
//
//	private ListView listView;
//	public static final String CHOSE_MENU_MESSAGE = "name";
//	public static final String SCHEDULE_TABLE = "0";
//	public static final String SCORE_MANAGEMENT = "3";
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		// Log.v("this","this is ok1");
//		listView = (ListView) findViewById(R.id.main_list_title);
//		// Log.v("this","this is ok2");
//		AdapterForMain adapter = new AdapterForMain(this);
//		// Log.v("this","this is ok3");
//		listView.setAdapter(adapter);
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				switch(arg2){
//				case 0://教学课表
//						Intent intent =  new Intent(MainActivity.this,ChoseActivity.class);
//						intent.putExtra(CHOSE_MENU_MESSAGE, SCHEDULE_TABLE);
//						MainActivity.this.startActivity(intent);break;
//
//
//				case 3://成绩管理
//						Intent intent3 = new Intent(MainActivity.this,ChoseActivity.class);
//						intent3.putExtra(CHOSE_MENU_MESSAGE, SCORE_MANAGEMENT);
//						MainActivity.this.startActivity(intent3);
//						break;
//				case 1:
//					intent = new Intent(MainActivity.this, InfoActivity.class);
//					MainActivity.this.startActivity(intent);
//					break;
//				case 2:
//					intent = new Intent(MainActivity.this, PublicActivity.class);
//					MainActivity.this.startActivity(intent);
//					break;
//				case 4:
//					intent = new Intent(MainActivity.this, ChangeActivity.class);
//					MainActivity.this.startActivity(intent);
//					break;
//				default:
//					break;
//
//				}
//			}
//		});
//
//	}
//
//
//
//
//}
