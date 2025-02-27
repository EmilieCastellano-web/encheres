package fr.eni.tp.encheres.bo;

import java.time.LocalDateTime;

public class PasswordToken {

	private String token;
	private LocalDateTime dateExpiration;
	private int noUtilisateur;

	public PasswordToken() {

	}

	public PasswordToken(String token, LocalDateTime dateExpiration, int noUtilisateur) {
		super();
		this.token = token;
		this.dateExpiration = dateExpiration;
		this.noUtilisateur = noUtilisateur;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PasswordToken token : ").append(token).append(", dateExpiration : ").append(dateExpiration)
				.append(", idUtilisateur : ").append(noUtilisateur);
		return builder.toString();
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(LocalDateTime dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

}
