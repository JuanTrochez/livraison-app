package com.insta.livraison_app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.insta.livraison_app.LivraisonActivity.connexionOpen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class LivraisonListFragment extends ListFragment {
	
	LivraisonDetailFragment detailView;
	private LivraisonAdapter adapter;
	public static String dbUpdated = "dbUpdated";
	private BroadcastDbUpdate broadcastDbUpdate;
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

		broadcastDbUpdate = new BroadcastDbUpdate();
		IntentFilter filter = new IntentFilter();
		filter.addAction(LivraisonListFragment.dbUpdated);
        getActivity().registerReceiver(broadcastDbUpdate, filter);
		
        ClientAppDataSource clientDataSource = new ClientAppDataSource(this.getContext());
		LivraisonAppDataSource LivraisonDataSource = new LivraisonAppDataSource(this.getContext());
		LivraisonDataSource.open();	
		clientDataSource.open();	
		Livraison.dailyLivraisons = LivraisonDataSource.getDailyLivraisons();
		
		int countLivraisons = LivraisonDataSource.getAll();
		
		Toast.makeText(this.getContext(), "rows in db" + countLivraisons, Toast.LENGTH_LONG).show();
		
		for (int i = 0; i < Livraison.dailyLivraisons.size(); i++) {
			Livraison.dailyLivraisons.get(i).setClient(clientDataSource.getClientById(Livraison.dailyLivraisons.get(i).getClient_id()));
		}

		LivraisonDataSource.close();
		clientDataSource.close();
		
		adapter = new LivraisonAdapter(this.getContext(), Livraison.dailyLivraisons);
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
	
	public class BroadcastDbUpdate extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
						
				try {
					LivraisonActivity.updateLivraisonDb(LivraisonActivity.livraisonDatas, context);
					
					ClientAppDataSource clientDataSource = new ClientAppDataSource(getActivity().getApplicationContext());
					LivraisonAppDataSource LivraisonDataSource = new LivraisonAppDataSource(getActivity().getApplicationContext());
					LivraisonDataSource.open();	
					clientDataSource.open();	
					Livraison.dailyLivraisons = LivraisonDataSource.getDailyLivraisons();				

					int countLivraisons = LivraisonDataSource.getAll();					
					Toast.makeText(getActivity().getApplicationContext(), "rows in db" + countLivraisons, Toast.LENGTH_LONG).show();

					for (int i = 0; i < Livraison.dailyLivraisons.size(); i++) {
						Livraison.dailyLivraisons.get(i).setClient(clientDataSource.getClientById(Livraison.dailyLivraisons.get(i).getClient_id()));
					}
					LivraisonDataSource.close();
					clientDataSource.close();
					
					adapter = new LivraisonAdapter(getActivity().getApplicationContext(), Livraison.dailyLivraisons);
					setListAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
					
		}		

	}
}
