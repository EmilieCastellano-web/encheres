package fr.eni.tp.encheres.controller;

import java.security.Principal;

import java.util.Locale;

import org.springframework.context.MessageSource;
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
import fr.eni.tp.encheres.bll.LoginService;
import fr.eni.tp.encheres.bll.UtilisateurService;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@SessionAttributes({ "userSession" })
public class LoginController {

	private LoginService loginService;
	private UtilisateurService utilisateurService;
	private MessageSource messageSource;

	public LoginController(LoginService loginService, UtilisateurService utilisateurService,
			MessageSource messageSource) {
		this.loginService = loginService;
		this.utilisateurService = utilisateurService;
		this.messageSource = messageSource;

	}

	// ********** CREATE **********

	/**
	 * Méthode permettant d'afficher la vue d'inscription
	 * 
	 * @param model
	 * @return le formulaire d'inscription
	 */
	@GetMapping("/inscription")
	public String getInscription(Model model) {

		model.addAttribute("utilisateur", new Utilisateur());

		return "inscription";
	}

	/**
	 * Méthode permettant de soumettre le formulaire d'inscription
	 * 
	 * @param mdpConfirm
	 * @param user
	 * @param bindingResult
	 * @return la page listes des enchères; Si erreur @Return le formulaire
	 *         d'inscription
	 * 
	 */
	@PostMapping("/inscription")
	public String postInscription(@RequestParam("PasswordConfirm") String mdpConfirm,
			@Valid @ModelAttribute("utilisateur") Utilisateur user, BindingResult bindingResult) {

		if (!bindingResult.hasErrors()) {
			try {
				this.utilisateurService.creerUtilisateur(user, mdpConfirm);
				return "redirect:/encheres";
			} catch (BusinessException e) {
				e.printStackTrace();
				e.getClesErreurs().forEach(cle -> {
					ObjectError error = new ObjectError("globalError", cle);
					bindingResult.addError(error);
				});
			}
			return "inscription";

		} else {
			return "inscription";
		}
	}

	/*
	 * Permet d'afficher la view pour entrer son adresse mail
	 * 
	 * @param model
	 * 
	 * @return reset-password
	 */
	@GetMapping("/forgotPassword")
	public String afficherMdpOublie() {
		return "forgot-password";

	}

	/**
	 * Permet d'afficher la view avec le lien du token
	 * 
	 * @param model
	 * @return
	 */
	@PostMapping("/forgotPassword")
	public String resetMdp(@RequestParam("email") String email, Locale locale, RedirectAttributes redirectAttributes,
			Model model) {

		try {
			String token = loginService.creerResetPasswordLink(email);
			redirectAttributes.addFlashAttribute("token", token);
			return "redirect:/forgotPassword?token=" + token;

		} catch (BusinessException e) {
			e.printStackTrace();
			/**
			 * List<String> erreurs = e.getClesErreurs().stream() .map(cle ->
			 * messageSource.getMessage(cle, null, locale)) .collect(Collectors.toList());
			 * System.out.println("Erreurs: " + erreurs);
			 * redirectAttributes.addFlashAttribute("erreurs", erreurs);
			 * 
			 * ;
			 **/
			return "forgot-password";
		}
	}

	// ********** READ **********

	// ********** UPDATE **********

	/**
	 * Permet d'afficher la view pour réinitialiser son mot de passe
	 * 
	 * @param token
	 * @param model
	 * @return
	 */
	@GetMapping("/resetPassword")
	public String verifTokenUser(@RequestParam("token") String token, Model model) {

		try {
			boolean valid = loginService.validerResetToken(token);
			if (valid) {
				// model.addAttribute("token", token);
				return "reset-password-form";
			} else {
				// model.addAttribute("erreur", "Token invalide ou expiré");
				return "forgot-password";
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			// model.addAttribute("erreur", "Une erreur s'est produite");
			return "forgot-password";
		}
	}

	@PostMapping("/resetPassword/formulaire")
	public String resetPassword(@RequestParam("token") String token, @RequestParam("NewMotDePasse") String password,
			@RequestParam("PasswordConfirm") String passwordConfirm, RedirectAttributes redirectAttributes) {
		try {
			loginService.resetPassword(token, password, passwordConfirm);
			// redirectAttributes.addFlashAttribute("success", "Mot de passe réinitialisé
			// avec succès");
			return "login";
		} catch (BusinessException e) {
			e.printStackTrace();
			// redirectAttributes.addFlashAttribute("erreur", "Une erreur s'est produite");
			return "redirect:/resetPassword?token=" + token;
		}

	}

	// ********** DELETE **********

	// ********** SESSION **********

	/**
	 * Méthode permettant d'afficher la page login
	 * 
	 * @return view login
	 */
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	/***
	 * Méthode permettant d'instancier l'Utilisateur userSession En récupérant les
	 * données de l'Utilisateur qui se connecte
	 * 
	 * @param userSession
	 * @param principal
	 * @param request
	 * @return page view encheres
	 */
	@GetMapping("/login/session")
	public String getSession(@ModelAttribute("userSession") Utilisateur userSession, Principal principal,
			HttpServletRequest request) {
		Utilisateur utilisateur = this.loginService.charger(principal.getName());

		if (utilisateur != null) {
			userSession.setNoUtilisateur(utilisateur.getNoUtilisateur());
			userSession.setPseudo(utilisateur.getPseudo());
			userSession.setNom(utilisateur.getNom());
			userSession.setPrenom(utilisateur.getPrenom());
			userSession.setEmail(utilisateur.getEmail());
			userSession.setCredit(utilisateur.getCredit());
			userSession.setTelephone(utilisateur.getTelephone());
			userSession.setRue(utilisateur.getRue());
			userSession.setCodePostal(utilisateur.getCodePostal());
			userSession.setVille(utilisateur.getVille());
			userSession.setAdministrateur(utilisateur.isAdministrateur());

		} else {

			userSession.setNoUtilisateur(0);
			userSession.setPseudo(null);
			userSession.setNom(null);
			userSession.setPrenom(null);
			userSession.setEmail(null);
			userSession.setCredit(0);
			userSession.setTelephone(null);
			userSession.setRue(null);
			userSession.setCodePostal(null);
			userSession.setVille(null);
			userSession.setAdministrateur(false);

		}

		return "redirect:/encheres";

	}

	/**
	 * Méthode permettant d'instancier un Utilisateur pour par la suite créer un
	 * userSession
	 * 
	 * @return new Utilisateur
	 */
	@ModelAttribute("userSession")
	public Utilisateur addUserSession() {
		return new Utilisateur();
	}

}
