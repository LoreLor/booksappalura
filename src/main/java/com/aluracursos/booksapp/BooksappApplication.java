package com.aluracursos.booksapp;

import com.aluracursos.booksapp.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksappApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BooksappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		//llamo al método
		principal.muestraMenu();
	}
}
