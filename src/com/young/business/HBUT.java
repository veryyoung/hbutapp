package com.young.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.young.entry.Schedule;
import com.young.entry.Score;
import com.young.entry.ScoreInfo;
import com.young.entry.Student;

public class HBUT {

    private static HBUT hbut = null;

    private HBUT() {

    }

    public static HBUT getInstance() {
        if (null == hbut) {
            hbut = new HBUT();
        }
        return hbut;
    }

    private static HttpClient httpClient = null;

    private static synchronized HttpClient getHttpClient() {
        if (httpClient == null) {
            final HttpParams httpParams = new BasicHttpParams();
            httpClient = new DefaultHttpClient(httpParams);
        }

        return httpClient;
    }

    // private HttpClient httpClient = new DefaultHttpClient();
    private String username;

    /**
     * @param username
     * @param password
     * @return logined or not
     * @throws IOException
     */
    public boolean login(String username, String password) throws IOException {
        // login
        this.username = username;
        String url = "http://run.hbut.edu.cn/Account/LogOnForJson?Mobile=1&UserName="
                + username + "&Password=" + password + "&Role=Student";
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = getHttpClient().execute(httpget);
        if (response.getStatusLine().getStatusCode() == 302) {// object
            // moved,代表登录失败
            return false;
        } else if (response.getStatusLine().getStatusCode() == 200) {// ok,代表正常返回
            try {
                JSONObject jsonObject = new JSONObject(EntityUtils.toString(
                        response.getEntity(), "utf-8"));
                String message = jsonObject.getString("Message");
                if ("登陆成功".equals(message)) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        httpget.abort();
        return false;
    }

    /**
     * @return student's infomation
     * @throws IOException
     */
    public Student getInfo() throws IOException {
        Student student = new Student();

        // get result
        String url = "http://run.hbut.edu.cn/T_Student/OwnInfo";
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = getHttpClient().execute(httpget);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, "utf-8");
        httpget.abort();

        // find the info
        String regex = "<td>(.+?)</td>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        List<String> matcherStrings = new ArrayList<String>();
        while (matcher.find()) {
            matcherStrings.add(matcher.group(1).trim());
        }
        student.setClassName(matcherStrings.get(0));
        student.setStuNum(matcherStrings.get(1));
        student.setStuName(matcherStrings.get(2));
        student.setIDCard(matcherStrings.get(3));
        student.setSex(matcherStrings.get(4));
        student.setEthnic(matcherStrings.get(5));
        student.setCollege(matcherStrings.get(6));
        student.setMajor(matcherStrings.get(7));
        student.setYear(matcherStrings.get(8));
        student.setPoliticalStatus(matcherStrings.get(9));
        student.setBirthDay(matcherStrings.get(10));
        student.setEnterScholl(matcherStrings.get(11));
        student.setLeftScholl(matcherStrings.get(12));
        return student;
    }

    /**
     * @param id
     * @return the class schedule of the student
     * @throws IOException
     * @throws JSONException
     */
    public List<Schedule> getSchedule(String id) throws IOException,
            JSONException {
        // get result
        String url = "http://run.hbut.edu.cn/ArrangeTask/MyselfScheduleForJson/?id="
                + id + "&Mobile=1";
        HttpGet httpget = new HttpGet(url);
        // must be the same httpClient
        HttpResponse response = getHttpClient().execute(httpget);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, "utf-8");
        httpget.abort();
        html = html.replaceAll("\\\\", "");
        html = html.substring(html.indexOf("{"), html.lastIndexOf("}") + 1);
        JSONObject jsonObject = new JSONObject(html);
        JSONArray jsonArray = jsonObject.getJSONArray("TimeScheduleList");
        int length = jsonArray.length();
        List<Schedule> schedules = new ArrayList<Schedule>(length);
        JSONObject oj = null;
        Schedule schedule = null;
        for (int i = 0; i < length; i++) {// 遍历JSONArray
            oj = jsonArray.getJSONObject(i);
            schedule = new Schedule();
            schedule.setStuId(id);
            schedule.setCurName(oj.getString("CurName"));
            schedule.setDay(oj.getInt("Day"));
            schedule.setDayTime(oj.getInt("DayTime"));
            schedule.setPlace(oj.getString("Place"));
            schedule.setTeacher(oj.getString("Teacher"));
            schedule.setWeek(oj.getString("Week"));
            schedules.add(schedule);
        }
        return schedules;
    }

    /**
     * @param currentPassword
     * @param newPassword
     * @param confirmPassword
     * @return Message
     * @throws IOException
     * @throws JSONException
     */
    public String changePass(String currentPassword, String newPassword,
                             String confirmPassword) throws IOException, JSONException {
        HttpPost httppost = new HttpPost(
                "http://run.hbut.edu.cn/T_Student/Save");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ID", this.username));
        params.add(new BasicNameValuePair("UserTable", "student"));
        params.add(new BasicNameValuePair("CurrentPassword", currentPassword));
        params.add(new BasicNameValuePair("NewPassword", newPassword));
        params.add(new BasicNameValuePair("ConfirmPassword", confirmPassword));
        params.add(new BasicNameValuePair("X-Requested-With", "XMLHttpRequest"));
        httppost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = getHttpClient().execute(httppost);
        HttpEntity entity = response.getEntity();
        String postResult = EntityUtils.toString(entity, "utf-8");
        httppost.abort();
        JSONObject jsonObject = new JSONObject(postResult);
        String message = jsonObject.getString("Message");

        return message;
    }

    /**
     * @param id
     * @return the score list of the input id
     * @throws ClientProtocolException
     * @throws IOException
     * @throws JSONException
     */
    public HashMap<String,Object> getScore(String id) throws IOException,
            JSONException {
    	HashMap<String,Object> map = new HashMap<String, Object>();
        String url = "http://run.hbut.edu.cn/StuGrade/IndexAllSemesterForJson/?id="
                + id + "&Mobile=1";
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = getHttpClient().execute(httpget);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, "utf-8");
        httpget.abort();
        html = html.replaceAll("\\\\", "");
        html = html.substring(html.indexOf("{"), html.lastIndexOf("}") + 1);
        JSONObject jsonObject = new JSONObject(html);
        ScoreInfo scoreInfo = new ScoreInfo();
        scoreInfo.setStuId(id);
        scoreInfo.setName(jsonObject.getString("Name"));
        scoreInfo.setTotalGradePoint(jsonObject.getString("TotalGradePoint"));
        scoreInfo.setAverageGradePoint(jsonObject.getString("AverageGradePoint"));
        scoreInfo.setContent(jsonObject.getString("Title"));
        map.put("score_info", scoreInfo);
        JSONArray jsonArray = jsonObject.getJSONArray("StuGradeList");
        int length = jsonArray.length();
        ArrayList<Score> scores = new ArrayList<Score>();
        JSONObject oj = null;
        Score score = null;
        for (int i = 0; i < length; i++) {// 遍历JSONArray
            oj = jsonArray.getJSONObject(i);
            score = new Score();
            score.setStuId(id);
            score.setCourseCredit(oj.getDouble("CourseCredit"));
            score.setCourseName(oj.getString("CourseName"));
            score.setCourseType(oj.getString("CourseType"));
            score.setGrade(oj.getDouble("Grade"));
            score.setGradePoint(oj.getDouble("GradePoint"));
            score.setShowScore(oj.getBoolean("IsShowScore"));
            score.setTaskNo(oj.getString("TaskNO"));
            scores.add(score);
        }
        map.put("scores", scores);
        return map;
    }

}
