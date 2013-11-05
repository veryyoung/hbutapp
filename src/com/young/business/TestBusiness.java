package com.young.business;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.young.entry.SubjectScore;



public class TestBusiness {
	public static void main(String[] args) throws IOException, JSONException {
		HBUT hbut = new HBUT();
	    if(hbut.login("1110321229","005685")){
	    	System.out.println("登录成功");
	    }
	    else{
	    	System.out.println("登录失败");
	    }
//		Student student = hbut.getInfo();
//		System.out.println(student.getLeftScholl());
//		System.out.println(student.getClassName());
	    List<SubjectScore> subjectScores = hbut.semesterScore("20121");
//	    Iterator<SubjectScore> it = subjectScores.iterator();
	    for(int i=0;i<subjectScores.size();i++){
	    	System.out.println(subjectScores.get(i).getScoreStatus());
	    }
//	    while(it.hasNext()){
//	    	SubjectScore sb = (SubjectScore) it.next();
//	    	System.out.println(sb.getTaskNo());
//	    }
//	   System.out.println( hbut.getPublic().getTaskPlace());
//	   System.out.println( hbut.getPublic().getTaskNo());
	    
//	    List<Map<String,Map<String,String>>>
	}
}
