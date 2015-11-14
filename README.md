Foursquare Integration Service - proof of concept
=====================

##Summary

This application is a proof of concept for an integration with the Foursquare API
The frameworks and libraries that were used are:
* **Spring Boot** - for fast bootstrapping of the web app
* **JUnit and Mockito** - for unit testing
* **Gson** - for deserialization
* **Vaadin**- for UI
* **Apache HTTP Client** - for http calls
* **Apache Commons IO/Lang** - utility libraries


##Approach
The application has a service which calls an HTTP client. The client supports a maximum
of 50 concurrent connections and has a 5 second timeout set. The response from the Foursquare
API is cached to avoid unnecessary trips. The cache is flushed every minute (for the sake of
demo) in order to avoid memory issues. The response from Foursquare API is deserialized using
Gson library and the forwarded to the view component for rendering.

##Build and start the app
For bootstrapping the app you need Java 8 JDK and Maven 8
Using maven build the application with the following command:
```
mvn clean install
```
and then run the application by typing
```
java -jar target/foursquare-integration-0.0.1-SNAPSHOT.jar
```

##Using the application
After starting the application you can access it in the browser:
```
http://localhost:8080/foursquare/explore
```


##Notes
Due to time constraints I did not write unit tests for all classes. I have tested the
service class, by injecting an instance of the deserializer instead of mocking it. In
this way the JSON deserializer was tested partially. It is not the best approach but
this was a tradeoff.


##Improvements

*Unit tests
*Improved parsing using model objects for the response from the service
*Caching should not be in memory
*The UI doesn't respect the separation of concerns paradigm
*Validations of the user input

