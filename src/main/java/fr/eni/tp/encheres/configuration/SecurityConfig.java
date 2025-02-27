package fr.eni.tp.encheres.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final String SELECT_USER = "select pseudo, mot_de_passe, 'true' as enable from UTILISATEURS where pseudo=?";
	private final String SELECT_ROLES = "select u.pseudo, r.role from UTILISATEURS u inner join ROLES r on r.IS_ADMIN = u.administrateur where u.pseudo = ?";
	private Securityhandler successHandler;

	public SecurityConfig(Securityhandler successHandler) {
		this.successHandler = successHandler;
	}

	// filtre d'accée aux pages

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> {

			auth.requestMatchers("/*").permitAll()
			.requestMatchers("/").permitAll()
			.requestMatchers("/css/*").permitAll()
			.requestMatchers("/images/*").permitAll()
			.requestMatchers("/images/uploads/*").permitAll()
			.requestMatchers("/js/*").permitAll()
			.requestMatchers("/login").permitAll()
			.requestMatchers("/login/session").permitAll()
			.requestMatchers("/inscription").permitAll()
			.requestMatchers("/encheres").permitAll()
			.requestMatchers("/encheres/search").permitAll()
			.requestMatchers("/encheres/nouvelleVente").hasRole("USER")
			.requestMatchers("/encheres/creer").hasRole("USER")
			.requestMatchers("/encheres/mesVentes").hasRole("USER")
			.requestMatchers("/encheres/voirEncheres").hasRole("USER")
			.requestMatchers("/encheres/detail").hasRole("USER")
			.requestMatchers("/encheres/modifier").hasRole("USER")
			.requestMatchers("/encheres/supprimer").hasRole("USER")
			.requestMatchers("/profil").hasRole("USER")
			.requestMatchers("/profil/vendeur").hasRole("USER")
			.requestMatchers("/monProfil").hasRole("USER")
			.requestMatchers("/monProfil/modifier").hasRole("USER")
			.requestMatchers("/monProfil/achatCredit").hasRole("USER")
			.requestMatchers("/monProfil/supprimer").hasRole("USER")
			.requestMatchers("/categories").hasRole("ADMIN")
			.requestMatchers("/categories/ajouter").hasRole("ADMIN")
			.requestMatchers("/categories/modifier").hasRole("ADMIN")
			.requestMatchers("/categories/supprimer").hasRole("ADMIN")
			.requestMatchers("/forgotPassword").permitAll()
			.requestMatchers("/resetPassword").permitAll()
			.requestMatchers("/resetPassword/token").permitAll()
			.requestMatchers("/resetPassword/formulaire").permitAll()
			.anyRequest().denyAll();

		});

		// Customiser le formulaire
		// RAJOUT SE SOUVENIR DE MOI ************************************************
		http.formLogin(form -> form.usernameParameter("pseudo").passwordParameter("mot_de_passe").loginPage("/login")
				.defaultSuccessUrl("/login/session").failureUrl("/login?error"))
				.rememberMe(r -> r.authenticationSuccessHandler(successHandler));

		// **************************************************************

		// /logout --> vider la session

		http.logout(logout -> logout.invalidateHttpSession(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl("/"))

		;

		// gestion du timeout au bout de 5 minutes avec redirection vers le /

		http.sessionManagement(session -> session.invalidSessionUrl("/").maximumSessions(1));

		return http.build();

	}

	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery(SELECT_USER);
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(SELECT_ROLES);
		return jdbcUserDetailsManager;
	}

}
