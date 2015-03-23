package com.mark.market.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mark.market.R;

public class MainActivity extends FragmentActivity implements OnClickListener{


	private Fragment_home fragment_home;
	private Fragment_my fragment_my;
	//定义布局对象
	private FrameLayout layout_home,layout_my;
	//定义图片组件对象
	private ImageView image_home,image_my;
	//定义中间按钮图片对象
	private ImageView image_toggle;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initData();
		
//默认进入首页
		clickHomeBtn();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


/*	
	初始化组件
	*/
private void initView(){
	//实例化各组件对象
	layout_home=(FrameLayout)findViewById(R.id.layout_home);
	layout_my=(FrameLayout)findViewById(R.id.layout_my);
	
	image_home=(ImageView)findViewById(R.id.image_home);
	image_my=(ImageView)findViewById(R.id.image_my);
	
	
}
/*	
初始化数据,这里主要是为控件添加监听事件
*/

private void initData(){
	
	layout_home.setOnClickListener(this);
	layout_my.setOnClickListener(this);
	image_toggle.setOnClickListener(this);
	
	
}


@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	
	switch(v.getId())
	{
	case R.id.layout_home:
		clickHomeBtn();
		break;
	case R.id.layout_my:
		clickMyBtn();
		break;

	}
	
}


/**
 * 
 */
private void clickHomeBtn() {
	// TODO Auto-generated method stub
	if(!layout_home.isSelected()){
		//实例化Fragment页面  
    fragment_home = new Fragment_home();  
    //得到Fragment事务管理器  
    FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
    fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
    //替换当前的页面  
    fragmentTransaction.replace(R.id.frame_content, fragment_home);  
    //事务管理提交  
    fragmentTransaction.commit();  
    
    layout_home.setSelected(true);
    image_home.setSelected(true); 
   
    layout_my.setSelected(false);  
    image_my.setSelected(false);  
	}
      

}


private void clickMyBtn() {
	// TODO Auto-generated method stub
	if(!layout_my.isSelected())
	{//实例化Fragment页面  
    fragment_my = new Fragment_my();  
    //得到Fragment事务管理器  
    FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();  
    fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
    //替换当前的页面  
    fragmentTransaction.replace(R.id.frame_content, fragment_my);  
    //事务管理提交
    fragmentTransaction.commit();  
    layout_my.setSelected(true);  
    image_my.setSelected(true);
    layout_home.setSelected(false);
    image_home.setSelected(false);  
	}
    

}

}
