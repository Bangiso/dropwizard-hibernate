package com.aphiwe.hibernate;
import com.aphiwe.hibernate.core.Student;
import com.aphiwe.hibernate.db.StudentDAO;
import com.aphiwe.hibernate.resources.StudentResource;
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
        final StudentDAO studentDAO
                = new StudentDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new StudentResource(studentDAO));
    }

}
