package fr.eni.tp.encheres.bll;

import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.exception.BusinessException;

public interface LoginService {

	// ********** CREATE **********

	String creerResetPasswordLink(String email) throws BusinessException;

	// ********** READ **********

	public Utilisateur charger(String pseudo);

	// ********** UPDATE **********

	void resetPassword(String token, String password, String passwordConfirm) throws BusinessException;

	// ********** DELETE **********

	// ********** VALIDATION **********

	boolean validerResetToken(String token) throws BusinessException;

}
