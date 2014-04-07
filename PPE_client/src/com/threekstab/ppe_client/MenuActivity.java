package com.threekstab.ppe_client;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {
	
	//private final String url = ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		addListenerOnButton();
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
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
public void addListenerOnButton(){
		
		final Context context = this;
		
		final Button returnButton = (Button) findViewById(R.id.button1);
		final Button validateButton = (Button) findViewById(R.id.button1);
		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,MainActivity.class);
				intent.putExtra("keyName","value");
				startActivity(intent);
			}
			
		});
		
		validateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText etX = (EditText) findViewById(R.id.editText1);
				EditText etY = (EditText) findViewById(R.id.editText2);
				EditText etZ = (EditText) findViewById(R.id.editText3);
				new SendService().execute("x:"+etX.getText(),"y:"+etY.getText(),"z:"+etZ.getText());
				Intent intent = new Intent(context,MainActivity.class);
				startActivity(intent);
			}
			
		});
	}

}
