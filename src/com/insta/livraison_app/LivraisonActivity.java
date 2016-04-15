package com.insta.livraison_app;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.CheckBox;
import android.widget.Toast;

public class LivraisonActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.livraison);
		
		JsonLoader getJsonConnection = new JsonLoader("livraison", findViewById(android.R.id.content), (CheckBox)findViewById(R.id.SaveData));
		try {
			getJsonConnection.execute("http://livraison-app.esy.es/?json=livraison").get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void jsonReturn() {
	}
}
