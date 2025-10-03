# ---- build ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# wrapper + permisos (evita CRLF)
COPY .mvn/ .mvn/
COPY mvnw .
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# cache deps
COPY pom.xml .
RUN ./mvnw -B -DskipTests dependency:go-offline

# código y build
COPY src/ src/
RUN ./mvnw -B -DskipTests package

# ---- runtime ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]