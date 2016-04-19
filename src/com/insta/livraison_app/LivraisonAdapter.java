package com.insta.livraison_app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LivraisonAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Livraison> values;
	

	public LivraisonAdapter(Context context, ArrayList<Livraison> dailyLivraisons) {
		this.mInflater = LayoutInflater.from(context);
		this.values = dailyLivraisons;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return values.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return values.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout view = (LinearLayout) convertView;
	     if (view == null) {
	          view = (LinearLayout) mInflater.inflate(R.layout.livraison_list, parent, false);
	     }

	     TextView tvClient = (TextView) view.findViewById(R.id.client_list);
	     TextView tvAdresse = (TextView) view.findViewById(R.id.adresse_list);

	     tvClient.setText(values.get(position).getClient().getNom());
	     tvAdresse.setText(values.get(position).getAdresse());
	     
		return view;
	}

}
