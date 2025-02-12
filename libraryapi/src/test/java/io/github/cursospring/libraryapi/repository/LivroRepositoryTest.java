package io.github.cursospring.libraryapi.repository;

import io.github.cursospring.libraryapi.model.Autor;
import io.github.cursospring.libraryapi.model.GeneroLivro;
import io.github.cursospring.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;

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
        livro.setIsbn("98887-85974");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("A lenda da ilha perdida");
        livro.setDataPublicacao(LocalDate.of(1980,10,5));

        Autor autor = autorRepository
                .findById(UUID.fromString("f73ce866-0b65-4522-b6f1-ade76d44531e"))
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
        UUID id = UUID.fromString("693c811d-03d8-4f87-9879-292399bdd69d");
        var livroPraAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("f73ce866-0b65-4522-b6f1-ade76d44531e");
        Autor autor = autorRepository.findById(idAutor).orElse(null);
        //livroPraAtualizar.setAutor(autor);
        livroPraAtualizar.setTitulo("A ilha perdida");

        repository.save(livroPraAtualizar);
    }

    @Test
    public void deletarTest(){
        UUID id = UUID.fromString("4a1d8e5c-50aa-49b2-bd2f-6d8b3969a90e");
        repository.deleteById(id);
    }

    @Test
//    @Transactional
    public void buscarLivroTest(){
        UUID id = UUID.fromString("dc383b7c-3a04-4684-84ee-7da1d2c87431");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());

//        System.out.println("Autor: ");
//        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisarPorTituloTest(){
        List<Livro> livros = repository.findByTitulo("UFO");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisarPorIsbnTest(){
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
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.MISTERIO, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest(){
        repository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacaoTest(){
        UUID id = UUID.fromString("dc383b7c-3a04-4684-84ee-7da1d2c87431");
        repository.updateDataPublicacao(LocalDate.of(2000,2,2), id);
    }

}