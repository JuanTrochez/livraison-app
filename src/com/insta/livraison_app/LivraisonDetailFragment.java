package com.insta.livraison_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LivraisonDetailFragment extends Fragment {
	
	public static int index;
	private Button buttonCall;
	
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
		tvNomClient.setText(client.getNom());
		
		TextView tvPrenomClient = (TextView)getView().findViewById(R.id.prenom_Client);
		tvPrenomClient.setText(client.getPrenom());
		
		TextView tvEmailClient = (TextView)getView().findViewById(R.id.email_Client);
		tvEmailClient.setText(client.getEmail());
		
		TextView tvAdresseClient = (TextView)getView().findViewById(R.id.adresse_Client);
		tvAdresseClient.setText(livraison.getAdresse());
		
		TextView tvTelephoneClient = (TextView)getView().findViewById(R.id.telephone_Client);
		tvTelephoneClient.setText(client.getTelephone());
		
		buttonCall = (Button) getView().findViewById(R.id.buttonCall);
		
		// add PhoneStateListener
//		PhoneCallListener phoneListener = new PhoneCallListener();
//		TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
//		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
		
		buttonCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:0661294672"));
				startActivity(callIntent);
			}
		});
	}	
	public void onResume()
	{
		super.onResume();
		this.update(index);
	}
	
//	private class PhoneCallListener extends PhoneStateListener {
//
//		private boolean isPhoneCalling = false;
//
//		@Override
//		public void onCallStateChanged(int state, String incomingNumber) {
//
//
//			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
//				isPhoneCalling = true;
//			}
//
//			if (TelephonyManager.CALL_STATE_IDLE == state) {
//				// run when class initial and phone call ended, 
//				// need detect flag from CALL_STATE_OFFHOOK
//
//				if (isPhoneCalling) {
//					// restart app
////					Intent i = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
////					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////					startActivity(i);
//
//					isPhoneCalling = false;
//				}
//			}
//		}
//	}

}
