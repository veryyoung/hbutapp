package com.young.business;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;




import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.young.entry.PubliClass;
import com.young.entry.Student;
import com.young.entry.SubjectScore;
import com.young.verycode.CrashCode;
import com.young.verycode.Tools;

public class HBUT {
	
	private static HBUT hbut = null;
	
	private HBUT(){
		
	}
	
	public static HBUT getInstance(){
		if(null==hbut){
			hbut = new HBUT();
		}
		return hbut;
	}
	
	
	private HttpClient httpClient = new DefaultHttpClient();
	private String username;
	 

	/**
	 * 
	 * @param username
	 * @param password
	 * @return logined or not
	 * @throws IOException
	 */
	public boolean login(String username, String password) throws IOException {
		
		this.username = username;
		// get the verycode
		String url = "http://run.hbut.edu.cn/Account/GetValidateCode";
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();

		
		
		// hack the code
		Bitmap bitmap = BitmapFactory.decodeStream(in);
		bitmap = Tools.getSingleCode(bitmap);
		
		String ValidateCode = CrashCode.compare(bitmap);
		System.out.println(ValidateCode);
		httpget.abort();

		// login
		HttpPost httppost = new HttpPost("http://run.hbut.edu.cn/Account/LogOn");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("isRemember", "1"));
		params.add(new BasicNameValuePair("Role", "Student"));
		params.add(new BasicNameValuePair("ValidateCode", ValidateCode));
		params.add(new BasicNameValuePair("UserName", username));
		params.add(new BasicNameValuePair("Password", password));
		httppost.setEntity(new UrlEncodedFormEntity(params));
		response = httpClient.execute(httppost);
		entity = response.getEntity();
		String postResult = EntityUtils.toString(entity, "utf-8");
		httppost.abort();
		Document doc = Jsoup.parse(postResult);
		String title = doc.title();
		if (("登录页面".equals(title))) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return student's infomation
	 * @throws IOException
	 */
	public Student getInfo() throws IOException {
		Student student = new Student();

		// get result
		String url = "http://run.hbut.edu.cn/T_Student/OwnInfo";
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity, "utf-8");
		httpget.abort();

