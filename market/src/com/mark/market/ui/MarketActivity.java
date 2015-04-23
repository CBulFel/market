package com.mark.market.ui;

/**
 * @author mazhao
 * @version 1.0
 * @describe 所有应用界面的接口
 */
public interface MarketActivity {

	//初始化界面操作
	//public void init();
	
	//任务完成后的UI界面更新操作
	public void refresh(int taskID,Object... objects);
	
}
