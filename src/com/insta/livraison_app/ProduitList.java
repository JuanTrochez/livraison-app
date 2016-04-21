package com.insta.livraison_app;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ProduitList extends Activity{
	
	private CustomAdapterProduit myCustomAdapter;
	private ProduitAppDataSource produitDataSource;
	private List<Produit> produits;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {         

       super.onCreate(savedInstanceState);    
       setContentView(R.layout.produitlist);
       produitDataSource = new ProduitAppDataSource(this);
       
//       produits = produitDataSource
       myCustomAdapter = new CustomAdapterProduit(this,produits);
       
       ListView lv = (ListView) findViewById(android.R.id.list);
       lv.setAdapter(myCustomAdapter);
       
       //rest of the code
   }
}
