package fr.eni.tp.encheres.bll;

import java.util.List;

import fr.eni.tp.encheres.bo.Enchère;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.exception.BusinessException;

public interface EnchereService {

	// ********** CREATE **********

	void creerEnchere(Utilisateur userSession, int montant, int idArticle) throws BusinessException;

	// ********** READ **********

	List<Enchère> getEncheresByIdArticle(int idArticle, Utilisateur userSession) throws BusinessException;

	// ********** UPDATE **********

	// ********** DELETE **********

}
