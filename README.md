# SWA-Project

## 📌 - License Management

This Project is a simple web based license management system, to manage different users associated with companies and of their Licenses and service contracts.
The Technologies used in this project are:

- MySQL RDBMS
- Java 11
- Quarkus Hibernate ORM
- Quarkus JAX-RS
- ReactJS

### ⚙️ - How to run the application? (Developer-Mode)

To run the Application in the Developer Mode (live coding), run:

```
foo@bar:~$ ./gradlew quarkusDev
```

### 📦 - How to package and run the Application?

To package the Application, run:

```
foo@bar:~$ ./gradlew quarkusBuild
```

This will produce a `.jar` file in the `/build` directory.
Be aware this is not a über-jar and therefore the dependencies will be copied in the `build/lib/` directory.
To run the application, run:

```
foo@bar:~$ java -jar build/[name_of_file].jar
```

To create a `uber-jar`, run:

```
foo@bar:~$ ./gradlew quarkusBuild --uber-jar
```

### 🛠️ - How to create an executable File?

To create a Native executable File, run:

```
foo@bar:~$ ./gradlew build -Dquarkus.package.type=native
```
