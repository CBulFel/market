/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import com.mark.android_ui.CommentDialog;
import com.mark.android_util.AndroidShare;
import com.mark.market.R;
import com.mark.market.bean.Good;
import com.mark.market.bean.User;
import com.mark.market.logic.MainService;
import com.mark.market.ui.Gooddetail;
import com.mark.market.ui.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * @author mazhao
 * @describ 
 */
public class GoodItemClick implements OnClickListener {
	
	
	private int position;
	private Good good;
	private Context context;
	
	
	/**
	 * @param position
	 */
	public GoodItemClick(Context context, int position, Good good) {
		super();
		this.position = position;
		this.good = good;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.like:
			break;
		case R.id.goods_item_layout_item: {
			Toast.makeText(context, "item---->" + position, Toast.LENGTH_SHORT)
					.show();
			Intent intent = new Intent(context, Gooddetail.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("good", good);
			intent.putExtras(bundle);
			context.startActivity(intent);
		}
			break;
		case R.id.comment:
			User user = new User();
			CommentDialog commentdialog = new CommentDialog(context);
			if ((user = LoginSessionUtil.getLoginUser(context)) != null) {
				commentdialog = new CommentDialog(context, user.getUid(),
						good.getGid());
				commentdialog.show();
			} else {
				Intent intent1 = new Intent();
				intent1.setClass(context, LoginActivity.class);
				context.startActivity(intent1);
			}
			break;
		case R.id.share:
			String sharemsg = context.getResources().getString(
					R.string.share_message_header)
					+ MainService.goodUrlhead + good.getGid();
			AndroidShare share = new AndroidShare(context, sharemsg,
					good.getGimg1());
			share.show();
			break;
		default:
			break;
		}
	
	}

}
