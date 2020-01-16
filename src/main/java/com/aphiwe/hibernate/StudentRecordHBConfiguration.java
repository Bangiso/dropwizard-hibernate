package com.aphiwe.hibernate;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.*;

public class StudentRecordHBConfiguration extends Configuration {
    @NotNull
    @Valid
    private DataSourceFactory dataSourceFactory
            = new DataSourceFactory();
    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }


}
