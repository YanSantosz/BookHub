package br.com.alura.BookHub.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alura_livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String googleBooksId;

    private String titulo;
    private String subtitulo;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> autores = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> categorias = new ArrayList<>();

    private Integer totalPaginas;

    public Livro(String googleBooksId, DadosLivros dados) {
        this.googleBooksId = googleBooksId;
        this.titulo = dados.titulo();
        this.subtitulo = dados.subtitulo();
        this.autores = dados.autores() != null ? dados.autores() : new ArrayList<>();
        this.categorias = dados.categoria() != null ? dados.categoria() : new ArrayList<>();
        this.totalPaginas = dados.totalPaginas();
    }

    public Long getId() {
        return id;
    }

    public String getGoogleBooksId() {
        return googleBooksId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public List<String> getAutores() {
        return autores;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    @Override
    public String toString() {
        return "\n----- LIVRO -----" +
                "\nTítulo: " + titulo +
                "\nAutores: " + autores +
                "\nCategorias: " + categorias +
                "\nPáginas: " + totalPaginas +
                "\n-----------------";
    }
}