package ru.dsoccer1980.rest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.dto.BookDto;
import ru.dsoccer1980.integration.BookGateway;
import ru.dsoccer1980.service.BookService;
import ru.dsoccer1980.web.rest.BookController;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Disabled
public class BookControllerLoadTest {

    private static int threadsCount = 10;
    private static CountDownLatch latchStartThreads = new CountDownLatch(threadsCount);
    private static CountDownLatch latchAllThreadDone = new CountDownLatch(threadsCount);

    @MockBean
    private BookGateway bookGateway;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookController bookController;

    @Test
    void loadTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            BookDto bookDto = new BookDto(String.valueOf(111111111110L + i), "nameshit", null, null, null);
            BookDto bookDto2 = new BookDto(bookDto.getId(), "nameXXX", null, null, null);
            given(bookGateway.process(bookDto)).willReturn(bookDto2);
            executorService.submit(new BookControllerLoadTest.BookSaveRunnable(bookDto));
        }
        executorService.shutdown();
        latchAllThreadDone.await();

        List<Book> books = bookService.getAll();
        assertThat(books.size()).isEqualTo(threadsCount + 3);
        assertThat(books.get(4).getName()).contains("XXX");
    }

    class BookSaveRunnable implements Runnable {
        private final BookDto bookDto;

        public BookSaveRunnable(BookDto bookDto) {
            this.bookDto = bookDto;
        }

        @Override
        public void run() {
            BookControllerLoadTest.latchStartThreads.countDown();
            try {
                BookControllerLoadTest.latchStartThreads.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bookController.create(bookDto);
            BookControllerLoadTest.latchAllThreadDone.countDown();
        }
    }
}
