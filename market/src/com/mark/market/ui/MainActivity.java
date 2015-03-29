package com.mark.market.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mark.market.R;

public class MainActivity extends FragmentActivity implements OnClickListener{


	private Fragment_home fragment_home;
	private Fragment_my fragment_my;
	//���岼�ֶ���
	private FrameLayout layout_home,layout_my;
	//����ͼƬ�������
	private ImageView image_home,image_my;
	//�м���������ť
	private Button tab_plus;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initData();
		
//Ĭ�Ͻ�����ҳ
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
		return super.onOptionsItemSelected(item);
	}


/*	
	��ʼ�����
	*/
private void initView(){
	//ʵ�������������
	layout_home=(FrameLayout)findViewById(R.id.layout_home);
	layout_my=(FrameLayout)findViewById(R.id.layout_my);
	
	image_home=(ImageView)findViewById(R.id.image_home);
	image_my=(ImageView)findViewById(R.id.image_my);
	tab_plus=(Button)findViewById(R.id.tab_plus);

	
}
/*	
��ʼ������,������Ҫ��Ϊ�ؼ���Ӽ����¼�
*/

private void initData(){
	
	layout_home.setOnClickListener(this);
	layout_my.setOnClickListener(this);
	tab_plus.setOnClickListener(this);
	
	
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
	case R.id.tab_plus:
		clickPlusBtn();
		break;
	}
	
}


/**
 * 
 */
private void clickPlusBtn() {
	// TODO Auto-generated method stub
	//������Ʒ����Ʒ�༭
	Intent intent=new Intent(this, GoodsEdit.class);
	startActivity(intent);
}

/**
 * 
 */
private void clickHomeBtn() {
	// TODO Auto-generated method stub
	if(!layout_home.isSelected()){
		//ʵ����Fragmentҳ��  
    fragment_home = new Fragment_home();  
    //�õ�Fragment���������  
    FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
    fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
    //�滻��ǰ��ҳ��  
    fragmentTransaction.replace(R.id.frame_content, fragment_home);  
    //��������ύ  
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
	{//ʵ����Fragmentҳ��  
    fragment_my = new Fragment_my();  
    //�õ�Fragment���������  
    FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();  
    fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
    //�滻��ǰ��ҳ��
    fragmentTransaction.replace(R.id.frame_content, fragment_my);  
    //��������ύ
    fragmentTransaction.commit();  
    layout_my.setSelected(true);  
    image_my.setSelected(true);
    layout_home.setSelected(false);
    image_home.setSelected(false);  
	}
    

}

}
