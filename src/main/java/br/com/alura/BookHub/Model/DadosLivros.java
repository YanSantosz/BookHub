package br.com.alura.BookHub.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(
        String title,
        String subtitle,
        List<String> authors,
        List<String> categories
) {}
