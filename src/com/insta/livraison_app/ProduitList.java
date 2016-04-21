package com.insta.livraison_app;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class ProduitList extends Activity{
	
	private CustomAdapterProduit myCustomAdapter;
	private ProduitAppDataSource produitDataSource;
	private List<Produit> produits;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {         

       super.onCreate(savedInstanceState);    
       setContentView(R.layout.produitlist);
       produitDataSource = new ProduitAppDataSource(this);
       Toast.makeText(this, "valeur int : " + Produit.id_livraisonList, Toast.LENGTH_LONG).show();
       produitDataSource.open();     
       produits = produitDataSource.getAllProduitByLivraison(Produit.id_livraisonList);
       produitDataSource.close();
       
       myCustomAdapter = new CustomAdapterProduit(this,produits);
       
       ListView lv = (ListView) findViewById(android.R.id.list);
       lv.setAdapter(myCustomAdapter);
       
       //rest of the code
   }
	
	public void onStart(){
		super.onStart();
	}
	
	public void onResume(){
		super.onResume();
	}
}
