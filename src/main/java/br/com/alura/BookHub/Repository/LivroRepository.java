package br.com.alura.BookHub.Repository;

import br.com.alura.BookHub.Model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByGoogleBooksId(String googleBooksId);

    List<Livro> findByAutoresContainingIgnoreCase(String autores);
}