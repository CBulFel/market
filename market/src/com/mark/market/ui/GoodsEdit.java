package com.mark.market.ui;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Toast;

import com.mark.market.R;

public class GoodsEdit extends Activity {
	private Gallery Mygallery;
	private ArrayList<Bitmap> groupbit;
	private Bitmap selectbit;
	private Button add, delete;
	private MyAdapter myadapter;
	private AlertDialog mydialog;
	private int number;
	private static int PHOTO_REQUEST_GALLERY = 1;
	private static int PHOTO_REQUEST_CAREMA = 2;
	private static int PHOTO_CUT = 3;
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private File tempFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodsedit);
		add = (Button) findViewById(R.id.myadd);
		Mygallery = (Gallery) findViewById(R.id.mygallery);
		groupbit = new ArrayList<Bitmap>();
		myadapter = new MyAdapter(this, groupbit);
		Mygallery.setAdapter(myadapter);
		// 添加图片
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				addImgAlert();
				mydialog.show();
			}
		});
		// 查看图片
		Mygallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				/*
				 * LayoutInflater li=getLayoutInflater(); View
				 * vi=li.inflate(R.layout.alertimg, null); showimg=(ImageView)
				 * vi.findViewById(R.id.showimage);
				 */
				selectbit = groupbit.get(position);
				WindowManager manager = getWindowManager();
				Display display = manager.getDefaultDisplay();
				int width = display.getWidth();
				int height = display.getHeight();
				AlertDialog alert = new AlertDialog.Builder(GoodsEdit.this)
						.create();
				alert.show();
				alert.setTitle("查看");
				alert.getWindow().setLayout(width / 2, height / 2);
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
				delete = (Button) view.findViewById(R.id.mybutton);

				number = position;
				delete.setOnClickListener(new OnClickListener() {

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
				// TODO 自动生成的方法存根

			}
		});

	}

	public void addImgAlert() {
		AlertDialog.Builder bu = new AlertDialog.Builder(this);
		bu.setTitle("请选择图片");
		bu.setPositiveButton("从图库中选择", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				Intent i = new Intent(Intent.ACTION_PICK);
				i.setType("image/*");
				GoodsEdit.this.startActivityForResult(i,
						PHOTO_REQUEST_GALLERY);
			}
		});
		bu.setNegativeButton("手机拍照相片", new DialogInterface.OnClickListener() {

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
				GoodsEdit.this.startActivityForResult(i,
						PHOTO_REQUEST_CAREMA);
			}
		});
		mydialog = bu.create();

	}

	public Boolean hasSd() {
		/*
		 * if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED
		 * )) { return true; }else{return false;}
		 */
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public void cut(Uri ui) {
		Intent i = new Intent("com.android.camera.action.CROP");
		i.setDataAndType(ui, "image/*");
		i.putExtra("crop", "true");
		i.putExtra("outputX", 250);
		i.putExtra("outputY", 250);
		i.putExtra("outputFormat", "JPEG");// ͼƬ��ʽ
		i.putExtra("noFaceDetection", true);// ȡ������ʶ��
		i.putExtra("return-data", true);
		// ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CUT
		// System.out.println("剪切"+"  "+1);
		GoodsEdit.this.startActivityForResult(i, PHOTO_CUT);
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
				// my.setImageBitmap(bitmap);
				groupbit.add(bitmap);
				myadapter = new MyAdapter(GoodsEdit.this, groupbit);
				Mygallery.setAdapter(myadapter);
				// System.out.println(bitmap+"  "+3);
				// my.setImageBitmap(groupbit.get(number));
				// number++;

			}
			try {
				tempFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;

		}
		/*
		 * if(requestCode==1){ System.out.println("回应后："+" 1 "+data);
		 * if(data!=null) { Uri ui=data.getData(); cut(ui);
		 * System.out.println(number+"  "+1); }else if(requestCode==2){
		 * System.out.println("回应后"+" 2 "+hasSd()); if(hasSd()){
		 * cut(Uri.fromFile(tempFile)); System.out.println(number+"  "+2); }else
		 * { Toast.makeText(MainActivity.this, "没有SD卡无法存储", 0).show(); } }else
		 * if(requestCode==3){ System.out.println("回应后："+" 3 "+data);
		 * if(data!=null){ Bitmap bitmap = data.getParcelableExtra("data");
		 * groupbit[number]=bitmap; number++; System.out.println(number+"  "+3);
		 * }try{ tempFile.delete(); }catch(Exception e){ e.printStackTrace(); }
		 * } }
		 */
		super.onActivityResult(requestCode, resultCode, data);

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

}
