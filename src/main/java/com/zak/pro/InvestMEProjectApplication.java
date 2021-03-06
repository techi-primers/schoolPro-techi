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

	String content = "Comment r??diger ses CGU ?\n"
			+ "Les conditions g??n??rales d???utilisation doivent ??tre r??dig??es dans le respect des dispositions l??gales applicables : en aucun cas une clause ne peut d??roger aux dispositions d???ordre public. Au-del?? de cet imp??ratif, l?????diteur du site peut pr??voir toute mention utile pour informer l???internaute sur le contenu du site d???une part, se prot??ger contre d?????ventuels litiges avec l???utilisateur d???autre part.\n"
			+ "\n" + "Le contenu des CGU inclut notamment :\n" + "\n"
			+ "La description du site : les conditions g??n??rales d???utilisation peuvent pr??ciser l???objectif du site et d??crire les services propos??s. Attention ?? pr??voir une description large, pour s?????viter d???avoir ?? modifier les CGU ?? chaque ??volution du site.\n"
			+ "Les droits et les obligations de l???utilisateur : les droits de l???utilisateur peuvent concerner notamment sa marge de man??uvre au moment de la cr??ation d???un espace personnel. Ses obligations peuvent inclure l???obligation de maintenir le caract??re confidentiel de ses identifiants de connexion, l???obligation d???utiliser le site conform??ment ?? sa destination, l???obligation de ne pas tenter de nuire au bon fonctionnement du site??? Ces dispositions des conditions g??n??rales d???utilisation permettent d???engager sa responsabilit?? en cas de dommage r??sultant du non-respect desdites obligations.\n"
			+ "Les droits et les obligations de l?????diteur : les conditions g??n??rales d???utilisation mettent en g??n??ral des obligations de moyens ?? la charge de l?????diteur. Maintenir l???acc??s au site, assurer le bon fonctionnement du site??? Les CGU sont aussi l???occasion pour l?????diteur de se d??charger de sa responsabilit?? en cas d???utilisation frauduleuse du site par un tiers, par exemple.\n"
			+ "Les conditions d???utilisation du forum ou d???un espace de libre ??change : lorsque l???utilisateur est autoris?? ?? publier lui-m??me du contenu sur le site, les conditions g??n??rales d???utilisation du site encadrent cet espace de libre ??change. L?????diteur, notamment, peut limiter sa responsabilit?? de mani??re ?? ne pas ??tre tenu responsable en cas de propos injurieux ou de publication de contenu contrefaisant les droits de propri??t?? intellectuelle d???un tiers.\n"
			+ "La responsabilit?? limit??e de l?????diteur quant aux liens hypertextes : des liens de redirection du site peuvent pointer vers des sites tiers. Les conditions g??n??rales d???utilisation pr??cisent le lien juridique entre le site et le site tiers, et permettent de d??charger l?????diteur de sa responsabilit?? en cas de contenu illicite sur le site tiers vers lequel le lien hypertexte redirige.\n"
			+ "Un rappel du droit de la propri??t?? intellectuelle : les conditions g??n??rales d???utilisation rappellent aux internautes que les ??l??ments du site ??? textes, vid??os, images??? ??? sont prot??g??s par le droit d???auteur et que leur utilisation sans autorisation pr??alable expresse est interdite.\n"
			+ "Un rappel sur la force majeure : la force majeure, un ??v??nement ext??rieur impr??visible et insurmontable, exon??re les parties de leurs responsabilit??s respectives. Cette disposition est d???ordre public.\n"
			+ "Les modalit??s de r??glement des litiges : l?????diteur peut mentionner qu???en cas de litige, les parties tenteront de r??gler le conflit ?? l???amiable. A d??faut de r??glement amiable, l?????diteur doit respecter les r??gles d???attribution de comp??tence mat??rielle et territoriale. Par exemple : le consommateur doit avoir le choix entre le tribunal de son domicile ou le tribunal du lieu de la soci??t?? ??ditrice du site.\n"
			+ "Faire accepter les conditions g??n??rales d???utilisation :\n"
			+ "Les conditions g??n??rales d???utilisation n???ont une valeur l??gale qu????? condition d?????tre accept??es par l???internaute au moment d???entrer sur le site. L???acceptation des CGU peut ??tre mat??rialis??e par une case ?? cocher, de type : ?? En cochant cette case, vous certifiez avoir lu et accept?? sans r??serve les pr??sentes ??.\n"
			+ "\n";

}
