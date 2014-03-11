package com.young.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.young.R;
import com.young.adapter.AdapterForScore;
import com.young.adapter.MyBaseAdapter;
import com.young.business.HBUT;
import com.young.entry.Score;
import com.young.sqlite.DatabaseHelper;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ScoreManagementActivity extends BaseActivity {

    private TextView textTitle;
    private ListView listScore;
    private String title;
    private List<Score> scores;
    private MyBaseAdapter adapter;
    private String text;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_management);
        textTitle = (TextView) findViewById(R.id.score_title_text);
        listScore = (ListView) findViewById(R.id.score_list);
        title = "20132";
        text = title.substring(0, 4) + "年第" + title.substring(4, 5) + "学期";
        databaseHelper = new DatabaseHelper(ScoreManagementActivity.this);
        scores = getDataFromDatabase();
        Log.d("test", scores.size()+"");
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
    public List<Score> getDataFromDatabase() {
        if (databaseHelper.isEmpty("score","1110321229")) {
            new GetScoreFromNetWork().execute("");
        }
        return databaseHelper.getScore("1110321229");
    }

    private class GetScoreFromNetWork extends
            AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg0) {
            HBUT hbut = HBUT.getInstance();
            try {
                scores = hbut.getScore("1110321229");
                for (Score score : scores) {
                    databaseHelper.addScore(score);
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
            Toast.makeText(getApplicationContext(), "成绩更新完毕", Toast.LENGTH_LONG)
                    .show();
            upDateUI();
        }
    }

}
