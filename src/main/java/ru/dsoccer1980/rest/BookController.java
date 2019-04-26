package ru.dsoccer1980.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.service.AuthorService;
import ru.dsoccer1980.service.BookService;
import ru.dsoccer1980.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;


    @GetMapping("/")
    public String root(Model model) {
        return "redirect:/book";
    }

    @GetMapping("/book")
    public String getAll(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "listBooks";
    }

    @GetMapping("/book/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        List<Genre> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "editBooks";
    }

    @PostMapping(value = "/book/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(Book book,
                       @RequestParam(value = "author_id", required = false) String authorId,
                       @RequestParam(value = "genre_id", required = false) String genreId) {

        bookService.save(book, authorId, genreId);
        return "redirect:/book";
    }

    @PostMapping("/book/delete")
    public String delete(@RequestParam("id") String id) {
        bookService.delete(id);
        return "redirect:/book";
    }
}
