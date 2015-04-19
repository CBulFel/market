package com.mark.market.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mark.market.R;
import com.mark.market.adapter.MyAdapter;
import com.mark.market.bean.Task;
import com.mark.market.logic.MainService;

public class GoodsEdit extends Activity implements MarketAcitivity,
		OnClickListener {
	private static String TAG = "goodedit";
	private static int PHOTO_REQUEST_GALLERY = 1;
	private static int PHOTO_REQUEST_CAREMA = 2;
	private static int PHOTO_CUT = 3;
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private Gallery Mygallery;
	private ArrayList<Bitmap> groupbit;
	private String[] categorys;
	private String[] places;
	private Bitmap selectbit;
	private Button button_addphoto, button_delphoto, sale;
	private MyAdapter myadapter;
	private AlertDialog mydialog;
	private int number;
	private Map<String, Object> good_info;
	private File tempFile;
	private viewholder holder;

	static class viewholder {
		public EditText edit_title;
		public EditText edit_describ;
		public EditText edit_price;
		public EditText edit_preprice;
		public Spinner edit_category;
		public Spinner edit_palce;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodsedit);
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(true);
		// actionbar.setHomeAsUpIndicator(R.drawable.actionbar_backbtn);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setTitle("编辑宝贝");
		holder = new viewholder();
		holder.edit_title = (EditText) findViewById(R.id.edit_title);
		holder.edit_describ = (EditText) findViewById(R.id.edit_describ);
		holder.edit_price = (EditText) findViewById(R.id.edit_price);
		holder.edit_preprice = (EditText) findViewById(R.id.edit_preprice);
		holder.edit_category = (Spinner) findViewById(R.id.edit_category);
		holder.edit_palce = (Spinner) findViewById(R.id.edit_place);
		button_addphoto = (Button) findViewById(R.id.myadd);
		Mygallery = (Gallery) findViewById(R.id.mygallery);
		sale = (Button) findViewById(R.id.good_edit_sale);
		groupbit = new ArrayList<Bitmap>();
		myadapter = new MyAdapter(this, groupbit);
		good_info = new HashMap<String, Object>();
		Mygallery.setAdapter(myadapter);
		Resources res = getResources();
		categorys = res.getStringArray(R.array.category);
		places = res.getStringArray(R.array.places);
		Log.w(TAG, holder.edit_category.getSelectedItem().toString());
		// 添加图片
		button_addphoto.setOnClickListener(this);
		sale.setOnClickListener(this);
		// 查看图片
		Mygallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				selectbit = groupbit.get(position);
				AlertDialog alert = new AlertDialog.Builder(GoodsEdit.this)
						.create();
				alert.show();
				alert.setTitle("查看");
				alert.getWindow().setLayout(selectbit.getWidth(),
						selectbit.getWidth());
				alert.getWindow().setGravity(Gravity.CENTER);
				alert.getWindow().setBackgroundDrawable(
						new BitmapDrawable(selectbit));
			}
		});
		/*
		 * 删除图片
		 */
		Mygallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				button_delphoto = (Button) view.findViewById(R.id.mybutton);

				number = position;
				button_delphoto.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						groupbit.remove(number);
						myadapter = new MyAdapter(GoodsEdit.this, groupbit);
						Mygallery.setAdapter(myadapter);

					}
				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

	}

	public void addImgAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("请选择图片");
		builder.setPositiveButton("图库", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				GoodsEdit.this.startActivityForResult(intent,
						PHOTO_REQUEST_GALLERY);
			}
		});
		builder.setNegativeButton("拍照", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
				if (hasSd()) {
					tempFile = new File(Environment
							.getExternalStorageDirectory(), PHOTO_FILE_NAME);
					Uri ui = Uri.fromFile(tempFile);
					i.putExtra(MediaStore.EXTRA_OUTPUT, ui);
				}
				GoodsEdit.this.startActivityForResult(i, PHOTO_REQUEST_CAREMA);
			}
		});
		mydialog = builder.create();

	}

	public Boolean hasSd() {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public void cut(Uri ui) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(ui, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);

		// System.out.println("剪切"+"  "+1);
		GoodsEdit.this.startActivityForResult(intent, PHOTO_CUT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		System.out.println("回应" + "  " + requestCode);

		switch (requestCode) {
		case 1: { // System.out.println("回应后："+" 1 "+data);
			if (data != null) {
				Uri ui = data.getData();
				cut(ui);
				// System.out.println(number+"  "+1);
			}
		}
			break;
		case 2: {
			System.out.println("回应后" + " 2 " + hasSd());
			if (hasSd()) {
				cut(Uri.fromFile(tempFile));
				// System.out.println(number+"  "+2);
			} else {
				Toast.makeText(GoodsEdit.this, "没有SD卡无法存储", 0).show();
			}
		}
			break;
		case 3: {
			if (data != null) {
				Bitmap bitmap = data.getParcelableExtra("data");
				groupbit.add(bitmap);
				myadapter = new MyAdapter(GoodsEdit.this, groupbit);
				Mygallery.setAdapter(myadapter);
			}
			try {
				// tempFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mark.market.ui.MarketAcitivity#refresh(int, java.lang.Object[])
	 */
	@Override
	public void refresh(int taskID, Object... objects) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.myadd: {
			if (groupbit.size() < 6) {
				addImgAlert();
				mydialog.show();
			} else {
				Toast.makeText(GoodsEdit.this, "最多只能添加6张图片呦～",
						Toast.LENGTH_SHORT).show();
			}

		}
			break;
		case R.id.good_edit_sale: {
			if (holder.edit_title.getText() != null)
				good_info.put("gname", holder.edit_title.getText());
			if (holder.edit_describ.getText() != null)
				good_info.put("gdescription", holder.edit_describ.getText());
			if (holder.edit_price.getText() != null)
				good_info.put("gprice", holder.edit_price.getText());
			if (holder.edit_preprice.getText() != null)
				good_info.put("gprePrice", holder.edit_preprice.getText());
			good_info.put("gcategory",
					categorys[holder.edit_category.getSelectedItemPosition()]);
			good_info.put("gpalce",
					places[holder.edit_category.getSelectedItemPosition()]);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("info", params);
			params.put("imgs", groupbit);
			Task task = new Task(Task.SALE_GOOD, params);
			MainService.newTask(this, task);
			Log.w(TAG, "sale clicked!");

		}
			break;
		}
	}

}
