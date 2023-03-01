package org.satel.ressatel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		/*
		 * Used for forwarding to the index page by default. This will trigger
		 * the login.
		 */
// TODO при работе страниц здесь только GET, поэтому компоненты на странице с POST и др. методами в составе могут не работать, например, кнопки p:commandButton и др. При ошибках переносить нужный контроллер отсюда в CardController
//		registry.addViewController("/").setViewName("forward:/index.xhtml");
		registry.addViewController("/persons").setViewName("forward:/persons.xhtml");
		registry.addViewController("/employees").setViewName("forward:/employees.xhtml");
		registry.addViewController("/companies").setViewName("forward:/companies.xhtml");
		registry.addViewController("/skills").setViewName("forward:/skills_directory.xhtml");
		registry.addViewController("/roles").setViewName("forward:/roles_directory.xhtml");
		registry.addViewController("/skills/edit").setViewName("forward:/skills_directory_edit.xhtml");
		registry.addViewController("/roles/edit").setViewName("forward:/roles_directory_edit.xhtml");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

}
