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


    public Livro() {
    }

    public Livro(String googleBooksId, DadosLivros dados) {
        this.googleBooksId = googleBooksId;
        this.titulo = dados.titulo();
        this.subtitulo = dados.subtitulo();
        this.autores = (dados.autores() != null && !dados.autores().isEmpty())
            ? dados.autores()
            : List.of("Autor não informado");
        this.categorias = (dados.categoria() != null && !dados.categoria().isEmpty())
                ? dados.categoria()
                : List.of("Categoria não informada");
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



    public void setId(Long id) {
        this.id = id;
    }

    public void setGoogleBooksId(String googleBooksId) {
        this.googleBooksId = googleBooksId;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores != null ? autores : new ArrayList<>();
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias != null ? categorias : new ArrayList<>();
    }

    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }



    @Override
    public String toString() {
        String autoresStr = (autores != null && !autores.isEmpty())
                ? String.join(", ", autores)
                : "Autor não informado";

        String categoriasStr = (categorias != null && !categorias.isEmpty())
                ? String.join(", ", categorias)
                : "Categoria não informada";
        return "\n----- LIVRO -----" +
                "\nTítulo: " + titulo +
                "\nAutor: " + autoresStr +
                "\nCategorias: " + categoriasStr +
                "\nPáginas: " + totalPaginas +
                "\n-----------------";
    }
}
