### AOT compilation and execution

AOT is enabled in POM file, so running 

```shell
mvn clean package
```
will generate AOT sources including Spring Data JDBC repositories.

Docker compose integration doesn't work with AOT-processed sources. So, we need to run the server with the `aot` profile. Run the DB and alter the `application-aot.yaml` accordingly. 

```shell
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.aot.enabled=true" -Dspring-boot.run.arguments="--spring.profiles.active=aot"
```