package com.insta.livraison_app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProduitAppDataSource {
	
	private SQLiteDatabase bdd;
	private LivraisonAppBaseOpenHelper baseSQLite;

	private static final String PRODUIT_TABLE_NAME = "produit";
	private static final String PRODUIT_COLUMN_ID = "id";
	private static final String PRODUIT_COLUMN_ID_WEBSERVICE = "id_webservice";
	private static final String PRODUIT_COLUMN_REFERENCE = "reference";
	private static final String PRODUIT_COLUMN_QUANTITE = "quantite";
	private static final String PRODUIT_COLUMN_STATUT = "statut";
	private static final String PRODUIT_COLUMN_COMMENTAIRE = "commentaire";
	private static final String PRODUIT_COLUMN_LIVRAISON_ID = "livraison_id";
	
	public ProduitAppDataSource(Context context){
		baseSQLite = new LivraisonAppBaseOpenHelper(context);
	}
	
	
	public int getAll() {
		List<String> users = new ArrayList<String>(); 
		String selectQuery = "SELECT * FROM " + ProduitAppDataSource.PRODUIT_TABLE_NAME;
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) { 
			users.add(cursor.getString(0)); 
			cursor.moveToNext();
		}
		cursor.close();
		return cursor.getCount();
	}
	
	public List<Produit> getAllProduitByLivraison(int id) {
		List<Produit> produits = new ArrayList<Produit>(); 
		String selectQuery = "SELECT *"
							+ " FROM " + ProduitAppDataSource.PRODUIT_TABLE_NAME
							+ " WHERE livraison_id = " + id;
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) { 
			Produit produit = new Produit();
			produit.setIdWebService(Integer.parseInt(cursor.getString(0)));
			produit.setReference(cursor.getString(1));
			produit.setQuantite(cursor.getString(2));
			produit.setStatut(Integer.parseInt(cursor.getString(3)));
			produit.setCommentaire(cursor.getString(4));
			produit.setLivraisonId(Integer.parseInt(cursor.getString(5)));
			
			produits.add(produit); 
			cursor.moveToNext();
		}
		cursor.close();
		return produits;
	}
	
	public ArrayList<Produit> getAllProduitToUpdateWebservice() {
		ArrayList<Produit> produits = new ArrayList<Produit>(); 
		String selectQuery = "SELECT p.id_webservice, p.reference, p.quantite, p.statut, p.commentaire, p.livraison_id"
							+ " FROM " + ProduitAppDataSource.PRODUIT_TABLE_NAME + " AS p"
							+ " JOIN livraison AS l"
							+ "	ON p.livraison_id = l.id"
							+ " WHERE l.statut = 2";
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) { 
			Produit produit = new Produit();
			produit.setIdWebService(Integer.parseInt(cursor.getString(0)));
			produit.setReference(cursor.getString(1));
			produit.setQuantite(cursor.getString(2));
			produit.setStatut(Integer.parseInt(cursor.getString(3)));
			produit.setCommentaire(cursor.getString(4));
			produit.setLivraisonId(Integer.parseInt(cursor.getString(5)));
			
			produits.add(produit); 
			cursor.moveToNext();
		}
		cursor.close();
		return produits;
	}
	
	public long insertProduit(int id_webservice, String reference, String quantite, int statut, 
			String commentaire, int livraison_id) {
		
		ContentValues values = new ContentValues(); 
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_ID_WEBSERVICE, id_webservice);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_REFERENCE, reference);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_QUANTITE, quantite);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_STATUT, statut);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_COMMENTAIRE, commentaire);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_LIVRAISON_ID, livraison_id);
		
		return bdd.insert(ProduitAppDataSource.PRODUIT_TABLE_NAME, null, values);
	}
	
	public long update(int id, String commentaire, int statut){
		ContentValues values = new ContentValues(); 
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_STATUT, commentaire);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_COMMENTAIRE, statut);		
		return bdd.update(ProduitAppDataSource.PRODUIT_TABLE_NAME, values, 
				ProduitAppDataSource.PRODUIT_COLUMN_ID + " = " + id, null);
	}
	
	public void open () throws SQLException
	{
		bdd = baseSQLite.getWritableDatabase();
	}
	
	public void close()
	{
		baseSQLite.close();
	}
}
