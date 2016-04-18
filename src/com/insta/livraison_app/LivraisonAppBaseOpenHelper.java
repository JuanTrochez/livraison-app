package com.insta.livraison_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LivraisonAppBaseOpenHelper extends SQLiteOpenHelper {

	private static final String DATABSE_NAME = "livraison_app.db";
	private static final int DATABASE_VERSION = 1;	
	
	
	public LivraisonAppBaseOpenHelper(Context context) {
		super(context, DATABSE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE IF NOT EXISTS client ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
														+ " nom TEXT NOT NULL,"
														+ " prenom TEXT NOT NULL,"
														+ " telephone TEXT NOT NULL,"
														+ " email TEXT NOT NULL);");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS livreur ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
														+ " nom TEXT NOT NULL,"
														+ " prenom TEXT NOT NULL,"
														+ " login TEXT NOT NULL,"
														+ " password TEXT NOT NULL);");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS livraison ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
														+ " id_webservice integer NOT NULL,"
														+ " adresse TEXT NOT NULL,"
														+ " numero TEXT NOT NULL,"
														+ " code_postal TEXT NOT NULL,"
														+ " ville TEXT NOT NULL,"
														+ " latitude TEXT NOT NULL,"
														+ " longitude TEXT NOT NULL,"
														+ " date TEXT NOT NULL,"
														+ " client_id INTEGER NOT NULL,"
														+ " statut INTEGER NOT NULL,"
														+ " duration TEXT NOT NULL,"
														+ " distance TEXT NOT NULL,"
														+ " livreur_id INTEGER NOT NULL,"
														+ " FOREIGN KEY (client_id) REFERENCES  client(id),"
														+ " FOREIGN KEY (livreur_id) REFERENCES livreur(id));");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS produit ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
														+ " reference TEXT NOT NULL,"
														+ " quantite TEXT NOT NULL,"
														+ " statut INTEGER NOT NULL,"
														+ " commentaire TEXT NOT NULL,"
														+ " livraison_id INTEGER NOT NULL,"
														+ " FOREIGN KEY (livraison_id) REFERENCES livraison(id));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS client ");
		db.execSQL("DROP TABLE IF EXISTS livreur ");
		db.execSQL("DROP TABLE IF EXISTS produit ");
		db.execSQL("DROP TABLE IF EXISTS livraison ");
		onCreate(db);
		
	}

}
