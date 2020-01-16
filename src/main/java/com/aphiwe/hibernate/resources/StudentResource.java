package com.aphiwe.hibernate.resources;
import com.aphiwe.hibernate.core.Student;
import com.aphiwe.hibernate.db.StudentDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    private StudentDAO studentDAO;

    public StudentResource(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GET
    @UnitOfWork
    public List<Student> findByName(
            @QueryParam("name") String name ) {
        return studentDAO.findByName(name);
    }
    @GET
    @UnitOfWork
    @Path("/all")
    public List<Student> findAll( ) {
        return studentDAO.findAll();  }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Student findById(@PathParam("id") LongParam id) {
        return studentDAO.findById(id.get()).get();
    }
}
