# How to Run the Backend

### Requiremnts

- JDK 17+
- Any IDE with Java EE support
- MySQL installation
- Internet connection (first time build only)

### Steps

1. clone the project
2. In `/codex-website-backend/src/main/resources/application.properties` ,change the credentials to your MySQL details
3. Run the Schema generator.sql file to generate the database
4. Run the `CodexApplication.java` at the root of `com.woxsen.codex` package.
5. The application will run on localhost:8080 by default and all APIs can be accessed from there.

## API Description

### Refrences -

User -

```
id - String (UUID),
username - String,
password - String
```

Member -

```
id - String (UUID),
name - String,
linkedInURL - String,
githubURL - String,
memberRole - String [Can only be EXECUTIVE, PRESIDENT, VICEPRESIDENT, CORE, MENTOR]
```
