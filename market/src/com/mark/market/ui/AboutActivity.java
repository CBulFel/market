package com.mark.market.ui;

import com.mark.market.R;
import com.mark.market.R.layout;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {

	private ActionBar actionbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		actionbar=getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setTitle("关于");
	}
}
