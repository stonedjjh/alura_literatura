package com.aluraapi.literatura;

import com.aluraapi.literatura.principal.MenuOpciones;
import com.aluraapi.literatura.service.AutorService;
import com.aluraapi.literatura.service.LibroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LiteraturaApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(LiteraturaApplication.class);
		app.setHeadless(false);
		ApplicationContext context = app.run(args);

		LibroService libroService = context.getBean(LibroService.class);
		AutorService autorService = context.getBean(AutorService.class);

		new MenuOpciones(libroService, autorService);
	}
}
