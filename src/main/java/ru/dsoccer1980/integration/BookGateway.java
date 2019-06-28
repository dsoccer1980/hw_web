package ru.dsoccer1980.integration;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.dsoccer1980.domain.Book;

@MessagingGateway
public interface BookGateway {

    @Gateway(requestChannel = "itemsChannel", replyChannel = "booksChannel")
    Book process(Book book);
}
