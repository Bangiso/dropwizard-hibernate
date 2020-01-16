package com.aphiwe.hibernate.core;
import javax.persistence.*;

@Entity
@Table(name = "students")
@NamedQueries({
        @NamedQuery(name = "com.aphiwe.hibernate.core.Student.findAll",
                query = "select e from Student e"),
        @NamedQuery(name = "com.aphiwe.hibernate.core.Student.findByName",
                query = "select e from Student e "
                        + "where e.fname like :name "
                        + "or e.lname like :name")
})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="fName")
    private String fname;
    @Column(name="lName")
    private String lname;
    @Column(name="Email")
    private String email;
    @Column(name="Field")
    private String Field;
    @Column(name="GPA")
    private Double GPA;

    public Student(){}
    public Student(long id, String fname, String lname, String email, String field, Double GPA) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        Field = field;
        this.GPA = GPA;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public Double getGPA() {
        return GPA;
    }

    public void setGPA(Double GPA) {
        this.GPA = GPA;
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", Field='" + Field + '\'' +
                ", GPA=" + GPA +
                '}';
    }

}
