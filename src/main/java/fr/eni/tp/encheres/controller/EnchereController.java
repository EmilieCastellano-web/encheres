package fr.eni.tp.encheres.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.multipart.MultipartFile;

import fr.eni.tp.encheres.bll.ArticleVenduService;
import fr.eni.tp.encheres.bll.CategorieService;
import fr.eni.tp.encheres.bll.EnchereService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Enchère;
import fr.eni.tp.encheres.bo.Retrait;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.exception.BusinessException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("userSession")
public class EnchereController {

	private ArticleVenduService articleVenduService;
	private CategorieService categorieService;
	private EnchereService enchereService;
	private MessageSource messageSource;

	public EnchereController(ArticleVenduService articleVenduService, CategorieService categorieService,
			EnchereService enchereService, MessageSource messageSource) {
		this.articleVenduService = articleVenduService;
		this.categorieService = categorieService;
		this.enchereService = enchereService;
		this.messageSource = messageSource;
	}

	/**
	 * Méthode retournant la vue d'accueil des enchères lorsque l'utilisateur ne
	 * spécifiie pas de ressource dans l'URL
	 * 
	 * @return la vue des enchères
	 */
	@GetMapping("/")
	public String redirectToEncheres() {
		return "redirect:/encheres";
	}

	// ********** CREATE **********

	/**
	 * Méthode retournant la vue de création d'un nouvel article
	 * 
	 * @param model       avec un article vide et la liste des catégories
	 * @param userSession avec les données de l'utilisateur en session
	 * @return la vue de création d'un article
	 */
	@GetMapping("/encheres/nouvelleVente")
	public String getNouvelleVenteArticle(Model model, @ModelAttribute("userSession") Utilisateur userSession) {
		ArticleVendu article = new ArticleVendu();
		Retrait retrait = new Retrait();

		article.setLieuRetrait(retrait);
		article.getLieuRetrait().setRue(userSession.getRue());
		article.getLieuRetrait().setCode_postal(userSession.getCodePostal());
		article.getLieuRetrait().setVille(userSession.getVille());
		model.addAttribute("article", article);

		List<Categorie> categories = categorieService.getCategories();
		model.addAttribute("categories", categories);

		return "view-nouvelle-vente";
	}

