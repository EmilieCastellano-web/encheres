package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Retrait;

public interface RetraitDAO {

	// ********** CREATE **********

	void createRetrait(ArticleVendu article);

	// ********** READ **********

	Retrait getRetraitByIdArticle(int noArticle);

	// ********** UPDATE **********

	void updateRetrait(ArticleVendu article);

	// ********** DELETE **********

	void deleteRetrait(int idArticle);

	// ********** VALIDATION **********

}
