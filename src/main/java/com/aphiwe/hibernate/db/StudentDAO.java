package com.aphiwe.hibernate.db;
import com.aphiwe.hibernate.core.*;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.logback.shaded.guava.base.Optional;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

public class StudentDAO extends AbstractDAO<Student> {

    private Logger logger = Logger.getLogger(StudentDAO.class.getName());
    private  SessionFactory sessionFactory;

    public StudentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
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

    public boolean save(Student student) {
        try{
            int ret = sessionFactory.getCurrentSession()
                    .createSQLQuery("INSERT INTO students (id, first_name, last_name, email, field, gpa)"
                            + "VALUES (:id, :firstName, :lastName, :email, :field, :gpa)")
                    .setParameter("id",student.getId())
                    .setParameter("firstName", student.getFirstName())
                    .setParameter("lastName", student.getLastName())
                    .setParameter("field", student.getField())
                    .setParameter("email", student.getEmail())
                    .setParameter("gpa", student.getGpa()).executeUpdate();

            return ret>0;
        } catch (Throwable ex){
            logger.info(ex.getMessage());
            return false;
        }
    }

    public boolean update(long id, Student student) {
        try{
            int ret = sessionFactory.getCurrentSession()
                    .createSQLQuery("UPDATE students SET  first_name = :firstName, last_name = :lastName, email = :email, field = :field, gpa = :gpa "
                            + "WHERE id = :id")
                    .setParameter("firstName", student.getFirstName())
                    .setParameter("lastName", student.getLastName())
                    .setParameter("field", student.getField())
                    .setParameter("email", student.getEmail())
                    .setParameter("gpa", student.getGpa())
                    .setParameter("id",id).executeUpdate();
            return ret>0;
        } catch (Throwable ex){
            logger.error(ex.getMessage());
            return false;
        }
    }
    public boolean delete(long id) {
        try{
            int ret = sessionFactory.getCurrentSession()
                    .createSQLQuery("DELETE FROM students WHERE id = :id")
                    .setParameter("id",id).executeUpdate();
            return ret>0;
        } catch (Throwable ex){
            logger.error(ex.getMessage());
            return false;
        }
    }
}

