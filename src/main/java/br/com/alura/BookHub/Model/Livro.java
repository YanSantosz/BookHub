package br.com.alura.BookHub.Model;

import java.util.List;


public class Livro {
    private Long id;
    private String googleBooksId;
    private String titulo;
    private String subtitulo;
    private List<String> autores;
    private List<String> categorias;
    private Integer totalPaginas;
}
