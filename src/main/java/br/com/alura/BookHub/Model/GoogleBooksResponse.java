package br.com.alura.BookHub.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleBooksResponse(
        String kind,
        Integer totalItems,
        List<ItemLivro> items)
{}
