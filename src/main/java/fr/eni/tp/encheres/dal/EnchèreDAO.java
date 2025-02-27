package fr.eni.tp.encheres.dal;

import java.util.List;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Enchère;
import fr.eni.tp.encheres.bo.Utilisateur;

public interface EnchèreDAO {

	// ********** CREATE **********

	void creerEnchere(int montant, ArticleVendu article, Utilisateur userSession);

	// ********** READ **********

	Enchère getMeilleureEnchereByIdArticle(int prixVente, int idArticle);

	List<Enchère> getEncheres(int idArticle);

	List<Enchère> getEncheresByIdArticleOrderDesc(int idArticle);

	List<Enchère> getEncheresByIdUser(int idUser);

	// ********** UPDATE **********

	// ********** DELETE **********

	// ********** VALIDATION **********

	int getCountEnchereByIdArticle(int idArticle);

	int getCountEnchereByIdArticlePrixVente(int prixVente, int idArticle);

}
