package com.young.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.young.R;

public class AboutUsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		TextView textAboutPic = (TextView)findViewById(R.id.text_about_picture);
		TextView textAboutUs = (TextView)findViewById(R.id.text_about_us);
		String aboutPic = "we are here with you all";
		String aboutUs = "我们是一群热爱技术，更热爱生<br>活的小伙伴！不错呢，不错呢，真的不错呢！";
		textAboutPic.setText(Html.fromHtml(aboutPic));
		textAboutUs.setText(Html.fromHtml(aboutUs));
		
		
	}


}
