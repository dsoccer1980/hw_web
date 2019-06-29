package ru.dsoccer1980.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.dto.BookDto;
import ru.dsoccer1980.integration.BookGateway;
import ru.dsoccer1980.service.BookService;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookGateway bookGateway;

    @GetMapping("/book")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable("id") String id) {
        return bookService.get(id);
    }

    @PutMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book update(@RequestBody BookDto bookDto) {
        bookGateway.process(bookDto);
        return bookService.save(BookDto.getBook(bookDto), bookDto.getAuthorId(), bookDto.getGenreId());
    }

    @PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book create(@RequestBody BookDto bookDto) {
        bookGateway.process(bookDto);
        return bookService.save(BookDto.getBook(bookDto), bookDto.getAuthorId(), bookDto.getGenreId());
    }

    @DeleteMapping(value = "/book")
    public void delete(@RequestParam("id") String id) {
        bookService.delete(id);
    }
}





