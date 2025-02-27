package fr.eni.tp.encheres.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.tp.encheres.bll.CategorieService;

import fr.eni.tp.encheres.bo.Categorie;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {

	private CategorieService categorieService;

	public StringToCategorieConverter(CategorieService categorieService) {
		this.categorieService = categorieService;
	}

	/**
	 * Méthode permettant de convertir l'id d'une catégorie en formant String en un
	 * objet de type Catégorie correspondant
	 */
	@Override
	public Categorie convert(String idCategorie) {
		return categorieService.getCategorie(Integer.parseInt(idCategorie));
	}
}
