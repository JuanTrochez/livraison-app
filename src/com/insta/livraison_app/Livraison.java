package com.insta.livraison_app;

import java.util.ArrayList;

public class Livraison {

	private int id_webService;
	private String adresse;
	private String numero;
	private String codePostal;
	private String ville;
	private String latitude;
	private String longitude;
	private String date;
	private Client client;
	private int statut;
	private String duration;
	private String distance;
	private int livreur_id;
	private int client_id;	
	public static ArrayList<Livraison> dailyLivraisons;
	
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public int getId_webService() {
		return id_webService;
	}
	public void setId_webService(int id_webService) {
		this.id_webService = id_webService;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public int getLivreur_id() {
		return livreur_id;
	}
	public void setLivreur_id(int livreur_id) {
		this.livreur_id = livreur_id;
	}
	
	
}
