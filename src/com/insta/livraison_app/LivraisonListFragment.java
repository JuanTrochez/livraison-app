package com.insta.livraison_app;

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
	private ArrayAdapter<String> adapter;
	public static String dbUpdated = "dbUpdated";
	private broadcastDbUpdate broadcastDbUpdate;
	private List<String> dailyLivraisons;
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

		broadcastDbUpdate = new broadcastDbUpdate();
		IntentFilter filter = new IntentFilter();
		filter.addAction(LivraisonListFragment.dbUpdated);
        getActivity().registerReceiver(broadcastDbUpdate, filter);
		
		LivraisonAppDataSource LivraisonDataSource = new LivraisonAppDataSource(this.getContext());
		LivraisonDataSource.open();		
		dailyLivraisons = LivraisonDataSource.getAll();
		
		String[] values = new String[]{"Android","iPhone","WindowsMobile","Blackberry","WebOs","Ubuntu","Windows7","Mac OS X","Linux","OS/2"};
		
		adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, dailyLivraisons);
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
	
	public void update(Context context) {
		LivraisonAppDataSource LivraisonDataSource = new LivraisonAppDataSource(context);
		LivraisonDataSource.open();		
		List<String> dailyLivraisons = LivraisonDataSource.getAll();
		
		String[] values = new String[]{"Android","iPhone","WindowsMobile","Blackberry","WebOs","Ubuntu","Windows7","Mac OS X","Linux","OS/2"};
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dailyLivraisons);
		setListAdapter(adapter);
		detailView = (LivraisonDetailFragment) getFragmentManager().findFragmentById(R.id.detail_fragment);
	};
	
	public class broadcastDbUpdate extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			Toast.makeText(context.getApplicationContext(), "broadcastDbUpdate", Toast.LENGTH_LONG).show();
			
				try {
					LivraisonActivity.updateLivraisonDb(LivraisonActivity.livraisonDatas, context);
					
					LivraisonAppDataSource LivraisonDataSource = new LivraisonAppDataSource(getContext());
					LivraisonDataSource.open();		
					dailyLivraisons = LivraisonDataSource.getAll();
					adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, dailyLivraisons);
					setListAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
					
		}		

	}
}
