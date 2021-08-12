# StudentRecordHB

I have followed a dropwizard-hibernate tutorial which can be found here https://dzone.com/articles/getting-started-with-dropwizard-connecting-to-a-da for detailed explanation or https://www.dropwizard.io/en/stable/manual/hibernate.html. There are some few changes I made.

Database:
---

A table can be created in StudentDB using the statement:

```
CREATE TABLE students(
	id int,
	fName varchar(255),
	lName varchar(255),
	Email varchar(255),
	Field varchar(255),
	GPA double);
```


How to start the StudentRecordHB application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/StudentRecordHB-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`


