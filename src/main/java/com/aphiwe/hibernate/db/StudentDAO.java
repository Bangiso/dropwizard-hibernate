package com.aphiwe.hibernate.db;
import com.aphiwe.hibernate.core.*;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.logback.shaded.guava.base.Optional;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAO extends AbstractDAO<Student> {

    public StudentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Student> findAll() {
        return list((Query<Student>) namedQuery("com.aphiwe.hibernate.core.Student.findAll"));
    }

    public List<Student> findByName(String name) {
        StringBuilder builder = new StringBuilder("%");
        builder.append(name).append("%");
        return list((Query<Student>) namedQuery("com.aphiwe.hibernate.core.Student.findByName")
                .setParameter("name", builder.toString())
        );
    }
    public Optional<Student> findById(long id) {
        return Optional.fromNullable(get(id));
    }
}

