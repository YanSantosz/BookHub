package br.com.alura.BookHub;

import br.com.alura.BookHub.Model.DadosLivros;
import br.com.alura.BookHub.Model.GoogleBooksResponse;
import br.com.alura.BookHub.Service.ConsumoApi;
import br.com.alura.BookHub.Service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookHubApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookHubApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        var consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados("https://www.googleapis.com/books/v1/volumes?q=Senhor+dos+aneis&key=AIzaSyA7QdIwUKz-ihWklwhVr3n1KX7ZgBrgs-w");

        ConverteDados conversor = new ConverteDados();
        GoogleBooksResponse response = conversor.obterDados(json, GoogleBooksResponse.class);

        System.out.println("=== INFORMAÇÕES DA BUSCA ===");
        System.out.println("Total de livros encontrados: " + response.totalItems());
        System.out.println("Quantidade retornada nesta página: " + response.items().size());

        System.out.println("\n=== PRIMEIRO LIVRO ===");
        DadosLivros primeiroLivro = response.items().get(0).volumeInfo();

        System.out.println("Título: " + primeiroLivro.title());
        System.out.println("Subtítulo: " + primeiroLivro.subtitle());
        System.out.println("Autores: " + primeiroLivro.authors());
        System.out.println("Categorias: " + primeiroLivro.categories());

        System.out.println("\n=== TODOS OS LIVROS DA PRIMEIRA PÁGINA ===");
        response.items().forEach(item -> {
            DadosLivros livro = item.volumeInfo();
            System.out.println("\n- Título: " + livro.title());
            System.out.println("  Autores: " + livro.authors());
        });
    }
}
