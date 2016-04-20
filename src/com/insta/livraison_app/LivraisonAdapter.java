package com.insta.livraison_app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		RelativeLayout view = (RelativeLayout) convertView;
	     if (view == null) {
	          view = (RelativeLayout) mInflater.inflate(R.layout.livraison_list, parent, false);
	     }

	     TextView tvClient = (TextView) view.findViewById(R.id.client_list);
	     TextView tvAdresse = (TextView) view.findViewById(R.id.adresse_list);
	     TextView tvDistance = (TextView) view.findViewById(R.id.distance_list);
	     TextView tvDate = (TextView) view.findViewById(R.id.date_list);
	     
	     
	      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		  Date today = (Date) Calendar.getInstance().getTime();
		  String compareDate = df.format(today);
		  String valueDate = values.get(position).getDate();
		  try {
			Date comp1 = df.parse(compareDate);
			Date comp2 = df.parse(valueDate);
			
			if (comp1.compareTo(comp2) == 0) {
		    	 valueDate = "today";
		     }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     

	     tvClient.setText(values.get(position).getClient().getNom());
	     tvAdresse.setText(values.get(position).getAdresse());
	     tvDistance.setText(values.get(position).getDistance());
	     tvDate.setText(valueDate);
	     
		return view;
	}

}
