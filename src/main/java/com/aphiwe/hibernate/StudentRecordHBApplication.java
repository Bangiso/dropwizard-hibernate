package com.aphiwe.hibernate;
import com.aphiwe.hibernate.api.Student;
import com.aphiwe.hibernate.db.StudentsDAO;
import com.aphiwe.hibernate.resources.StudentsResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class StudentRecordHBApplication extends Application<StudentRecordHBConfiguration> {

    public static void main(final String[] args) throws Exception {
        new StudentRecordHBApplication().run(args);
    }

    @Override
    public String getName() {
        return "StudentRecordHB";
    }

    @Override
    public void initialize(final Bootstrap<StudentRecordHBConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    private final HibernateBundle<StudentRecordHBConfiguration> hibernateBundle
            = new HibernateBundle<StudentRecordHBConfiguration>(
            Student.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(
                StudentRecordHBConfiguration configuration
        ) {
            return configuration.getDataSourceFactory();
        }
    };


    @Override
    public void run(final StudentRecordHBConfiguration configuration,
                    final Environment environment) {
        final StudentsDAO studentDAO
                = new StudentsDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new StudentsResource(studentDAO));
    }

}
