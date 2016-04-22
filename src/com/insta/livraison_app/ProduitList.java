package com.insta.livraison_app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ProduitList extends Activity{
	
	private CustomAdapterProduit myCustomAdapter;
	private ProduitAppDataSource produitDataSource;
	private List<Produit> produits;
	private ListView lv;
	private LivraisonAppDataSource livraison;
	private Context context = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {         

       super.onCreate(savedInstanceState);    
       setContentView(R.layout.produitlist);
       produitDataSource = new ProduitAppDataSource(this);
       livraison = new LivraisonAppDataSource(this);
//       Toast.makeText(this, "valeur int : " + Produit.id_livraisonList, Toast.LENGTH_LONG).show();
       produitDataSource.open();     
       produits = produitDataSource.getAllProduitByLivraison(Produit.id_livraisonList);
       produitDataSource.close();
       
       myCustomAdapter = new CustomAdapterProduit(this,produits);
       
       lv = (ListView) findViewById(android.R.id.list);
       lv.setAdapter(myCustomAdapter);
       Button button = (Button) findViewById(R.id.ValiderProduit);
       
       button.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			produitDataSource.open();
			livraison.open();
			
			for(int i=0; i <myCustomAdapter.getCount(); i++)
			{
				String comment = ((EditText) lv.getChildAt(i).findViewById(R.id.produitdetailCommentaire)).getText().toString();
			    boolean checked = ((CheckBox)lv.getChildAt(i).findViewById(R.id.check)).isChecked();
			    int statut = 0;	
//			    Toast.makeText(context, "comment = " + checked, Toast.LENGTH_LONG).show();
			    if(checked){
			    	statut = 1;
			    }
			    int idProduit = produits.get(i).getIdWebService();
			    int idLivraison = produits.get(i).getLivraisonId();
			    produitDataSource.update(idProduit, comment, statut);
			    livraison.update(idLivraison, 2);		    
			}
			
			
			if(LivraisonActivity.isConnectionEnabled){
				Produit.produit = produitDataSource.getAllProduitToUpdateWebservice();
				PostRequestAsync request = new PostRequestAsync(context, Produit.produit);
        		String token = LivraisonActivity.generateToken(context);
        		try {
					request.execute("http://livraison-app.esy.es/?json=livraison&token="+ URLEncoder.encode( token,"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			finish();
		}
    	   
       });
       
       
       //rest of the code
   }
	
	public void onStart(){
		super.onStart();
	}
	
	public void onResume(){
		super.onResume();
	}
}
