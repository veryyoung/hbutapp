package com.young.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.young.R;
import com.young.adapter.AdapterForScore;
import com.young.entry.Score;
import com.young.sqlite.DatabaseHelper;

public class ScoreManagementActivity extends BaseActivity {

    private TextView textTitle;
    private ListView listScore;
    private String title;
    private String stuId;
    private List<Score> scores;
    private BaseAdapter adapter;
    private String text;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_management);
        textTitle = (TextView) findViewById(R.id.score_title_text);
        listScore = (ListView) findViewById(R.id.score_list);
        Intent intent = getIntent();
        title = intent.getStringExtra("term");
        stuId = intent.getStringExtra("id");
        
        text = title.substring(0, 4) + "年第" + title.substring(4, 5) + "学期";
        databaseHelper = new DatabaseHelper(ScoreManagementActivity.this);
        scores = getDataFromDatabase(stuId,title);
        adapter = new AdapterForScore(ScoreManagementActivity.this, scores);
        upDateUI();
    }

    public void upDateUI() {
        textTitle.setText(text);
        listScore.setAdapter(adapter);
    }

    /**
     * 查询成绩将数据库中数据读出并且用于创建adapter
     *
     * @return
     */
    public List<Score> getDataFromDatabase(String id,String term) {
        return databaseHelper.getScore(id, term);
    }

  

}
