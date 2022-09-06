package com.aphiwe.hibernate.resources;

import com.aphiwe.hibernate.api.Student;
import com.aphiwe.hibernate.db.StudentsDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.logback.shaded.guava.base.Optional;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import javax.ws.rs.client.Entity;

import java.util.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class StudentsResourceTest {

    private static final StudentsDAO DAO = mock(StudentsDAO.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new StudentsResource(DAO))
            .build();
    private Student student;
    private List<Student> students;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        student = new Student(1L,"Aphiwe", "Sam", "a.sam@gmail.com","Science",76.5);
        students = Arrays.asList(student,new Student(3L,"Dan", "Sam", "dan@gmail.com","Engineering",80.5));
    }

    @AfterEach
    void tearDown() {
        reset(DAO);
    }

    @Test
    void fetchStudentByIdSuccess() {
        when(DAO.findById(1)).thenReturn(Optional.of(student));

        Student found = EXT.target("/students/1").request().get(Student.class);

        assertThat(found.getId()).isEqualTo(student.getId());
        assertThat(found.getFirstName()).isEqualTo(student.getFirstName());
        assertThat(found.getGpa()).isEqualTo(student.getGpa());
        verify(DAO, times(1)).findById(1L);
    }

    @Test
    void fetchStudentByIdNotFound() {
        when(DAO.findById(2L)).thenReturn(Optional.absent());
        final Response response = EXT.target("/students/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO, times(1)).findById(2L);
    }

    @Test
    void fetchStudentsSuccess() {
        when(DAO.findAll()).thenReturn(students);
        List<Student> found = EXT.target("/students/all").request().get(new GenericType<List<Student>>(){});

        assertThat(found.get(0).getGpa()).isEqualTo(students.get(0).getGpa());
        assertThat(found.get(0).getId()).isEqualTo(students.get(0).getId());
        assertThat(found.get(0).getFirstName()).isEqualTo(students.get(0).getFirstName());

        assertThat(found.get(1).getGpa()).isEqualTo(students.get(1).getGpa());
        assertThat(found.get(1).getId()).isEqualTo(students.get(1).getId());
        assertThat(found.get(1).getFirstName()).isEqualTo(students.get(1).getFirstName());
        verify(DAO, times(1)).findAll();
    }

    @Test
    void fetchStudentsNotFound() {
        when(DAO.findAll()).thenReturn(new ArrayList<Student>());
        List<Student> found = EXT.target("/students/all").request().get(new GenericType<List<Student>>(){});
        assertThat(found.isEmpty()).isTrue();
        verify(DAO, times(1)).findAll();
    }

    @Test
    void updateStudentSuccess() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(student);
        Entity payload = Entity.entity(json, MediaType.APPLICATION_JSON);

        when(DAO.update(any(long.class), any(Student.class))).thenReturn(true);

        Response found = EXT.target("/students/1/update").request().put(payload);
        assertThat(found.getStatus()).isEqualTo(200);
        verify(DAO, times(1)).update(any(long.class), any(Student.class));
    }

    @Test
    void updateStudentConflict() throws JsonProcessingException {

        when(DAO.update(any(long.class), any(Student.class))).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(student);

        Entity payload = Entity.entity(json, MediaType.APPLICATION_JSON_TYPE);
        Response found = EXT.target("/students/2/update").request().put(payload);
        assertThat(found.getStatus()).isEqualTo(409);
        verify(DAO, never()).update(any(long.class), any(Student.class));
    }

    @Test
    void updateStudentNotModified() throws JsonProcessingException {

        when(DAO.update(any(long.class), any(Student.class))).thenReturn(false);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(student);

        Entity payload = Entity.entity(json, MediaType.APPLICATION_JSON_TYPE);
        Response found = EXT.target("/students/1/update").request().put(payload);
        assertThat(found.getStatus()).isEqualTo(304);
        verify(DAO, times(1)).update(any(long.class), any(Student.class));
    }

    @Test
    void saveStudentSuccess() throws JsonProcessingException {
        when(DAO.save(any(Student.class))).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(student);

        Entity payload = Entity.entity(json, MediaType.APPLICATION_JSON_TYPE);
        Response found = EXT.target("/students/add").request().post(payload);
        assertThat(found.getStatus()).isEqualTo(200);
        verify(DAO, times(1)).save(any(Student.class));
    }
    @Test
    void saveStudentNotModified() throws JsonProcessingException {
        when(DAO.save(any(Student.class))).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(student);

        Entity payload = Entity.entity(json, MediaType.APPLICATION_JSON_TYPE);
        Response found = EXT.target("/students/add").request().post(payload);
        assertThat(found.getStatus()).isEqualTo(304);
        verify(DAO,times(1)).save(any(Student.class));
    }

    @Test
    void removeStudentSuccess(){
        when(DAO.delete(1L)).thenReturn(true);

        Response found = EXT.target("/students/1/remove").request().delete();
        assertThat(found.getStatus()).isEqualTo(200);
        verify(DAO,times(1)).delete(1L);
    }
    @Test
    void removeStudentNotFound() {
        when(DAO.delete(1L)).thenReturn(false);

        Response found = EXT.target("/students/1/remove").request().delete();
        assertThat(found.getStatus()).isEqualTo(404);
        verify(DAO,times(1)).delete(1L);
    }
}
