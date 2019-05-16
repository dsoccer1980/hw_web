package ru.dsoccer1980.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.dto.BookDto;
import ru.dsoccer1980.repository.BookRepository;
import ru.dsoccer1980.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @GetMapping("/book")
    public Flux<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/book/id/{id}")
    public Mono<Book> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id);
    }

    @GetMapping("/book/edit/{id}")
    public Mono<Book> edit(@PathVariable("id") String id) {
        return bookService.get(id);
    }

    @PutMapping(value = "/book/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> update(@RequestBody BookDto bookDto) {
        return bookService.save(BookDto.getBook(bookDto), bookDto.getAuthorId(), bookDto.getGenreId());
    }

    @PostMapping(value = "/book/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> create(@RequestBody BookDto bookDto) {
        return bookService.save(BookDto.getBook(bookDto), bookDto.getAuthorId(), bookDto.getGenreId());
    }

    @DeleteMapping(value = "/book/delete")
    public Mono<Void> delete(@RequestParam("id") String id) {
        return bookService.delete(id);
    }
}





