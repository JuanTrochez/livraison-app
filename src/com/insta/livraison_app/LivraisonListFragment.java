package com.insta.livraison_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LivraisonListFragment extends ListFragment {
	
	LivraisonDetailFragment detailView;
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] values = new String[]{"Android","iPhone","WindowsMobile","Blackberry","WebOs","Ubuntu","Windows7","Mac OS X","Linux","OS/2"};
		
		ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,values);
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

}
