package com.zak.pro;

import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.zak.pro.entity.CGU;
import com.zak.pro.repository.CGURepository;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@SpringBootApplication
@EnableWebSecurity
@EnableAsync
public class InvestMEProjectApplication implements CommandLineRunner {

	@Autowired
	private CGURepository cguRepository;

	public static void main(String[] args) {
		SpringApplication.run(InvestMEProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CGU cgu = new CGU();
		cgu.setContent(this.content);
		this.cguRepository.save(cgu);
	}

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("Bearer Token",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.info(new Info().title("InvestMe API").description(
						"InvestMe is a mobile application that helps you as a student to find an investor for your brilliant ideas, and to make you dreams came true")
						.version("1.0.0"));
	}

	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

	@Bean(name = "messageSource")
	public ResourceBundleMessageSource bundleMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	String content = "Comment rédiger ses CGU ?\n"
			+ "Les conditions générales d’utilisation doivent être rédigées dans le respect des dispositions légales applicables : en aucun cas une clause ne peut déroger aux dispositions d’ordre public. Au-delà de cet impératif, l’éditeur du site peut prévoir toute mention utile pour informer l’internaute sur le contenu du site d’une part, se protéger contre d’éventuels litiges avec l’utilisateur d’autre part.\n"
			+ "\n" + "Le contenu des CGU inclut notamment :\n" + "\n"
			+ "La description du site : les conditions générales d’utilisation peuvent préciser l’objectif du site et décrire les services proposés. Attention à prévoir une description large, pour s’éviter d’avoir à modifier les CGU à chaque évolution du site.\n"
			+ "Les droits et les obligations de l’utilisateur : les droits de l’utilisateur peuvent concerner notamment sa marge de manœuvre au moment de la création d’un espace personnel. Ses obligations peuvent inclure l’obligation de maintenir le caractère confidentiel de ses identifiants de connexion, l’obligation d’utiliser le site conformément à sa destination, l’obligation de ne pas tenter de nuire au bon fonctionnement du site… Ces dispositions des conditions générales d’utilisation permettent d’engager sa responsabilité en cas de dommage résultant du non-respect desdites obligations.\n"
			+ "Les droits et les obligations de l’éditeur : les conditions générales d’utilisation mettent en général des obligations de moyens à la charge de l’éditeur. Maintenir l’accès au site, assurer le bon fonctionnement du site… Les CGU sont aussi l’occasion pour l’éditeur de se décharger de sa responsabilité en cas d’utilisation frauduleuse du site par un tiers, par exemple.\n"
			+ "Les conditions d’utilisation du forum ou d’un espace de libre échange : lorsque l’utilisateur est autorisé à publier lui-même du contenu sur le site, les conditions générales d’utilisation du site encadrent cet espace de libre échange. L’éditeur, notamment, peut limiter sa responsabilité de manière à ne pas être tenu responsable en cas de propos injurieux ou de publication de contenu contrefaisant les droits de propriété intellectuelle d’un tiers.\n"
			+ "La responsabilité limitée de l’éditeur quant aux liens hypertextes : des liens de redirection du site peuvent pointer vers des sites tiers. Les conditions générales d’utilisation précisent le lien juridique entre le site et le site tiers, et permettent de décharger l’éditeur de sa responsabilité en cas de contenu illicite sur le site tiers vers lequel le lien hypertexte redirige.\n"
			+ "Un rappel du droit de la propriété intellectuelle : les conditions générales d’utilisation rappellent aux internautes que les éléments du site – textes, vidéos, images… – sont protégés par le droit d’auteur et que leur utilisation sans autorisation préalable expresse est interdite.\n"
			+ "Un rappel sur la force majeure : la force majeure, un évènement extérieur imprévisible et insurmontable, exonère les parties de leurs responsabilités respectives. Cette disposition est d’ordre public.\n"
			+ "Les modalités de règlement des litiges : l’éditeur peut mentionner qu’en cas de litige, les parties tenteront de régler le conflit à l’amiable. A défaut de règlement amiable, l’éditeur doit respecter les règles d’attribution de compétence matérielle et territoriale. Par exemple : le consommateur doit avoir le choix entre le tribunal de son domicile ou le tribunal du lieu de la société éditrice du site.\n"
			+ "Faire accepter les conditions générales d’utilisation :\n"
			+ "Les conditions générales d’utilisation n’ont une valeur légale qu’à condition d’être acceptées par l’internaute au moment d’entrer sur le site. L’acceptation des CGU peut être matérialisée par une case à cocher, de type : « En cochant cette case, vous certifiez avoir lu et accepté sans réserve les présentes ».\n"
			+ "\n";

}
