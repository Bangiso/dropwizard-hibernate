package com.aphiwe.hibernate.core;
import javax.persistence.*;


//@NamedNativeQueries({
//        @NamedNativeQuery(name = "com.aphiwe.hibernate.core.Student.findAll",
//                query = "select id, first_name as firstName, last_name lastName, Email as email,  field , gpa from students e"),
//        @NamedNativeQuery(name = "com.aphiwe.hibernate.core.Student.findByName",
//                query = "select id, first_name as firstName, last_name lastName, Email as email,  field , gpa from students e "
//                        + "where e.last_name like :name "
//                        + "or e.first_name like :name"),
//        @NamedNativeQuery(name = "com.aphiwe.hibernate.core.Student.findById",
//                query = "select id, first_name as firstName, last_name lastName, Email as email,  field , gpa from students e "
//                        + "where e.id = :id"),
//        @NamedNativeQuery(name = "com.aphiwe.hibernate.core.Student.insert",
//                query = "INSERT INTO students (id, firstName, lastName, email, field, gpa) " +
//                        "VALUES (:id, ':firstName', ':lastName', ':email', ':field', :gpa)")
//})
//@Entity
//@Table(name = "students")
//@NamedQueries({
//        @NamedQuery(name = "com.aphiwe.hibernate.core.Student.findAll",
//                query = "select e from Student e"),
//        @NamedQuery(name = "com.aphiwe.hibernate.core.Student.findByName",
//                query = "select e from Student e "
//                        + "where e.last_name like :name "
//                        + "or e.first_name like :name"),
//        @NamedQuery(name = "com.aphiwe.hibernate.core.Student.findById",
//                query = "select e Student e "
//                        + "where e.id = :id"),
////        @NamedQuery(name = "com.aphiwe.hibernate.core.Student.insert",
////                query = "INSERT INTO Student (id, first_name, last_name, email, field, gpa) " +
////                        "VALUES (:id, ':firstName', ':lastName', ':email', ':field', :gpa)")
//})
@Entity
@Table(name = "students")
@NamedQueries({
        @NamedQuery(name = "com.aphiwe.hibernate.core.Student.findAll",
                query = "select e from Student e"),
        @NamedQuery(name = "com.aphiwe.hibernate.core.Student.findByName",
                query = "select e from Student e "
                        + "where e.firstName like :name "
                        + "or e.lastName like :name"),
})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="field")
    private String field;
    @Column(name="gpa")
    private Double gpa;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Student(){}
    public Student(long id, String firstName, String lastName, String email, String field, Double gpa) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.field = field;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fname='" + firstName + '\'' +
                ", lname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", Field='" + field + '\'' +
                ", GPA=" + gpa +
                '}';
    }

}
