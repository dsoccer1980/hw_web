package ru.dsoccer1980.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.dto.BookDto;
import ru.dsoccer1980.service.BookService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    public Flux<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/book/{id}")
    public Mono<Book> getBook(@PathVariable("id") String id) {
        return bookService.get(id);
    }

    @PutMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> update(@RequestBody BookDto bookDto) {
        return bookService.save(BookDto.getBook(bookDto), bookDto.getAuthorId(), bookDto.getGenreId());
    }

    @PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> create(@RequestBody BookDto bookDto) {
        return bookService.save(BookDto.getBook(bookDto), bookDto.getAuthorId(), bookDto.getGenreId());
    }

    @DeleteMapping(value = "/book")
    public Mono<Void> delete(@RequestParam("id") String id) {
        return bookService.delete(id);
    }
}





