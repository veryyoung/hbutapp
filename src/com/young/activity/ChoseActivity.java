package com.young.activity;

import java.io.IOException;
import java.util.LinkedList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.young.R;
import com.young.business.HBUT;
//import android.widget.ArrayAdapter;

public class ChoseActivity extends BaseActivity implements Runnable{
        
        public static final String SEMESTER = "semester";
        
        private TextView choseText;
        private ListView choseList;
        private String secondTitle;
        private ArrayAdapter<String> myAdapter;
        private LinkedList<String> list ;//= new LinkedList<String>();
        private int switch_title = 10;//用来选择是哪一个activity调用了这个二级菜单

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_chose);
                choseText = (TextView)this.findViewById(R.id.chose_title);
                choseList = (ListView)this.findViewById(R.id.chose_list);
                //这里需要添加一个switch用来添加不同的数据
                switch_title = Integer.parseInt(this.getIntent().getStringExtra(MainActivity.CHOSE_MENU_MESSAGE));
                Log.v("chose_menu_message",""+switch_title);
                switch(switch_title){
                case 0:secondTitle = "课表";
                        list = getScheduleSecondList();
                        break;//教学课表的子菜单
                case 3:secondTitle = "成绩";
                        getScoreSecondList();
                        break;//成绩管理的子菜单
                default:break;
                }
                myAdapter = new ArrayAdapter<String>(ChoseActivity.this,R.layout.activity_chose_item,R.id.chose_item_text,list);
                upDateSecondTitle();
                choseList.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                                // TODO Auto-generated method stub
                                //如果是课表
                                if(switch_title==0){
                                        switch(arg2){
                                                case 0:Intent intent = new Intent(ChoseActivity.this,ScheduleActivity.class);
                                                        intent.putExtra(ChoseItemActivity.CLASS_NAME, ScheduleActivity.NO_NAME);
                                                        startActivity(intent);break;
                                                        
                                                case 1:Intent intent1 = new Intent(ChoseActivity.this,ChoseItemActivity.class);
                                                        
                                                        startActivity(intent1);break;
                                                default:break;
                                                
                                        }
                                }//如果是成绩
                                else if(switch_title==3){
                                        Intent mIntent = new Intent();
                                        int i = 0;
                                        switch(arg2){
                                        case 0://大一上
                                                i = 0;
                                                break;
                                        case 1://
                                                i = 1;
                                                break;
                                        case 2://da er shang 
                                                i = 2;
                                                break;
                                        case 3://da er xia 
                                                i = 3;
                                                break;
                                        case 4://da san shang 
                                                i = 4;
                                                break;
                                        case 5://da san xia
                                                i = 5;
                                                break;
                                        case 6://da si shang 
                                                i = 6;
                                                break;
                                        case 7://da si xia
                                                i = 7;
                                                break;
                                        default:break;
                                        }
                                        
                                        mIntent.putExtra(SEMESTER, list.get(i));
                                        mIntent.setClass(ChoseActivity.this, ScoreManagementActivity.class);
                                        startActivity(mIntent);
                                }
                        }
                });
        }
        
        
        
        //在这里添加一些代码可以不在onCreate里面的执行的，可以加速
        @Override
        protected void onStart() {
                // TODO Auto-generated method stub
                
                super.onStart();
        }




        //更新数据
        public void upDateSecondTitle(){
                choseText.setText(secondTitle);
                choseList.setAdapter(myAdapter);
        }
        
        private LinkedList<String> getScheduleSecondList(){
                LinkedList<String> list = new LinkedList<String>();
                list.add("个人课表");
                list.add("其他班级课表");
                return list;
        }
        
        /**
         * 学期的问题可以每个人都写大一上，大一下，大二上，大二下，大三上，大三下，大四上，大四下
         * 如果还没有数据就显示空白页面或者显示提示页面
         * @return
         */
        
        private void getScoreSecondList(){
                Thread thread = new Thread(this);
                thread.start();
                while(list==null){
                        
                }
        }
        //在这里得到各个学期的列表，因为低年级的有些列表时没有的

        @Override
        public void run() {
                // TODO Auto-generated method stub
                try{
                        HBUT hbut = HBUT.getInstance();
                        list = (LinkedList<String>) hbut.getSemesterName();
//                        System.out.println(list);
                }catch(IOException e){
                        e.printStackTrace();
                }
                
        }


        
}
