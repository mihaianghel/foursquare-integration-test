<h1>Foursquare Integration Service - proof of concept</h1>

<h2>Summary</h2>

<p>This application is a proof of concept for an integration with the Foursquare API\n
The frameworks and libraries that were used are:\n
1. Spring Boot - for fast bootstrapping of the web app\n
2. JUnit and Mockito - for unit testing\n
3. Gson - for deserialization\n
4. Vaadin - for UI\n
5. Apache HTTP Client\n
6. Apache Commons IO/Lang - utility libraries
</p>

<h2>Approach</h2>
<p>
The application has a service which calls an HTTP client. The client supports a maximum\n
of 50 concurrent connections and has a 5 second timeout set. The response from the Foursquare\n
API is cached to avoid unnecessary trips. The cache is flushed every minute (for the sake of\n
demo) in order to avoid memory issues. The response from Foursquare API is deserialized using\n
Gson library and the forwarded to the view component for rendering.
</p>

<h2>Build and start the app</h2>
<p>For bootstrapping the app you need Java 8 JDK and Maven 8\n
Using maven build the application with the following command:\n
\n
mvn clean install\n
\n
and then run the application by typing\n
\n
java -jar target/foursquare-integration-0.0.1-SNAPSHOT.jar
</p>

<h2>Using the application</h2>
<p>After starting the application you can access it in the browser:\n
http://localhost:8080/foursquare/explore
</p>

<h2>Notes</h2>
<p>Due to time constraints I did not write unit tests for all classes. I have tested the\n
service class, by injecting an instance of the deserializer instead of mocking it. In\n
this way the JSON deserializer was tested partially. It is not the best approach but\n
this was a tradeoff.
</p>

<h2>Improvements</h2>
<p>
1. Unit tests\n
2. Improved parsing using model objects for the response from the service\n
3. Caching should not be in memory\n
4. The UI doesn't respect the separation of concerns paradigm\n
5. Validations of the user input
</p>
