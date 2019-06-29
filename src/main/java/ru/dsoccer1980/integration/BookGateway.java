package ru.dsoccer1980.integration;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.dsoccer1980.dto.BookDto;

@MessagingGateway
public interface BookGateway {

    @Gateway(requestChannel = "itemsChannel", replyChannel = "booksChannel")
    BookDto process(BookDto book);
}
