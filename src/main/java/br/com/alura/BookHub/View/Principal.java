package br.com.alura.BookHub.View;

import br.com.alura.BookHub.Model.DadosLivros;
import br.com.alura.BookHub.Model.GoogleBooksResponse;
import br.com.alura.BookHub.Model.ItemLivro;
import br.com.alura.BookHub.Service.ConsumoApi;
import br.com.alura.BookHub.Service.ConverteDados;

import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.googleapis.com/books/v1/volumes?q=";
    private final String API_KEY = "&key=AIzaSyA7QdIwUKz-ihWklwhVr3n1KX7ZgBrgs-w";

    public void exibirmenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║      BOOKHUB - BIBLIOTECA      ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("1 - Buscar livro");
            System.out.println("2 - Listar livros salvos");
            System.out.println("3 - Buscar livro por título");
            System.out.println("0 - Sair");
            System.out.print("\nEscolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    verMeusLivros();
                    break;
                case 3:
                    adcionarLivros();
                    break;
                case 0:
                    System.out.println("\n Saindo... Até logo!");
                    break;
                default:
                    System.out.println("\n Opção inválida!");
            }
        }
    }

    private void buscarLivro() {
        System.out.print("\nDigite o nome do livro: ");
        String nomeLivro = scanner.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+") + API_KEY);

        GoogleBooksResponse response = conversor.obterDados(json, GoogleBooksResponse.class);

        if (response.items() == null || response.items().isEmpty()) {
            System.out.println("\nNenhum livro encontrado!");
            return;
        }

        int contador = 1;
        for (ItemLivro item : response.items()) {
            DadosLivros livro = item.volumeInfo();

            System.out.println("─────────────────────────────────────────");
            System.out.println("LIVRO " + contador);
            System.out.println("─────────────────────────────────────────");
            System.out.println("Título: " + livro.titulo());
            System.out.println("Subtítulo: " + livro.subtitulo());
            System.out.println("Autores: " + livro.autores());
            System.out.println("Categoria: " + livro.categorias());
            System.out.println("Páginas: " + livro.TotalPaginas());
            System.out.println();

            contador++;
        }
    }


    private void verMeusLivros() {
        System.out.println("\nVer meus livros salvos");
    }

    private void adcionarLivros() {
        System.out.println("\nAdicionar livro a biblioteca");
        System.out.print("Digite o nome do livro para buscar:");
        String nomeLivro = scanner.nextLine();
    }
}