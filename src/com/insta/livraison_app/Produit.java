package com.insta.livraison_app;

import java.util.ArrayList;

public class Produit {

	private int id_webService;
	private String reference;
	private String quantite;
	private int statut;
	private String commentaire;
	private int livraison_id;	
	public static ArrayList<Produit> getProduitByLivraisonID;
	
	
	public int getId_webService() {
		return id_webService;
	}
	public void setId_webService(int id_webService) {
		this.id_webService = id_webService;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getQuantite() {
		return quantite;
	}
	public void setQuantite(String quantite) {
		this.quantite = quantite;
	}
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public int getLivraison_id() {
		return livraison_id;
	}
	public void setLivraison_id(int livraison_id) {
		this.livraison_id = livraison_id;
	}
		
	
}