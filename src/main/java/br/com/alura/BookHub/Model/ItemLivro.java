package br.com.alura.BookHub.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemLivro(
        String kind,
        String id,
        String etag,
        String selfLink,
        DadosLivros volumeInfo) {}