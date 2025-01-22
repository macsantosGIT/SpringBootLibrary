package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.model.GeneroLivro;
import io.github.curso.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("98887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980,10,5));

        Autor autor = autorRepository
                .findById(UUID.fromString("e42bd6fc-207f-4238-8c8a-f54be823b182"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarAutorLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("98887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980,10,5));

        Autor autor = new Autor();
        autor.setNome("Erick");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2000,3,31));

        autorRepository.save(autor);

        livro.setAutor(autor);
        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("98887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980,10,5));

        Autor autor = new Autor();
        autor.setNome("Erick");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2000,3,31));

        livro.setAutor(autor);
        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivroTest(){
        UUID id = UUID.fromString("4a1d8e5c-50aa-49b2-bd2f-6d8b3969a90e");
        var livroPraAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("e42bd6fc-207f-4238-8c8a-f54be823b182");
        Autor autor = autorRepository.findById(idAutor).orElse(null);
        livroPraAtualizar.setAutor(autor);

        repository.save(livroPraAtualizar);
    }

    @Test
    public void deletarTest(){
        UUID id = UUID.fromString("4a1d8e5c-50aa-49b2-bd2f-6d8b3969a90e");
        repository.deleteById(id);
    }

    @Test
    public void buscarLivroTest(){
        UUID id = UUID.fromString("7f7e0d5b-20ff-4758-ae67-15d87d00a8d4");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());

        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> livros = repository.findByTitulo("UFO");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest(){
        List<Livro> livros = repository.findByIsbn("27589-98384");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloEPreco(){
        var preco = BigDecimal.valueOf(100.00);
        List<Livro> livros = repository.findByTituloAndPreco("UFO", preco);
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloEOuIsbn(){
        List<Livro> livros = repository.findByTituloOrIsbn("UFO", "27589-98384");
        livros.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosComQueryJPQL(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivrosComQueryJPQL(){
        var resultado = repository.listarNomeDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGeneroAutoresBrasileirosLivrosComQueryJPQL(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParam(){
        var resultado = repository.findByGenero(GeneroLivro.MISTERIO, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParam(){
        var resultado = repository.findByGenero(GeneroLivro.MISTERIO, "preco");
        resultado.forEach(System.out::println);
    }

}