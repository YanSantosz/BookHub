package br.com.alura.BookHub.View;

import br.com.alura.BookHub.Model.DadosLivros;
import br.com.alura.BookHub.Model.GoogleBooksResponse;
import br.com.alura.BookHub.Model.ItemLivro;
import br.com.alura.BookHub.Model.Livro;
import br.com.alura.BookHub.Repository.LivroRepository;
import br.com.alura.BookHub.Service.ConsumoApi;
import br.com.alura.BookHub.Service.ConverteDados;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.Optional;

@Component
public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = System.getenv("GBOOK_BASEURL") != null
            ? System.getenv("GBOOK_BASEURL")
            : "https://www.googleapis.com/books/v1/volumes?q=";

    private final String API_KEY = System.getenv("GBOOK_APIKEY");

    private final LivroRepository repositorio;

    public Principal(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibirMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    \n--------------------------------
                    BOOKHUB - BIBLIOTECA
                    --------------------------------
                    1 - Pesquisar livro
                    2 - Pesquisar livro por autor
                    3 - Listar livros salvos
                    
                    0 - Sair
                    --------------------------------
                    Escolha uma opção:
                    """;

            System.out.println(menu);

            try {
                String entrada = scanner.nextLine();
                opcao = Integer.parseInt(entrada.trim());

                switch (opcao) {
                    case 1 -> buscarLivros();
                    case 2 -> pesquisarLivroAutor();
                    case 3 -> listarLivrosDoBanco();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números.");
                scanner.nextLine();
                opcao = -1;
            }
        }
        scanner.close();
    }

    private void buscarLivros() {
        System.out.println("\nDigite o nome do livro para busca:");
        var nomeLivro = scanner.nextLine().trim();

        List<ItemLivro> livros = buscarNaApi(nomeLivro.replace(" ", "+"));

        if (livros == null) {
            System.out.println("\nNenhum livro encontrado com esse nome.");
            return;
        }

        exibirEsalvarLivros(livros);
    }


    private void pesquisarLivroAutor() {
        System.out.print("Digite o nome do autor: ");
        var nomeAutor = scanner.nextLine().trim();

        List<ItemLivro> livros = buscarNaApi("inauthor:" + nomeAutor.replace(" ", "+"));

        if (livros == null) {
            System.out.println("\nNenhum livro encontrado desse autor.");
            return;
        }

        exibirEsalvarLivros(livros);
    }


    private List<ItemLivro> buscarNaApi(String parametro) {
        String url = ENDERECO + parametro + "&key=" + API_KEY;

        var json = consumo.obterDados(url);
        GoogleBooksResponse response = conversor.obterDados(json, GoogleBooksResponse.class);

        if (response.items() == null || response.items().isEmpty()) {
            return null;
        }

        return response.items();
    }


    private void exibirEsalvarLivros(List<ItemLivro> livrosEncontrados) {
        System.out.println("\n--- RESULTADOS ENCONTRADOS ---");

        for (int i = 0; i < livrosEncontrados.size(); i++) {
            DadosLivros dados = livrosEncontrados.get(i).volumeInfo();

            String autor = (dados.autores() != null && !dados.autores().isEmpty())
                    ?  String.join(", ", dados.autores())
                    : "Autor não informado";

            String categorias = (dados.categoria() != null && !dados.categoria().isEmpty())
                    ? String.join(", ", dados.categoria())
                    : "Categoria não informada";

            System.out.println("\n----- LIVRO " + (i + 1) + " -----");
            System.out.println("Título: " + dados.titulo());
            System.out.println("Autores: " + autor);
            System.out.println("Categorias: " + categorias);
            System.out.println("Páginas: " + dados.totalPaginas());
            System.out.println("-------------------");
        }

        System.out.println("\nDigite o NÚMERO do livro que deseja salvar:");
        try {
            String entrada = scanner.nextLine();
            int escolha = Integer.parseInt(entrada.trim());

            if (escolha > 0 && escolha <= livrosEncontrados.size()) {
                ItemLivro livroEscolhido = livrosEncontrados.get(escolha - 1);
                salvarLivro(livroEscolhido);
            } else if (escolha == 0) {
                System.out.println("Operação cancelada.");
            } else {
                System.out.println("Número inválido!");
            }
        } catch (Exception e) {
            System.out.println("Erro: Digite apenas números!");
            scanner.nextLine();
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
