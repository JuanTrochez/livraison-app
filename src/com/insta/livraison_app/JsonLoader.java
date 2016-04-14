package com.insta.livraison_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class JsonLoader extends AsyncTask<String,Integer,StringBuffer>{

	private final WeakReference<TextView> jsonTV;
	private final WeakReference<View> mainActivity;
	
	public JsonLoader(TextView jsonTv, View v){
		this.jsonTV = new WeakReference<TextView>(jsonTv);
		this.mainActivity = new WeakReference<View>(v);
	}
	
	@Override
	protected StringBuffer doInBackground(String... params) {
		String url = params[0];
		HttpURLConnection urlConnection = null;
		
		URL urlCo;
		try {
			urlCo = new URL(url);
//			publishProgress(0);
//			Thread.sleep(4000);
			urlConnection  = (HttpURLConnection) urlCo.openConnection();
			int statusCode = urlConnection.getResponseCode();
	        if (statusCode != HttpURLConnection.HTTP_OK) {
	            return null;
	        }
	        
	        InputStream inputStream = urlConnection.getInputStream();
	        if (inputStream != null) {
	            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
	            StringBuilder sBuilder = new StringBuilder();

	            String line = null;
	            while ((line = bReader.readLine()) != null) {
	                sBuilder.append(line + "\n");
	            }

	            inputStream.close();
	            StringBuffer result = new StringBuffer(sBuilder.toString());
	            return result;
	        }        
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (urlConnection != null) {
	            urlConnection.disconnect();
	        }
		}
		return null;
	}
	
	protected void onPostExecute(StringBuffer json)
	{
		try {
			JSONObject jsonObject = new JSONObject(json.toString());
			JSONObject resultats = jsonObject.getJSONObject("infos");
			Choice choice = null;
			
			try{
				choice = Choice.valueOf(resultats.getString("data"));
			}catch(Exception e){
			}
			
			switch(choice){
			case login:
				if(resultats.getString("valide").equals("true"))
				{
					//TODO Appeler la nouvelle activité pour la liste des livraisons
				}else{
					LayoutInflater layoutInflater = (LayoutInflater)mainActivity.get().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
					View popupView = layoutInflater.inflate(R.layout.popup, null); 
					final PopupWindow popupWindow = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
				    Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
				    popupWindow.showAtLocation(mainActivity.get(), Gravity.CENTER, 0, mainActivity.get().getHeight());
				    btnDismiss.setOnClickListener(new Button.OnClickListener(){					    	
					    @Override
					    public void onClick(View v) {
					     // TODO Auto-generated method stub
					     popupWindow.dismiss();
					    }});
				}
				break;
			default :
				
			}
				

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private enum Choice{
		login,livraison;
	}
}
