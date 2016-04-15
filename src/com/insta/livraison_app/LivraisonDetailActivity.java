package com.insta.livraison_app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class LivraisonDetailActivity extends FragmentActivity {

	LivraisonDetailFragment viewer;
	int indice;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		indice = getIntent().getExtras().getInt("index");
		viewer = new LivraisonDetailFragment();
		
		FragmentManager fragmentmanager = getSupportFragmentManager();
		FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
		fragmenttransaction.add(android.R.id.content, viewer);
		fragmenttransaction.commit();
	
	}
	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume(); 
		viewer.update(indice);
	}

}
