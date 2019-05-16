package ru.dsoccer1980.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.dto.BookDto;
import ru.dsoccer1980.repository.BookRepository;
import ru.dsoccer1980.service.BookService;
import ru.dsoccer1980.util.exception.NotFoundException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @GetMapping("/book")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/book/id/{id}")
    public Book getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping("/book/edit/{id}")
    public Book edit(@PathVariable("id") String id) {
        return bookService.get(id);
    }

    @PutMapping(value = "/book/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book update(@RequestBody BookDto bookDto) {
        return bookService.save(BookDto.getBook(bookDto), bookDto.getAuthorId(), bookDto.getGenreId());
    }

    @PostMapping(value = "/book/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book create(@RequestBody BookDto bookDto) {
        return bookService.save(BookDto.getBook(bookDto), bookDto.getAuthorId(), bookDto.getGenreId());
    }

    @DeleteMapping(value = "/book/delete")
    public void delete(@RequestParam("id") String id) {
        bookService.delete(id);
    }
}





