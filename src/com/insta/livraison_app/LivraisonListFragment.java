package com.insta.livraison_app;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class LivraisonListFragment extends ListFragment {
	
	LivraisonDetailFragment detailView;
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
//		LivraisonAppDataSource livraisons = new LivraisonAppDataSource(null);		
//		List<String> dailyLivraisons = livraisons.getAll();
		
		String[] values = new String[]{"Android","iPhone","WindowsMobile","Blackberry","WebOs","Ubuntu","Windows7","Mac OS X","Linux","OS/2"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		
		detailView = (LivraisonDetailFragment) getFragmentManager().findFragmentById(R.id.detail_fragment);

	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		LivraisonDetailFragment.index = position;
		if (detailView == null || !detailView.isInLayout()) {
			//mode portrait
			Intent intent = new Intent();
			intent.setClass(getActivity(), LivraisonDetailActivity.class); 
			intent.putExtra("index", position); 
			startActivity(intent);
		}else{
			//mode paysage
			detailView.update(position);
		}		
	}
	
	public static void updateLivraisonDb(JSONObject datas, Context context) throws JSONException {
		Toast.makeText(context.getApplicationContext(), "inserting in db", Toast.LENGTH_LONG).show();
		
		JSONObject livraisons = datas.getJSONObject("livraisons");
		JSONArray lastDaysLivraisons = livraisons.getJSONArray("priority");
		JSONArray dailyLivraisons = livraisons.getJSONArray("day");

		
		LivraisonAppDataSource LivraisonDataSource = new LivraisonAppDataSource(context.getApplicationContext());
		LivraisonDataSource.open();
		
		for (int i = 0; i < lastDaysLivraisons.length(); i++) {

			JSONObject livraison = lastDaysLivraisons.getJSONObject(i);
			
			int id = Integer.parseInt(livraison.getString("id"));
			String adresse = livraison.getString("adresse");
			String numero = livraison.getString("numero");
			String postal = livraison.getString("code_postal");
			String ville = livraison.getString("ville");
			String latitude = livraison.getString("latitude");
			String longitude = livraison.getString("longitude");
			String date = livraison.getString("date");
			String distance = livraison.getString("distance");
			String duration = livraison.getString("duration");
			int statut = Integer.parseInt(livraison.getString("statut"));
			int client_id = Integer.parseInt(livraison.getString("client_id"));
			int livreur_id = Integer.parseInt(livraison.getString("livreur_id"));

			Toast.makeText(context.getApplicationContext(), "Livraison id " + id, Toast.LENGTH_LONG).show();			
			
			LivraisonDataSource.insertLivraison(id, adresse, numero, postal, ville, latitude, 
					longitude, date, duration, distance, statut, client_id, livreur_id);
		}
		
		for (int i = 0; i < dailyLivraisons.length(); i++) {

			JSONObject livraison = dailyLivraisons.getJSONObject(i);
			
			int id = Integer.parseInt(livraison.getString("id"));
			String adresse = livraison.getString("adresse");
			String numero = livraison.getString("numero");
			String postal = livraison.getString("code_postal");
			String ville = livraison.getString("ville");
			String latitude = livraison.getString("latitude");
			String longitude = livraison.getString("longitude");
			String date = livraison.getString("date");
			String distance = livraison.getString("distance");
			String duration = livraison.getString("duration");
			int statut = Integer.parseInt(livraison.getString("statut"));
			int client_id = Integer.parseInt(livraison.getString("client_id"));
			int livreur_id = Integer.parseInt(livraison.getString("livreur_id"));

			Toast.makeText(context.getApplicationContext(), "Livraison id " + id, Toast.LENGTH_LONG).show();			
			
			LivraisonDataSource.insertLivraison(id, adresse, numero, postal, ville, latitude, 
					longitude, date, duration, distance, statut, client_id, livreur_id);
		}		
	}

}