	/**
	 * Méthode permettant d'insérer en BDD un nouvel article
	 * 
	 * @param article       comprenant les données du formulaire
	 * @param bindingResult comprenant les erreurs du formulaire
	 * @param model         avec la liste des catégories
	 * @param userSession   avec les données de l'utilisateur en session
	 * @return la vue de création d'un article si erreur, la vue des enchères sinon
	 */
	@PostMapping("/encheres/nouvelleVente")
	public String postNouvelleVenteArticle(@Valid @ModelAttribute("article") ArticleVendu article,
			BindingResult bindingResult, Model model, @ModelAttribute("userSession") Utilisateur userSession,
			@RequestParam("image") MultipartFile file) {

		if (bindingResult.hasErrors()) {
			List<Categorie> categories = categorieService.getCategories();
			model.addAttribute("categories", categories);
			return "view-nouvelle-vente";
		} else {
			try {
				article.setVendeur(userSession);
				articleVenduService.createArticle(article);

				Path uploadDir = Paths.get("uploads/");
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}

				Path imagePath = Paths.get("uploads/" + article.getNoArticle() + ".png");

				if (file.isEmpty()) {
					Path defaultImagePath = Paths.get("src/main/resources/static/images/photo.png");
					Files.copy(defaultImagePath, imagePath, StandardCopyOption.REPLACE_EXISTING);
				} else {
					byte[] bytes = file.getBytes();
					Files.write(imagePath, bytes);
				}

				return "redirect:/encheres";
			} catch (BusinessException e) {
				e.printStackTrace();
				e.getClesErreurs().forEach(cle -> {
					ObjectError error = new ObjectError("globalError", cle);
					bindingResult.addError(error);
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<Categorie> categories = categorieService.getCategories();
		model.addAttribute("categories", categories);
		return "view-nouvelle-vente";
	}

	/**
	 * 
	 * Méthode permettant de faire une nouvelle enchère
	 * 
	 * @param montant
	 * @param idArticle
	 * @param userSession
	 * @param redirectAttributes
	 * @param locale
	 * @return
	 * @throws BusinessException
	 */
	@PostMapping("/encheres/creer")
	public String postCreerEnchere(@RequestParam(name = "proposition") int montant, @RequestParam("id") int idArticle,
			@ModelAttribute("userSession") Utilisateur userSession, RedirectAttributes redirectAttributes,
			Locale locale) {
		try {

			this.enchereService.creerEnchere(userSession, montant, idArticle);
			return "redirect:/encheres/detail?id=" + idArticle;

		} catch (BusinessException e) {
			e.printStackTrace();
			e.getClesErreurs().forEach(cle -> {
				String message = messageSource.getMessage(cle, null, locale);
				redirectAttributes.addFlashAttribute("erreurs", message);
			});

			return "redirect:/encheres/detail?id=" + idArticle;

		}

	}

	// ********** READ **********

	/**
	 * Méthode retournant la vue des enchères
	 * 
	 * @param model avec la liste des articles et la liste des catégories
	 * @return la vue des enchères
	 */
	@GetMapping("/encheres")
	public String getArticles(Model model, HttpSession session) {
		List<ArticleVendu> articles = articleVenduService.getArticles();
		model.addAttribute("articles", articles);

		List<Categorie> categories = categorieService.getCategories();
		model.addAttribute("categories", categories);

		Utilisateur userSession = (Utilisateur) session.getAttribute("userSession");
		model.addAttribute("userSession", userSession);

		return "view-encheres";
	}

	/**
	 * Méthode permettant de filtrer les articles en fonction d'un mot-clé et d'une
	 * catégorie si déconnecté, si connecté, des options supplémentaires en achats
	 * et ventes sont disponibles
	 * 
	 * @param filtre      comprenant le mot-clé
	 * @param idCategorie comprenant l'id de la catégorie
	 * @param options     comprenant la liste des options choisies
	 * @param model       avec la liste des articles filtrée et la liste des
	 *                    catégories
	 * @param session     avec les données de l'utilisateur en session
	 * @return la vue des enchères
	 */
	@GetMapping("/encheres/search")
	public String getSearchArticles(@RequestParam(name = "filtre") String filtre,
			@RequestParam(name = "categorie") int idCategorie,
			@RequestParam(name = "option", required = false) List<String> options, Model model, HttpSession session) {
		List<ArticleVendu> articles = new ArrayList<ArticleVendu>();

		if (options == null) {
			articles = articleVenduService.getArticlesFiltres(filtre, idCategorie);
		}

		if (options != null) {
			articles = articleVenduService.getArticlesFiltresOptions(filtre, idCategorie, options,
					(Utilisateur) session.getAttribute("userSession"));
		}

		model.addAttribute("articles", articles);

		List<Categorie> categories = categorieService.getCategories();
		model.addAttribute("categories", categories);

		Utilisateur userSession = (Utilisateur) session.getAttribute("userSession");

		model.addAttribute("userSession", userSession);

		return "view-encheres";
	}

	/**
	 * Méthode permettant d'envoyer la vue détail des ventes Avec vérification
	 * auprès de enchèreService pour : Savoir si l'enchère est en cours ; si
	 * l'userSession est l'enchère la plus haute Si oui affichage du pseudo de la
	 * personne qui a remporté l'enchère Si non affichage d'aucune enchère
	 * 
	 * @param id
	 * @param model
	 * @param userSession
	 * @return view detail-vente
	 */
	@GetMapping("/encheres/detail")
	public String detailArticle(@RequestParam("id") int id, Model model,
			@ModelAttribute("userSession") Utilisateur userSession) {
		
		
		ArticleVendu article = articleVenduService.getArticleCompletById(id);
		model.addAttribute("article", article);
		model.addAttribute("userSession", userSession);

		return "detail_vente";
	}

	@GetMapping("/encheres/voirEncheres")
	public String getVoirEncheresArticle(@ModelAttribute("userSession") Utilisateur userSession,
			@RequestParam("idArticle") int idArticle, Model model) {
		try {
			List<Enchère> encheres = enchereService.getEncheresByIdArticle(idArticle, userSession);
			model.addAttribute("encheres", encheres);
			return "view-encheres-article";
		} catch (BusinessException e) {
			e.printStackTrace();
			List<String> errorMessages = new ArrayList<String>();
			e.getClesErreurs().forEach(cle -> {
				String errorMessage = messageSource.getMessage(cle, null, LocaleContextHolder.getLocale());
				errorMessages.add(errorMessage);
			});
			model.addAttribute("errorMessages", errorMessages);
		}
		return "view-encheres";
	}

	// ********** UPDATE **********

	@GetMapping("/encheres/modifier")
	public String getModifierArticle(@ModelAttribute("userSession") Utilisateur userSession,
			@RequestParam("idArticle") int idArticle, Model model) {
		try {
			ArticleVendu article = articleVenduService.getArticleByIdAndUser(idArticle, userSession);
			model.addAttribute("article", article);

			List<Categorie> categories = categorieService.getCategories();
			model.addAttribute("categories", categories);

			return "view-modifier-article";
		} catch (BusinessException e) {
			e.printStackTrace();
			List<String> errorMessages = new ArrayList<String>();
			e.getClesErreurs().forEach(cle -> {
				String errorMessage = messageSource.getMessage(cle, null, LocaleContextHolder.getLocale());
				errorMessages.add(errorMessage);
			});
			model.addAttribute("errorMessages", errorMessages);
		}
		return "view-encheres";
	}

	@PostMapping("/encheres/modifier")
	public String postModifierArticle(@Valid @ModelAttribute("article") ArticleVendu article,
			BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile file) {
		if (bindingResult.hasErrors()) {
			List<Categorie> categories = categorieService.getCategories();
			model.addAttribute("categories", categories);
			return "view-modifier-article";
		} else {
			try {
				articleVenduService.updateArticle(article);

				Path uploadDir = Paths.get("uploads/");
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}

				Path imagePath = Paths.get("uploads/" + article.getNoArticle() + ".png");

				if (!file.isEmpty()) {
					byte[] bytes = file.getBytes();
					Files.write(imagePath, bytes);
				}

				return "redirect:/encheres";
			} catch (BusinessException e) {
				e.printStackTrace();
				e.getClesErreurs().forEach(cle -> {
					ObjectError error = new ObjectError("globalError", cle);
					bindingResult.addError(error);
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<Categorie> categories = categorieService.getCategories();
		model.addAttribute("categories", categories);
		return "view-modifier-article";
	}

	// ********** DELETE **********

	@PostMapping("/encheres/supprimer")
	public String postSupprimerArticle(@ModelAttribute("userSession") Utilisateur userSession,
			@RequestParam("idArticle") int idArticle, Model model) {
		try {
			articleVenduService.deleteArticle(userSession.getNoUtilisateur(), idArticle);

			return "redirect:/encheres";
		} catch (BusinessException e) {
			e.printStackTrace();
			List<String> errorMessages = new ArrayList<String>();
			e.getClesErreurs().forEach(cle -> {
				String errorMessage = messageSource.getMessage(cle, null, LocaleContextHolder.getLocale());
				errorMessages.add(errorMessage);
			});
			model.addAttribute("errorMessages", errorMessages);
		}
		return "view-encheres";
	}

}
