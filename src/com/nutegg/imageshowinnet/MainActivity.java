package com.nutegg.imageshowinnet;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText et;
	TextView et21;
	ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et = (EditText)this.findViewById(R.id.editText1);
		iv = (ImageView)this.findViewById(R.id.iv);
		et21 = (TextView)this.findViewById(R.id.tv21);
		
	}
	

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//super.handleMessage(msg);
			System.out.println("OK!!!!!!!!!");
			if(msg.what == 1){
				Toast.makeText(MainActivity.this, "图片获取成功", Toast.LENGTH_SHORT).show();
				et21.setText("图片获取成功");
				System.out.println("YES!!!!!!!!!");
				Bitmap bitmap = (Bitmap)msg.obj;
				System.out.println(bitmap);
				iv.setImageBitmap(bitmap);
				
			}
		}
		
	};

	public void showImage(View view){
		Toast.makeText(this, "开始获取图片", Toast.LENGTH_SHORT).show();
		final String url = et.getText().toString().trim();
		new Thread(){
			public void run(){
				try {
					URL imageUrl = new URL(url);
					if(imageUrl != null){
						HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
						conn.setRequestMethod("GET");
						conn.setConnectTimeout(5000);
						conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; Sicent; .NET CLR 2.0.50727)");
						int code = conn.getResponseCode();
						if(code == 200){
							InputStream is = conn.getInputStream();
							Bitmap bitmap = BitmapFactory.decodeStream(is);
							//iv.setImageBitmap(bitmap);
							Message msg = new Message();
							msg.what = 1;
							msg.obj = bitmap;
							handler.sendMessage(msg);
							System.out.println("yaoxi!!!!!!!");
						}else{
							Toast.makeText(MainActivity.this, "获取图片失败", Toast.LENGTH_SHORT);
						}
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
}
