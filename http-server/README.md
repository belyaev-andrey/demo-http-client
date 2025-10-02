### AOT compilation and execution

AOT is enabled in POM file, so running 

```shell
mvn clean package
```
will generate AOT sources including Spring Data JDBC repositories.

Docker compose integration doesn't work with AOT-processed sources. So, we need to run the server with the `localdb` profile. Run the DB and alter the `application-localdb.yaml` accordingly. 

```shell
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.aot.enabled=true" -Dspring-boot.run.arguments="--spring.profiles.active=localdb"
```

 Note the `com.jetbrains.test.boot4.server.config.AotConfiguration` class. It is needed for AOT processing to avoid running DB and searching the JDBC driver