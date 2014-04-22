package com.example.demojs;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("JavascriptInterface")
public class MainActivity extends Activity {
	
	WebView webView;
	Handler handler = new Handler();
	private Button eastBtn;
	private Button westBtn;
	private Button northBtn;
	private Button southBtn;
	private Button checkBtn;
	private Button changeMapBtn;
	
	private HashMap<String, OrientationPicBean> controllOrientationMap = new HashMap<String,OrientationPicBean>();
	
	
	private Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			// int[] data = (int[]) msg.obj;
			// for(int i=0;i<data.length;i++){
			// System.out.println(data[i]);
			// }
			OrientationPicBean bean = (OrientationPicBean) msg.obj;
			if (bean != null) {
				int data = (Integer) bean.getHeading();
				// System.out.println("hello:  "+data);

				if ((data > 0 && data <= 20) || (data >= 350 && data <= 390)) {
					northBtn.setEnabled(true);
					controllOrientationMap.put("north", bean);
				} else if (data >= 80 && data <= 110) {
					eastBtn.setEnabled(true);
					controllOrientationMap.put("east", bean);

				} else if (data >= 170 && data <= 200) {
					southBtn.setEnabled(true);
					controllOrientationMap.put("south", bean);
				} else if (data >= 260 && data <= 290) {
					westBtn.setEnabled(true);
					controllOrientationMap.put("west", bean);
				}

			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		eastBtn = (Button)findViewById(R.id.eastBtn);
		westBtn = (Button)findViewById(R.id.westBtn);
		northBtn = (Button)findViewById(R.id.northBtn);
		southBtn = (Button)findViewById(R.id.southBtn);
		
		checkBtn = (Button)findViewById(R.id.checkBtn);
		changeMapBtn = (Button)findViewById(R.id.changeMapBtn);
		webView = (WebView) findViewById(R.id.webView1);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.loadUrl("file:///android_asset/map.html");
	
		webView.setWebChromeClient(new MyWebChromeClient());
		
		webView.addJavascriptInterface(new Object() {

	
			public void show(final int heading,final String id) {
//				
				System.out.println("执行");
				handler.post(new Runnable() {
					@Override
					public void run() {
						/*
						 * 通过webView.loadUrl("javascript:xxx")方式就可以调用当前网页中的名称
						 * 为xxx的javascript方法
						 */
						//webView.loadUrl("javascript:show()");
						//System.out.println("haier: "+data.length);
						
						OrientationPicBean bean = new OrientationPicBean(heading,id);
						
						Message msg = myHandler.obtainMessage();
						msg.obj = bean;
						myHandler.sendMessage(msg);
					
					}
				});

		
			}
		}, "wanglei_java");

		
		eastBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				OrientationPicBean bean = (OrientationPicBean)controllOrientationMap.get("east");
//				if(bean != null)
				OrientationPicBean bean = controllOrientationMap.get("east");
				if(bean!=null){
					String id = bean.getId();
					int heading = bean.getHeading();
					if(id!=null && !id.equals(""))
						webView.loadUrl("javascript:east('"+id+"')");
				}
//				controllOrientationMap.clear();
//				webView.loadUrl("javascript:check()");
			}
			
		});
		
		westBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				OrientationPicBean bean = controllOrientationMap.get("west");
				if(bean!=null){
				String id = bean.getId();
				int heading = bean.getHeading();
					if(id!=null && !id.equals(""))
						webView.loadUrl("javascript:west('"+id+"')");
				
				}
//				controllOrientationMap.clear();
//				webView.loadUrl("javascript:check()");
			}
			
		});
		
		
		checkBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				eastBtn.setEnabled(false);
				westBtn.setEnabled(false);
				southBtn.setEnabled(false);
				northBtn.setEnabled(false);
				controllOrientationMap.clear();
				webView.loadUrl("javascript:check()");
			}
			
	});
		
		changeMapBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				webView.loadUrl("javascript:changeMap()");
			}
			
	});
		
	}
	
	
	public void refreshBtn(){
		eastBtn.setEnabled(false);
		westBtn.setEnabled(false);
		southBtn.setEnabled(false);
		northBtn.setEnabled(false);
		controllOrientationMap.clear();
		webView.loadUrl("javascript:check()");
	}
	class MyWebChromeClient extends WebChromeClient {

		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			Toast.makeText(getApplicationContext(), message,
					Toast.LENGTH_LONG).show();
			return true;
		}

	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
