package com.aphiwe.hibernate.resources;

import com.aphiwe.hibernate.core.Student;
import com.aphiwe.hibernate.db.StudentDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.logback.shaded.guava.base.Optional;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    private StudentDAO studentDAO;
    private Logger logger = Logger.getLogger(StudentResource.class.getName());

    public StudentResource(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GET
    @UnitOfWork
    public List<Student> findByName(
            @QueryParam("name") String name) {
        return studentDAO.findByName(name);
    }

    @GET
    @UnitOfWork
    @Path("/all")
    public List<Student> findAll() {
        return studentDAO.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response findById(@PathParam("id") long id) {
        Optional<Student> studentOpt = studentDAO.findById(id);
        if(studentOpt.isPresent()){
            return Response.ok(studentOpt.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Something went wrong, student may not exist yet!").build();
        }
    }

    @POST
    @UnitOfWork
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Student student) {
        boolean ret = studentDAO.save(student);
        if (!ret) {
            logger.error("Something went wrong!");
            return Response.notModified("Something went wrong!").build();
        }
        return Response.ok("Student added!").build();
    }

    @PUT
    @UnitOfWork
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, Student student) {
        if (id != student.getId()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("The ID in the payload does not match that in the url!").build();
        }
        boolean ret = studentDAO.update(id, student);
        if (!ret) {
            logger.error("Something went wrong!");
            return Response.status(Response.Status.NOT_MODIFIED)
                    .entity("Something went wrong!").build();
        }
        return Response.ok("Student updated!").build();
    }

    @DELETE
    @UnitOfWork
    @Path("/{id}/remove")
    public Response remove(@PathParam("id") long id) {
        boolean ret = studentDAO.delete(id);
        if (!ret) {
            logger.error("Something went wrong, student may not exist yet!");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Something went wrong, student may not exist yet!").build();
        }
        return Response.ok("Student deleted!").build();
    }
}
