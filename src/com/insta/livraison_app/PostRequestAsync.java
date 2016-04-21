package com.insta.livraison_app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class PostRequestAsync extends AsyncTask<String, Integer, Double> {

	private Context context;
	private ArrayList<Produit> produit;
	
	public PostRequestAsync(Context context,ArrayList<Produit> produit ){
		this.context = context;
		this.produit = produit;
	}
	@Override
	protected Double doInBackground(String... params) {
		postData(params[0]);
		return null;
	}

	public void postData(String url)
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				
		for(int i = 0; i < produit.size(); i++){
					
			Produit value = produit.get(i);
					
			nameValuePairs.add(new BasicNameValuePair("id"+i, Integer.toString(value.getIdWebService())));
		    nameValuePairs.add(new BasicNameValuePair("statut"+i, Integer.toString(value.getStatut())));
		    nameValuePairs.add(new BasicNameValuePair("commentaire"+i, value.getCommentaire()));
		}
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			// Execute HTTP Post Request
		    HttpResponse response = httpclient.execute(httppost);
		    
		    String json_string = EntityUtils.toString(response.getEntity());
//		    JSONObject temp1 = new JSONObject(json_string);
		    
		    JSONObject jsonObject = new JSONObject(json_string);
		    JSONObject datas = jsonObject.getJSONObject("data");
		    
		    LivraisonActivity.livraisonDatas = datas;
		    Intent intentMessage = new Intent(LivraisonListFragment.dbUpdated);
		    context.sendBroadcast(intentMessage);
		    
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
