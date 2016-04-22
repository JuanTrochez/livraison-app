package com.insta.livraison_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CustomAdapterProduit extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private List<Produit> values;
	
	public CustomAdapterProduit(Context context, List<Produit> data) {
		this.mInflater = LayoutInflater.from(context);
		this.values = data;
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
		
		LinearLayout view = (LinearLayout) convertView;
	     if (view == null) {
	          view = (LinearLayout) mInflater.inflate(R.layout.produitlistdetail, parent, false);
	     }

	     
	     TextView tvproduitdetailRef = (TextView) view.findViewById(R.id.produitdetailRef);
	     tvproduitdetailRef.setText(values.get(position).getQuantite());
	     
	     TextView tvproduitdetailQuantite = (TextView) view.findViewById(R.id.produitdetailQuantite);
	     tvproduitdetailQuantite.setText("(".concat(values.get(position).getReference().concat(" produit(s))")));
//	     tvproduitdetailQuantite.setText("(".concat(values.get(position).getQuantite().concat(" produit(s)")));
	     
	     EditText et = (EditText) view.findViewById(R.id.produitdetailCommentaire);
	     
	     et.addTextChangedListener(new TextWatcher() {
	    	 String nText = "";
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				nText = s.toString();
			}
	    	 
	     });
	     
	     CheckBox cBox = (CheckBox) view.findViewById(R.id.check);
	     
	     cBox.setTag(Integer.valueOf(position)); // set the tag so we can identify the correct row in the listener
	     
	     cBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
	 	{
	 	    @Override
	 	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	 	    {
	 	       int position = (Integer) buttonView.getTag();
	 	       if (buttonView.isChecked()) {
					//do Something
				} else {
					//do something else
				}	
	 	    }
	 	});
	     return view;
	}

	public void swapItems(List<Produit> items) {
	    this.values = items;
	    notifyDataSetChanged();
	}

}
