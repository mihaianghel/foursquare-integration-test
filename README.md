Foursquare Integration Service - proof of concept
=====================

##Summary

This application is a POC for an integration with the Foursquare API. It allows searching for the most popular places
around a location.

####The frameworks and libraries that were used are:
* **Spring Boot** - for fast bootstrapping of the web app
* **JUnit, Mockito** - for unit testing
* **Infinispan cache** - for caching
* **Gson** - for deserialization
* **Vaadin**- for UI
* **Apache HTTP Client** - for http calls
* **Apache Commons IO/Lang** - utility libraries
* **Lombok** - for reducing boilerplate code


##Approach
The application has a GUI component which calls a service with the data selected by the user.The service calls a DAO 
layer, which in the current implementation is coupled to a replicated Infinispan cache. This is populated by regularly
calling the Foursquare API through an HTTP client which supports 50 concurrent connections and has a timeout set for 5
seconds. The response from the API is de-serialized using Gson library, stored in the cache and sent to the view component
for rendering.


##Build and start the app
For bootstrapping the app you need Java 8 JDK and Maven 3.
Using Maven, build the application with the following command:
```
mvn clean install
```
and then run the application by typing
```
java -jar target/foursquare-integration-0.0.1-SNAPSHOT.jar
```
Or checking out the code in your IDE and run it selecting
```
RunAs - Spring Boot App
```
##Using the application
After starting the application you can access it in the browser:
```
http://localhost:8080/foursquare/explore
```

##Note
There is also a local Infinispan cache implemented which is not wired at runtime automatically in order to avoid 
bootstrapping errors due to duplicate cache manager instances registered under 'org.infinispan' JMX domain