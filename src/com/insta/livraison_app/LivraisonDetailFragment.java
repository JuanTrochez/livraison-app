package com.insta.livraison_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LivraisonDetailFragment extends Fragment {
	
	public static int index;
	
	private ImageView callImage;
	private ImageView smsImage;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.livraison_detail, container, false);
	}
	
	public void update(int indice){
		
//		String[] values = new String[] { "Detail Android","Detail iPhone",
//				"Detail WindowsMobile","Detail Windows7", "Detail Max OS X",
//				"Detail Blackberry", "WebOS", "Detail Ubuntu",
//				"Detail Linux", "Detail OS/2" };
		
		ClientAppDataSource clientDataSource = new ClientAppDataSource(this.getContext());
		clientDataSource.open();
		
		final Client client = clientDataSource.getClientById(Livraison.dailyLivraisons.get(indice).getClient_id());
		Livraison livraison = Livraison.dailyLivraisons.get(indice);
		
		clientDataSource.close();
		
		
		TextView tvNomClient = (TextView)getView().findViewById(R.id.nom_Client);
		tvNomClient.setText("Nom: ".concat(client.getNom()));
		
		TextView tvPrenomClient = (TextView)getView().findViewById(R.id.prenom_Client);
		tvPrenomClient.setText("Prenom: ".concat(client.getPrenom()));
		
		TextView tvEmailClient = (TextView)getView().findViewById(R.id.email_Client);
		tvEmailClient.setText("Email: ".concat(client.getEmail()));
		
		TextView tvAdresseClient = (TextView)getView().findViewById(R.id.adresse_Client);
		tvAdresseClient.setText("Adresse: ".concat(livraison.getNumero().concat(" ".concat(livraison.getAdresse().concat(" ".concat(livraison.getVille()))))));
		
		TextView tvStatutLivraison = (TextView)getView().findViewById(R.id.statut_Client);
		if(livraison.getStatut() == 0)
		{
			tvStatutLivraison.setText("Statut: En cours");
		}else if(livraison.getStatut() == 1)
		{
			tvStatutLivraison.setText("Statut: Terminé");
		}	
		
		TextView tvTelephoneClient = (TextView)getView().findViewById(R.id.telephone_Client);
		tvTelephoneClient.setText("Tel: ".concat(client.getTelephone()));
		
		callImage = (ImageView) getView().findViewById(R.id.imageCall);
		
		callImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:".concat(client.getTelephone())));
				startActivity(callIntent);
			}
		});
		
		smsImage = (ImageView) getView().findViewById(R.id.imageSms);
		
		smsImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:".concat(client.getTelephone())));
				startActivity(callIntent);
			}
		});
		
	}	
	public void onResume()
	{
		super.onResume();
		this.update(index);
	}
}
