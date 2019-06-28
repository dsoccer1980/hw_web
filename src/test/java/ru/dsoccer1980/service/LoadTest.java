package ru.dsoccer1980.service;

import com.github.cloudyrock.mongock.Mongock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.dsoccer1980.domain.Book;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class LoadTest {

    private static int threadsCount = 10000;
    private static CountDownLatch latchStartThreads = new CountDownLatch(threadsCount);
    private static CountDownLatch latchAllThreadDone = new CountDownLatch(threadsCount);

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private Mongock mongock;

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @Test
    void loadTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            executorService.submit(new BookSaveRunnable(bookService, i));
        }
        executorService.shutdown();
        latchAllThreadDone.await();

        List<Book> books = bookService.getAll();
        assertThat(books.size()).isEqualTo(threadsCount + 3);
        assertThat(books.get(4).getName()).contains("XXX");
    }


    class BookSaveRunnable implements Runnable {
        private final BookService bookService;
        private int index;

        public BookSaveRunnable(BookService bookService, int index) {
            this.bookService = bookService;
            this.index = index;
        }

        @Override
        public void run() {
            LoadTest.latchStartThreads.countDown();
            try {
                LoadTest.latchStartThreads.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bookService.save(new Book("nameshit " + index, null, null), "100000000000000000000001", "100000000000000000000010");
            LoadTest.latchAllThreadDone.countDown();
        }
    }

}
