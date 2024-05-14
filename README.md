# DSR - Fachdienst Backend

> [!NOTE]
> This software is a proof of concept and is not intended for production use. It will not be maintained or receive updates. Concepts from this project will be used in gematik specifications to standardize Zero Trust in Telematics Infrastructure. Developers are encouraged to use the implementation ideas in their own software.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
mvn compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
mvn package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Health Check - localhost
http://localhost:9000/management/health         <br>
http://localhost:9000/management/health/live    <br>
http://localhost:9000/management/health/ready   <br>

## Metrics - localhost
http://localhost:9000/management/metrics

## License

Apache License Version 2.0

See [LICENSE](./LICENSE).
