package fr.eni.tp.encheres.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import fr.eni.tp.encheres.bll.CategorieService;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.exception.BusinessException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategorieController {

	private CategorieService categorieService;
	private MessageSource messageSource;

	public CategorieController(CategorieService categorieService, MessageSource messageSource) {
		this.categorieService = categorieService;
		this.messageSource = messageSource;
	}

	// ********** CREATE **********

	/**
	 * Méthode renvoyant la vue de création d'une nouvelle catégorie
	 * 
	 * @param model avec un objet de catégorie vide
	 * @return la vue de création de catégorie
	 */
	@GetMapping("/categories/ajouter")
	public String getAjouter(Model model) {
		model.addAttribute("categorie", new Categorie());

		return "view-nouvelle-categorie";
	}

	/**
	 * Méthode permettant de créer une catégorie en BDD et de renvoyer la vue des
	 * catégories après
	 * 
	 * @param categorie     les données de la nouvelle catégorie
	 * @param bindingResult les erreurs
	 * @return la vue des catégories
	 */
	@PostMapping("/categories/ajouter")
	public String postAjouter(@Valid @ModelAttribute("categorie") Categorie categorie,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			return "view-nouvelle-categorie";
		} else {
			categorieService.createCategorie(categorie);

			return "redirect:/categories";
		}
	}

	// ********** READ **********

	/**
	 * Méthode renvoyant la vue des catégories
	 * 
	 * @param model avec les catégories
	 * @return la vue des catégories
	 */
	@GetMapping("/categories")
	public String getCategories(Model model) {
		List<Categorie> categories = categorieService.getCategories();
		model.addAttribute("categories", categories);
		return "view-categories";
	}

	// ********** UPDATE **********

	/**
	 * Méthode renvoyant la vue de modification de la catégorie
	 * 
	 * @param idCategorie l'id de la catégorie à modifier
	 * @param model       avec les données de la catégorie
	 * @return la vue de modification de la catégorie
	 */
	@GetMapping("/categories/modifier")
	public String getModifier(@RequestParam("idCategorie") int idCategorie, Model model) {
		model.addAttribute("categorie", categorieService.getCategorie(idCategorie));

		return "view-modifier-categorie";
	}

	/**
	 * Méthode permettant de modifier les données de la catégorie en BDD et
	 * renvoyant la vue des catégories
	 * 
	 * @param categorie     la données de la catégorie modifiée
	 * @param bindingResult les erreurs
	 * @return la vue des catégories
	 */
	@PostMapping("/categories/modifier")
	public String postModifier(@Valid @ModelAttribute("categorie") Categorie categorie,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			return "view-modifier-categorie";
		} else {
			categorieService.updateCategorie(categorie);

			return "redirect:/categories";
		}
	}

	// ********** DELETE **********

	/**
	 * Méthode permettant de supprimer une catégorie si celle-ci n'est pas utilisée
	 * par un article
	 * 
	 * @param idCategorie id de la catégorie à supprimer
	 * @param model       avec les catégories
	 * @return la vue des catégories
	 */
	@PostMapping("/categories/supprimer")
	public String postSupprimer(@RequestParam("idCategorie") int idCategorie, Model model) {

		try {
			categorieService.deleteCategorie(idCategorie);

			return "redirect:/categories";
		} catch (BusinessException e) {
			e.printStackTrace();
			List<String> errorMessages = new ArrayList<String>();
			e.getClesErreurs().forEach(cle -> {
				String errorMessage = messageSource.getMessage(cle, null, LocaleContextHolder.getLocale());
				errorMessages.add(errorMessage);
			});
			model.addAttribute("errorMessages", errorMessages);
		}

		List<Categorie> categories = categorieService.getCategories();
		model.addAttribute("categories", categories);
		return "view-categories";
	}

}