<h1>Foursquare Integration Service - proof of concept</h1>

<h2>Summary</h2>

<p>This application is a proof of concept for an integration with the Foursquare API
The frameworks and libraries that were used are:
1. Spring Boot - for fast bootstrapping of the web app
2. JUnit and Mockito - for unit testing
3. Gson - for deserialization
4. Vaadin - for UI
5. Apache HTTP Client
6. Apache Commons IO/Lang - utility libraries
</p>

<h2>Approach</h2>
<p>
The application has a service which calls an HTTP client. The client supports a maximum
of 50 concurrent connections and has a 5 second timeout set. The response from the Foursquare
API is cached to avoid unnecessary trips. The cache is flushed every minute (for the sake of 
demo) in order to avoid memory issues. The response from Foursquare API is deserialized using
Gson library and the forwarded to the view component for rendering.
</p>

<h2>Build and start the app</h2>
<p>For bootstrapping the app you need Java 8 JDK and Maven 8
Using maven build the application with the following command:
```
mvn clean install
```
and then run the application by typing
```
java -jar target/foursquare-integration-0.0.1-SNAPSHOT.jar
```
</p>

<h2>Using the application</h2>
<p>After starting the application you can access the application in the browser:
```http://localhost:8080/foursquare/explore``
</p>

<h2>Notes</h2>
<p>Due to time constraints I did not write unit tests for all classes. I have tested the
service class, by injecting an instance of the deserializer instead of mocking it. In
this way the JSON deserializer was tested partially. It is not the best approach but
this was a tradeoff.</p>

<h2>Improvements</h2>
<p>
1. Unit tests
2. Improved parsing using model objects for the response from the service
3. Caching should not be in memory
4. The UI doesn't respect the separation of concerns paradigm
5. Validations of the user input
</p>
