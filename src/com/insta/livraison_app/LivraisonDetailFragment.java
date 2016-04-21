package com.insta.livraison_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LivraisonDetailFragment extends Fragment {
	
	public static int index;
	
	private ImageView callImage;
	private ImageView smsImage;
	private ImageView gpsImage;
	private Button ListProduits;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.livraison_detail, container, false);
	}
	
	public void update(int indice){
		
		ClientAppDataSource clientDataSource = new ClientAppDataSource(this.getContext());
		clientDataSource.open();
		
		final Client client = clientDataSource.getClientById(Livraison.dailyLivraisons.get(indice).getClient_id());
		final Livraison livraison = Livraison.dailyLivraisons.get(indice);
		
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
				buildAlertMessageSms(client);
				
			}
		});
		
		gpsImage= (ImageView) getView().findViewById(R.id.imageGPS);
		
		gpsImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final LocationManager manager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );
				
				if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
				{
					buildAlertMessageNoGps();
				}
				
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse("http://maps.google.com/maps?saddr="+ LivraisonActivity.location.getLatitude()+","+ LivraisonActivity.location.getLongitude()+"&daddr="+livraison.getLatitude()+","+livraison.getLongitude()));
				startActivity(intent);
			}
		});
		
		ListProduits = (Button) getView().findViewById(R.id.ListProduits);
		
		ListProduits.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), ProduitList.class);
				Produit.id_livraisonList =  livraison.getId_webService();
				startActivity(intent);
			}
		});
		
		
	}	
	public void onResume()
	{
		super.onResume();
		this.update(index);
	}
	
	private void buildAlertMessageSms(final Client client){
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(this.getContext());
		
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.select_dialog_singlechoice);
		arrayAdapter.add("Bonjour Madame,Monsieur : " + client.getNom() + " je vous informe que votre colis est en cours de livraison");
		arrayAdapter.add("Bonjour Madame,Monsieur : " + client.getNom() + " je vous informe que votre colis ne pourra malheureusement pas être livré aujourd'hui");
		arrayAdapter.add("Bonjour Madame,Monsieur : " + client.getNom() + " je vous informe que votre colis arrivera avec du retard");

		builderSingle.setNegativeButton(
		        "cancel",
		        new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		                dialog.dismiss();
		            }
		        });

		builderSingle.setAdapter(
		        arrayAdapter,
		        new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		                final String strName = arrayAdapter.getItem(which);
		                AlertDialog.Builder builderInner = new AlertDialog.Builder(LivraisonDetailFragment.this.getContext());
		                builderInner.setMessage(strName);
		                builderInner.setTitle("Votre message est le suivant : ");
		                builderInner.setPositiveButton(
		                        "Envoyer",
		                        new DialogInterface.OnClickListener() {
		                            @Override
		                            public void onClick( DialogInterface dialog,int which) {
		                            	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + client.getTelephone()));     
		                				intent.putExtra("sms_body", strName); 
		                				startActivity(intent);
		                                dialog.dismiss();
		                            }
		                        });
		                builderInner.show();
		            }
		        });
		builderSingle.show();
	}
	
	private void buildAlertMessageNoGps() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
	    builder.setMessage("Votre GPS semble être désactivé, voulez vous le réactiver ?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, final int id) {
	                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, final int id) {
	                    dialog.cancel();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}
}
