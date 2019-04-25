package ru.dsoccer1980.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.dsoccer1980.domain.Book;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public void removeAuthorArrayElementsById(String id) {
        val query = Query.query(Criteria.where("_id").is(new ObjectId(id)));
        val update = new Update().set("author", query);
        mongoTemplate.updateMulti(query, update, Book.class);
    }

    @Override
    public void removeGenreArrayElementsById(String id) {
        val query = Query.query(Criteria.where("_id").is(new ObjectId(id)));
        val update = new Update().set("genre", query);
        mongoTemplate.updateMulti(query, update, Book.class);

    }

}
