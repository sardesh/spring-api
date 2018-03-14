# spring-api
This project demonstrate spring rest api with basic authentication.

===================================

Requirements
------------
* [Java Platform (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Apache Maven 3.x](https://maven.apache.org/download.cgi)
**Databases
* [Database : Postgres (Application configuration is need see below)] (https://www.postgresql.org/download/)
OR
* [Database : MySQL (Application configuration is needed see below ] (https://www.mysql.com/downloads/)

* POSTMAN Client to test application (Chrome extention)

Quick start
-----------
[/contacts-api/src/main/resources/application.properties needs to be configured for database connection (preconfigured for postgres database just update the url)]
1.  go to root directory of application and run `mvn clean install` [Runs all tests and builds a jar file]
2. `java -jar target\contacts-api-0.0.1-SNAPSHOT.jar`[The target folder is in the application root path]
3. Hit GET Request to URL [http://localhost:8080/contacts] with default basic authentication as [username: "joe" & password: "password"] 
	Refer to [Basic authentication using postman](https://www.getpostman.com/docs/postman/sending_api_requests/authorization)

Other Functions:

1. All request formats are as per [http://docs.cwcontacts.apiary.io/#](http://docs.cwcontacts.apiary.io/#)
2. Url follows format as 
(POST, DELETE)http://localhost:8080/contacts/contact_id : update,delete contact with contact_id
(POST) http://localhost:8080/contacts/contact_id/entries : update phone number to contact_id
(GET) http://localhost:8080/contacts/contact_id : get all contacts for the logged in user
(POST) http://localhost:8080/contacts : create a new contact
(POST) http://localhost:8080/users/register : create a new user
(POST) http://localhost:8080/users : get token for authentication (although not needed as we are using basic authentication) ]


OR 
-----

[/contacts-api/src/main/resources/application.properties needs to be configured for database connection (preconfigured for postgres database just update the url)]
1. `mvn spring-boot:run` [Runs directly the application bypassing tests]

 
 How to configure .properties file for databases
------------------------------------------------

1. For postgres update the 'spring.datasource.url=jdbc:postgresql://[host]:[port]/[schema]' & check for dependency in pom.xml (comment other database dependency)
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
		
		

2. For Mysql update/uncomment the 'spring.datasource.url=jdbc:mysql://[host]:[port]/[schema]'& check for dependency in pom.xml (comment other database dependency)
		<dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        
 
Note: update the 'spring.datasource.username' & 'spring.datasource.password' field accordingly
       
3.  For H2 database comment all database properties in application.properties file & check for dependency in pom.xml(already configured for test environment just comment the scope field to get it running for all environment) (comment other database dependency)       



 How to configure .properties file for logs
-------------------------------------------
 1. change the file path in  'logging.file=<path>/<filename>'
 2. you can change logging leves and other configuration in application.properties file (check there for comments)
