package ru.dsoccer1980.rest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.exception.NotFoundExcepton;
import ru.dsoccer1980.repository.AuthorRepository;
import ru.dsoccer1980.repository.BookRepository;
import ru.dsoccer1980.repository.GenreRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/")
    public String root(Model model) {
        return "redirect:/book";
    }

    @GetMapping("/book")
    public String getAll(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "listBooks";
    }

    @GetMapping("/book/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundExcepton::new);
        model.addAttribute("book", book);
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "editBooks";
    }

    @GetMapping("/book/create")
    public String create(Model model) {
        model.addAttribute("book", new Book("Имя книги", new Author("Автор"), new Genre("Жанр")));
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "editBooks";
    }

    @PostMapping(value = "/book/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(Book book, HttpServletRequest request) {
        String authorId = request.getParameter("author_id");
        Author author = authorRepository.findById(authorId).orElseThrow(NotFoundExcepton::new);
        String genreId = request.getParameter("genre_id");
        Genre genre = genreRepository.findById(genreId).orElseThrow(NotFoundExcepton::new);
        if (book.getId().equals("") || book.getId() == null) {
            book = new Book(book.getName(), book.getAuthor(), book.getGenre());
        }
        book.setAuthor(author);
        book.setGenre(genre);
        bookRepository.save(book);
        return "redirect:/book";
    }

    @GetMapping("/book/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        bookRepository.deleteById(id);
        return "redirect:/book";
    }
}
