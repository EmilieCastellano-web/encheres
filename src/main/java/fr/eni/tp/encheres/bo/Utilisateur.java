package fr.eni.tp.encheres.bo;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Utilisateur {

	// ****************** ATTRIBUTS D'INSTANCE ***********************************
	
	@NotBlank
	@Size(max = 30)
	private String pseudo;

	@NotBlank
	@Size(max = 30)
	private String nom;

	@NotBlank
	@Size(max = 30)
	private String prenom;

	@NotBlank
	@Size(max = 40)
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "L'adresse email est invalide.")
	private String email;

	@Size(max = 15)
	@Pattern(regexp = "^(\\d{2}([ .-]?)?){4}\\d{2}$", message = "Le numéro de telephone est invalide.")
	private String telephone;

	@NotBlank
	@Size(max = 30)
	private String rue;

	@NotBlank
	@Size(max = 5)
	private String codePostal;

	@NotBlank
	@Size(max = 30)
	private String ville;

	@NotBlank
	private String motDePasse;

	@NotNull
	@Min(value = 0)
	private int credit = 0;
	private boolean administrateur = false;

	@NotNull
	private int noUtilisateur;

	List<ArticleVendu> achats = new ArrayList<ArticleVendu>();
	List<ArticleVendu> vendus = new ArrayList<ArticleVendu>();
	List<Enchère> encheres = new ArrayList<Enchère>();

	// /****************** CONSTRUCTEURS ***********************************
	
	public Utilisateur() {

	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, String motDePasse, int credit, boolean administrateur, int noUtilisateur,
			List<ArticleVendu> achats, List<ArticleVendu> vendus, List<Enchère> encheres) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
		this.noUtilisateur = noUtilisateur;
		this.achats = achats;
		this.vendus = vendus;
		this.encheres = encheres;
	}
	
	///****************** METHODES ***********************************

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Utilisateur [pseudo=").append(pseudo).append(", nom=").append(nom).append(", prenom=")
				.append(prenom).append(", email=").append(email).append(", telephone=").append(telephone)
				.append(", rue=").append(rue).append(", codePostal=").append(codePostal).append(", ville=")
				.append(ville).append(", motDePasse=").append(motDePasse).append(", credit=").append(credit)
				.append(", administrateur=").append(administrateur).append(", noUtilisateur=").append(noUtilisateur)
				.append("]");
		return builder.toString();
	}

	// ****************** GETTERS & SETTERS ***********************************
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
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

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {

		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public List<ArticleVendu> getAchats() {
		return achats;
	}

	public void setAchats(List<ArticleVendu> achats) {
		this.achats = achats;
	}

	public List<ArticleVendu> getVendus() {
		return vendus;
	}

	public void setVendus(List<ArticleVendu> vendus) {
		this.vendus = vendus;
	}

	public List<Enchère> getEncheres() {
		return encheres;
	}

	public void setEncheres(List<Enchère> encheres) {
		this.encheres = encheres;
	}

}
