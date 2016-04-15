package com.insta.livraison_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LivraisonDetailFragment extends Fragment {
	
	public static int index;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.livraison_detail, container, false);
	}
	
	public void update(int indice){
		String[] values = new String[] { "Detail Android","Detail iPhone",
				"Detail WindowsMobile","Detail Windows7", "Detail Max OS X",
				"Detail Blackberry", "WebOS", "Detail Ubuntu",
				"Detail Linux", "Detail OS/2" };

		TextView textview = (TextView)getView().findViewById(R.id.detailTextView);
		textview.setText(values[indice]);
	}	
	public void onResume()
	{
		super.onResume();
		this.update(index);
	}


}
