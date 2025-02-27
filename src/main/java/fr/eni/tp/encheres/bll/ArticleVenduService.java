package fr.eni.tp.encheres.bll;

import java.util.List;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.exception.BusinessException;

public interface ArticleVenduService {

	// ********** CREATE **********

	void createArticle(ArticleVendu article) throws BusinessException;

	// ********** READ **********
	ArticleVendu getArticleById(int id);

	ArticleVendu getArticleCompletById(int id);

	ArticleVendu getArticleByIdAndUser(int idArticle, Utilisateur userSession) throws BusinessException;

	List<ArticleVendu> getArticles();

	List<ArticleVendu> getArticlesFiltres(String filtre, int idCategorie);

	List<ArticleVendu> getArticlesFiltresOptions(String filtre, int idCategorie, List<String> options,
			Utilisateur userSession);

	// ********** UPDATE **********

	void updateArticle(ArticleVendu article) throws BusinessException;

	// ********** DELETE **********

	void deleteArticle(int noUtilisateur, int idArticle) throws BusinessException;

}
// ********** CREATE **********

// ********** READ **********

// ********** UPDATE **********

// ********** DELETE **********

// ********** VALIDATION **********

// Méthodes dans enchère service à enlever

// Login service impl reset password