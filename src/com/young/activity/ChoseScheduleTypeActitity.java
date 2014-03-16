package com.young.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.young.R;

/**
 * Created by VERYYOUNG on 14-3-8.
 */
@SuppressWarnings("deprecation")
public class ChoseScheduleTypeActitity extends TabActivity implements
		OnCheckedChangeListener {

	private TabHost mHost;
	private RadioGroup radioderGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_schedule);
		mHost = this.getTabHost();
		mHost.addTab(mHost.newTabSpec("ONE").setIndicator("ONE")
				.setContent(new Intent(this, LocalScheduleActivity.class)));
		mHost.addTab(mHost.newTabSpec("TWO").setIndicator("TWO")
				.setContent(new Intent(this, ScheduleActivity.class)));
		mHost.addTab(mHost
				.newTabSpec("THREE")
				.setIndicator("THREE")
				.setContent(
						new Intent(this,
								InputOtherStuIdForScheduleActivity.class)));
		radioderGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioderGroup.setOnCheckedChangeListener(this);
		RadioButton radioButton = (RadioButton) findViewById(R.id.local_radio_button);
		radioButton.setChecked(true);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.local_radio_button:
			mHost.setCurrentTabByTag("ONE");
			break;
		case R.id.net_radio_button:
			mHost.setCurrentTabByTag("TWO");
			break;
		case R.id.others_radio_button:
			mHost.setCurrentTabByTag("THREE");
			break;
		default:
			break;
		}
	}
}