		// find the info
		Document doc = Jsoup.parse(html);
		Elements resultElements = doc.select("td");
		student.setClassName(resultElements.get(0).text());
		student.setStuNum(resultElements.get(1).text());
		student.setStuName(resultElements.get(2).text());
		student.setIDCard(resultElements.get(3).text());
		student.setSex(resultElements.get(4).text());
		student.setEthnic(resultElements.get(5).text());
		student.setCollege(resultElements.get(6).text());
		student.setMajor(resultElements.get(7).text());
		student.setYear(resultElements.get(8).text());
		student.setPoliticalStatus(resultElements.get(9).text());
		student.setBirthDay(resultElements.get(10).text());
		student.setEnterScholl(resultElements.get(11).text());
		student.setLeftScholl(resultElements.get(12).text());
		return student;
	}

	/**
	 * 
	 * @return your ownen class schedule
	 * @throws IOException
	 */
	public List<String> myselfSchedule() throws IOException {
		// get result
		String url = "http://run.hbut.edu.cn/ArrangeTask/MyselfSchedule";
		HttpGet httpget = new HttpGet(url);
		// must be the same httpClient
		HttpResponse response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity, "utf-8");
		httpget.abort();
		html = html.replace("<br />", "|");
		
		// find the schedule
		Document doc = Jsoup.parse(html);

		Elements resultElements = doc.select("td");
		List<String> classInfo = new LinkedList<String>();
		for (Element element : resultElements) {
//			System.out.println("in HBUT");
//			System.out.println(element.text());
			classInfo.add(element.text());
		}
		return classInfo;
	}

	/**
	 * 
	 * @param className
	 * @return the class schedule of the class
	 * @throws IOException
	 */
	public List<String> classSchedule(String className) throws IOException {
		String url = "http://run.hbut.edu.cn/ArrangeTask/ClassSchedule?ClassName="
				+ className;
		HttpGet httpget = new HttpGet(url);
		// must be the same httpClient
		HttpResponse response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity, "utf-8");
		httpget.abort();
		html = html.replace("<br />", "|");
		// find the schedule
		Document doc = Jsoup.parse(html);
		Elements resultElements = doc.select("td");
		List<String> classInfo = new LinkedList<String>();
		for (Element element : resultElements) {
			classInfo.add(element.text());
			// System.out.println(i++ + "    " + element.text());
		}
		return classInfo;
	}

	/**
	 * 
	 * @return the info of the public class
	 * @throws IOException
	 */
	public PubliClass getPublic() throws IOException {
		String url = "http://run.hbut.edu.cn/SelectCurriculum/PublicElectiveIndex";
		HttpGet httpget = new HttpGet(url);
		// must be the same httpClient
		HttpResponse response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity, "utf-8");
		httpget.abort();

		// find the info of the public class
		Document doc = Jsoup.parse(html);
		Elements resultElements = doc.select("td");
		PubliClass publiClass = new PubliClass();
		publiClass.setTaskNo(resultElements.get(1).text());
		publiClass.setTaskName(resultElements.get(2).text());
		publiClass.setTaskType(resultElements.get(3).text());
		publiClass.setTaskColledge(resultElements.get(4).text());
		publiClass.setTaskCredit(resultElements.get(5).text());
		publiClass.setExamTimes(resultElements.get(6).text());
		publiClass.setScore(resultElements.get(7).text());
		int size = resultElements.size();
		for (int i = size - 1; i >= 0; i--) {
			if (resultElements.get(i).text().equals(publiClass.getTaskNo())) {
				publiClass.setTaskPlace(resultElements.get(i + 4).text());
				break;
			}
		}
		return publiClass;
	}
	
	/**
	 * 
	 * @param currentPassword
	 * @param newPassword
	 * @param confirmPassword
	 * @return Message
	 * @throws IOException
	 * @throws JSONException
	 */
	public String changePass(String currentPassword,String newPassword,String confirmPassword) throws IOException, JSONException{
		HttpPost httppost = new HttpPost("http://run.hbut.edu.cn/T_Student/Save");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ID", this.username));
		params.add(new BasicNameValuePair("UserTable", "student"));
		params.add(new BasicNameValuePair("CurrentPassword", currentPassword));
		params.add(new BasicNameValuePair("NewPassword", newPassword));
		params.add(new BasicNameValuePair("ConfirmPassword", confirmPassword));
		params.add(new BasicNameValuePair("X-Requested-With", "XMLHttpRequest"));
		httppost.setEntity(new UrlEncodedFormEntity(params));
		HttpResponse response = httpClient.execute(httppost);
		HttpEntity entity = response.getEntity();
		String postResult = EntityUtils.toString(entity, "utf-8");
		httppost.abort();
		JSONObject jsonObject = new JSONObject(postResult);
		String message = jsonObject.getString("Message");
		return message;
	}
	
	/**
	 *  
	 * @return the semestername of the student
	 * @throws IOException
	 */
	public List<String> getSemesterName() throws  IOException{
		
		List<String> semesterName = new LinkedList<String>();
		String url = "http://run.hbut.edu.cn/StuGrade/Index";
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity, "utf-8");
		httpget.abort();

		// find the info
		Document doc = Jsoup.parse(html);
		Elements resultElements = doc.select("option");
		for (Element element : resultElements) {
			semesterName.add(element.attr("value"));
		}
		return semesterName;
	}
	
	/**
	 * 
	 * @param semester
	 * @return the score list of the input semester
 	 * @throws IOException
	 */
	public List<SubjectScore> semesterScore(String semester) throws IOException{
		String url = "http://run.hbut.edu.cn/StuGrade/Index?SemesterName="+semester;
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity, "utf-8");
		httpget.abort();

		List<SubjectScore> subjectScores = new LinkedList<SubjectScore>();
		// find the info
		Document doc = Jsoup.parse(html);
		Elements resultElements = doc.select("td");
		int size = resultElements.size();
		for(int i=0;i<size;i+=9){
			SubjectScore subjectScore = new SubjectScore();
			subjectScore.setTaskNo(resultElements.get(i).text());
			subjectScore.setTaskName(resultElements.get(i+1).text());
			subjectScore.setTaskType(resultElements.get(i+2).text());
			subjectScore.setGPA(resultElements.get(i+3).text());
			subjectScore.setTaskCredit(resultElements.get(i+4).text());
			subjectScore.setScore(resultElements.get(i+5).text());
			subjectScore.setScoreStatus(resultElements.get(i+7).text());
			subjectScores.add(subjectScore);
		}
		return subjectScores;
	}
	
public ArrayList<String> getClassName() throws IOException{
		
		ArrayList<String> className = new ArrayList<String>();
		String url = "http://run.hbut.edu.cn/ArrangeTask/Index";
		HttpGet httpget = new HttpGet(url);
		// must be the same httpClient
		HttpResponse response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity, "utf-8");///^value=\".*\"$/
		Document doc = Jsoup.parse(html);
//		System.out.println("###################################");
//		System.out.println(doc);
//		Elements classElements = doc.select("a.t-link");
		Elements classElements = doc.getElementsByAttribute("value");
		// > span.t-icon t-plus
		httpget.abort();
//		System.out.println("**************************************");
		for (Element element : classElements) {
			className.add(element.attr("value"));
//			System.out.println(element.attr("value"));
		}
		return className;
		
	}
	

}
