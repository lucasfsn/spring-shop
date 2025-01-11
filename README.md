## Run Locally

Clone the entire repository

```bash
  git clone https://gitlab.com/ug_jn/aplikacje-przemys-owe-2024/grupa-2-prowadz-cy-jakub-neumann/nowosielski-lukasz.git
```

Go to the project directory

```bash
  cd projekt
```

If you have made any changes to the database, adjust the configuration to suit your needs.

- The project uses **HSQLDB** as the database. You can find the configuration in the `application.properties`:
  - **spring.datasource.url**: jdbc:hsqldb:hsql://localhost/shop
- You can also modify the `jwt.secret` in the aforementioned `application.properties` file to your own secret key

Start HSQLDB server

```bash
  ./db/runHSQLDBServer.sh
```

Install dependencies

```bash
  mvn clean install
```

Start the backend:

```bash
  mvn spring-boot:run
```

Start HSQLDB client

```bash
  ./db/runHSQLDBClient.sh
```

Postman collection can be found in `src/test/java/com/example/demo/resources` directory.
