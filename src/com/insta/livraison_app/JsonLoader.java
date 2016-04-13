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

import android.os.AsyncTask;
import android.widget.TextView;

public class JsonLoader extends AsyncTask<String,Integer,StringBuffer>{

	private final WeakReference<TextView> jsonTV;
	
	public JsonLoader(TextView jsonTv){
		this.jsonTV = new WeakReference<TextView>(jsonTv);
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
			
			if(resultats.getString("data").equals("login"))
			{
				JSONObject	token = jsonObject.getJSONObject("data");
				jsonTV.get().append(token.getString("token"));
			}else{
				jsonTV.get().append("Rien trouvé morray"); 
			}	

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
