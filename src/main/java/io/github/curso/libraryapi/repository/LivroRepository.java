package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.model.GeneroLivro;
import io.github.curso.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query method
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);
    List<Livro> findByTitulo(String titulo);
    List<Livro> findByIsbn(String isbn);
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);
    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //JPQL referencia as entidades e as propriedades
    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id_autor
     */
    @Query(" select a from Livro as l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    @Query(" select distinct l.titulo from Livro as l ")
    List<String> listarNomeDiferentesLivros();

    @Query("""
            select  l.genero 
            from Livro as l 
            join l.autor a 
            where a.nacionalidade = 'Brasileira' 
            order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    //named parameters -> parametros nomeados
    @Query(" select l from Livro l where l.genero = :genero order by :paramOrdenacao ")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropiedade
    );

    //parametros posicionais
    @Query(" select l from Livro l where l.genero = ?1 order by ?2 ")
    List<Livro> findByGeneroPositionalParameters(
            GeneroLivro generoLivro,
            String nomePropiedade
    );
}
