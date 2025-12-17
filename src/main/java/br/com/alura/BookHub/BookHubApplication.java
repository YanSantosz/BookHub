package br.com.alura.BookHub;

import br.com.alura.BookHub.Repository.LivroRepository;
import br.com.alura.BookHub.View.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookHubApplication implements CommandLineRunner {

    @Autowired
    private LivroRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(BookHubApplication.class, args);
	}

    @Override
    public void run(String... args){
        Principal principal = new Principal(repositorio);
        principal.exibirmenu();
    }
}
