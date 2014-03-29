package com.threekstab.ppe_client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {
	private VideoView videoView;
	private WebView wv;
	private String ipServer = "192.168.43.64";
	private static String port = "8554";
	private String link = "rtsp://192.168.43.64:8554/stream.sdp";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
		/*videoView =(VideoView)findViewById(R.id.videoView1);
		MediaController mediaController= new MediaController(this);
	    mediaController.setAnchorView(videoView);
	    videoView.setMediaController(mediaController);
	    initVideo();*/
		wv = (WebView) findViewById(R.id.webView1);
		initWebBrowser();	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		/* test passing argument from other activity */
		String data = "no data received yet";
		if(getIntent()!=null)
			if(getIntent().getExtras()!=null)
				data = getIntent().getExtras().getString("keyName");
		
		/* toast for test */
		Context context = getApplicationContext();
		CharSequence text = "Activity received : "+data;
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addListenerOnButton(){
		
		final Context context = this;
		
		final Button button = (Button) findViewById(R.id.button1);
		final Button button2 = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,MenuActivity.class);
				intent.putExtra("keyName","value");
				startActivity(intent);
			}
			
		});
		button2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				EditText et = (EditText) findViewById(R.id.editText1);
				MainActivity.this.link = et.getText().toString();
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText(MainActivity.this.link);
				initVideo();
			}
			
		});
	}
	
	private void initVideo()
	{
		

	    // Uri uri = 	Uri.parse("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov");
	     //Uri uri = Uri.parse("rtsp://"+ipServer+":"+port+"/stream.sdp");
	    //Uri uri = Uri.parse("rtp://@:5004");
	    PopupWindow popup = new PopupWindow(this);
	    popup.showAtLocation(new LinearLayout(this), Gravity.BOTTOM, 50, 50);
	    popup.update(50,50,300,80);
	    Uri uri = Uri.parse(link);

	    
	    videoView.setVideoURI(uri);
	    videoView.requestFocus();
	    videoView.start();
	}
	
	private void initWebBrowser(){
		 //getWindow().requestFeature(Window.FEATURE_PROGRESS);

		 wv.getSettings().setJavaScriptEnabled(true);

		 final Activity activity = this;
		 wv.setWebChromeClient(new WebChromeClient() {
		   public void onProgressChanged(WebView view, int progress) {
		     // Activities and WebViews measure progress with different scales.
		     // The progress meter will automatically disappear when we reach 100%
		     activity.setProgress(progress * 1000);
		   }
		 });
		 wv.setWebViewClient(new WebViewClient() {
		   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		     Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
		   }
		 });

		 //wv.loadUrl(getResources().openRawResource(R.raw.streamexample));
		 wv.loadData(readTextFromResource(R.raw.streamexample), "text/html", "utf-8");
	}
	
	private String readTextFromResource(int resourceID)
	{
	    InputStream raw = getResources().openRawResource(resourceID);
	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    int i;
	    try
	    {
	        i = raw.read();
	        while (i != -1)
	        {
	            stream.write(i);
	            i = raw.read();
	        }
	        raw.close();
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return stream.toString();
	}



}
