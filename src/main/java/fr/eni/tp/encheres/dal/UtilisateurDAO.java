package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.PasswordToken;
import fr.eni.tp.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	// ********** CREATE **********

	void createUtilisateur(Utilisateur user);

	void sauvegarderPasswordResetToken(PasswordToken passwordToken);

	// ********** READ **********

	Utilisateur getUtilisateurById(int noUtilisateur);

	Utilisateur getUtilisateurByPseudo(String pseudo);

	Utilisateur getUtilisateurByEmail(String email);

	PasswordToken findToken(String token);

	// ********** UPDATE **********

	void updateUtilisateur(Utilisateur user);

	void updateCredit(int noUtilisateur, int nouveauSoldeCredit);

	void updateCredit(Utilisateur user);

	void resetPassword(PasswordToken passwordToken, String password);

	// ********** DELETE **********

	// ********** VALIDATION **********

	int getCountUtilisateurByPseudo(String pseudo);

	int getCountUtilisateurByEmail(String email);

	int getCountUtilisateurByMdp(String mdp, int idUser);

}
