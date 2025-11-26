package br.com.alura.BookHub.Model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(
        @JsonAlias("title") String titulo,
        @JsonAlias("subtitle") String subtitulo,
        @JsonAlias("authors") List<String> autores,
        @JsonAlias("categories") List<String> categoria,
        @JsonAlias("pageCount") Integer totalPaginas
) {}
