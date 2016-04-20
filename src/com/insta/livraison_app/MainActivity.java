package com.insta.livraison_app;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity {

	private String date = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Calendar c = Calendar.getInstance();
		
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy");
		date = df.format(c.getTime());
		
		
		findViewById(R.id.Envoyer).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				try {
					EditText loginET = (EditText) findViewById(R.id.Login);
					EditText passwordET = (EditText) findViewById(R.id.Password);
					if(!loginET.getText().toString().equals("") && !passwordET.getText().toString().equals(""))
					{
						String login = loginET.getText().toString();
						String password1 = ConvertSha1.SHA1("lol");
						String password = passwordET.getText().toString();
						
						
						String allConcat = login.concat("|".concat(password).concat("|".concat(date)));
						String result = Base64.encodeToString(allConcat.getBytes(), Base64.DEFAULT);
						connect(login,password,v,result);
							
					}else{
						
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
		});
		
		findViewById(R.id.ShowPassWord).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) findViewById(R.id.ShowPassWord);
				EditText passwordET = (EditText) findViewById(R.id.Password);
				if(cb.isChecked())
				{
					passwordET.setTransformationMethod(new PasswordTransformationMethod());
				}else{
					passwordET.setTransformationMethod(null);
				}
				
			}
			
		});
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
	
	public void connect(String login, String password, View v, String result) throws InterruptedException, ExecutionException
	{

		ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetInfo =   connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (wifiNetInfo.isConnected() || mobNetInfo.isConnected()) {
			JsonLoader getJsonConnection = new JsonLoader(login.concat("|".concat(password)), v, (CheckBox)findViewById(R.id.SaveData));
			getJsonConnection.execute("http://livraison-app.esy.es/?json=login&token=".concat(result)).get();
		} else {
			this.buildAlertMessageNoConnection();
		}
	}
	
	public void onResume(){
		super.onResume();
	}
	public void onStart(){
		super.onStart();
		Toast.makeText(this, "on start main activity", Toast.LENGTH_LONG).show();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String username = prefs.getString("username", "");
		String password = prefs.getString("password", "");
		
		if(!username.equals("") && !password.equals(""))
		{
			Intent intent = new Intent(this, LivraisonActivity.class);
			startActivity(intent);
		 }
	}
	
	private void buildAlertMessageNoConnection() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Votre connexion semble être désactivée, nous ne pouvons actualiser les données")
	           .setPositiveButton("D'accord", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, final int id) {
	                    dialog.dismiss();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}
}
