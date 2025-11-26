package br.com.alura.BookHub.View;

import br.com.alura.BookHub.Model.DadosLivros;
import br.com.alura.BookHub.Model.GoogleBooksResponse;
import br.com.alura.BookHub.Model.ItemLivro;
import br.com.alura.BookHub.Model.Livro;
import br.com.alura.BookHub.Repository.LivroRepository;
import br.com.alura.BookHub.Service.ConsumoApi;
import br.com.alura.BookHub.Service.ConverteDados;

import java.util.List;
import java.util.Scanner;
import java.util.Optional;

public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.googleapis.com/books/v1/volumes?q=";
    private final String API_KEY = "&key=AIzaSyA7QdIwUKz-ihWklwhVr3n1KX7ZgBrgs-w";

    private LivroRepository repositorio;

    public Principal(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibirmenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    \n--------------------------------
                    BOOKHUB - BIBLIOTECA
                    --------------------------------
                    1 - Pesquisar Novo Livro (e escolher qual salvar)
                    2 - Ver meus livros salvos
                    
                    0 - Sair
                    --------------------------------
                    Escolha uma opção: 
                    """;

            System.out.println(menu);

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> buscarLivros();
                    case 2 -> listarLivrosDoBanco();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida");
                }
            } catch (Exception e) {
                System.out.println("Erro: Digite apenas números.");
                scanner.nextLine();
            }
        }
    }

    private void buscarLivros() {
        System.out.println("\nDigite o nome do livro para busca:");
        var nomeLivro = scanner.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+") + API_KEY);
        GoogleBooksResponse response = conversor.obterDados(json, GoogleBooksResponse.class);

        if (response.items() == null || response.items().isEmpty()) {
            System.out.println("\nNenhum livro encontrado com esse nome.");
            return;
        }

        List<ItemLivro> livrosEncontrados = response.items();

        System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
        for (int i = 0; i < livrosEncontrados.size(); i++) {
            DadosLivros dados = livrosEncontrados.get(i).volumeInfo();
            String autor = (dados.autores() != null && !dados.autores().isEmpty())
                    ? dados.autores().get(0)
                    : "Desconhecido";

            System.out.println("\n----- LIVRO " + (i + 1) + " -----");
            System.out.println("Título: " + dados.titulo());
            System.out.println("Autores: " + dados.autores());
            System.out.println("Categorias: " + dados.categoria());
            System.out.println("Páginas: " + dados.totalPaginas());
            System.out.println("-------------------");
        }

        System.out.println("\nDigite o NÚMERO do livro que deseja salvar (ou 0 para cancelar):");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha > 0 && escolha <= livrosEncontrados.size()) {
            ItemLivro livroEscolhido = livrosEncontrados.get(escolha - 1);
            salvarLivro(livroEscolhido);
        } else if (escolha == 0) {
            System.out.println("Operação cancelada.");
        } else {
            System.out.println("Número inválido!");
        }
    }

    private void salvarLivro(ItemLivro itemLivro) {
        Optional<Livro> livroExistente = repositorio.findByGoogleBooksId(itemLivro.id());

        if (livroExistente.isPresent()) {
            System.out.println("\nERRO: O livro '" + livroExistente.get().getTitulo() + "' já está salvo na sua lista!");
        } else {
            Livro livro = new Livro(itemLivro.id(), itemLivro.volumeInfo());
            repositorio.save(livro);
            System.out.println("\nSUCESSO! Livro salvo: " + livro.getTitulo());
        }
    }

    private void listarLivrosDoBanco() {
        List<Livro> livros = repositorio.findAll();
        if (livros.isEmpty()) {
            System.out.println("\nSua lista está vazia.");
        } else {
            System.out.println("\n--- MEUS LIVROS SALVOS ---");
            livros.forEach(System.out::println);
        }
    }
}