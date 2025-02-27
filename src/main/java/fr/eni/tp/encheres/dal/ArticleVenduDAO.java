package fr.eni.tp.encheres.dal;

import java.util.List;

import fr.eni.tp.encheres.bo.ArticleVendu;

public interface ArticleVenduDAO {

	// ********** CREATE **********

	void createArticle(ArticleVendu article);

	// ********** READ **********

	ArticleVendu getArticleById(int id);

	List<ArticleVendu> getArticles();

	List<ArticleVendu> getArticlesFiltresByString(String filtreSql);

	List<ArticleVendu> getArticlesFiltresById(int idCategorie);

	List<ArticleVendu> getArticlesFiltresByStringAndId(String filtreSql, int idCategorie);

	List<ArticleVendu> getArticlesByIdUser(int idUser);

	// ********** UPDATE **********

	void updateArticle(ArticleVendu article);

	// ********** DELETE **********

	void deleteArticle(int idArticle);

	// ********** VALIDATION **********

	int getCountArticleByIdCategorie(int idCategorie);

}
