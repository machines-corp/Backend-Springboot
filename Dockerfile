# ---------- build ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiamos el wrapper y lo dejamos ejecutable (y sin CRLF)
COPY .mvn/ .mvn/
COPY mvnw .
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Cache de dependencias (más rápido en builds posteriores)
COPY pom.xml .
RUN ./mvnw -B -DskipTests dependency:go-offline

# Copiamos el código y compilamos
COPY src/ src/
RUN ./mvnw -B -DskipTests package

# ---------- runtime ----------
# FROM eclipse-temurin:21-jre
# WORKDIR /app
# COPY --from=build /app/target/*.jar app.jar
# EXPOSE 8082
# ENTRYPOINT ["java","-jar","app.jar"]

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src/ src/

RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

CMD ["./mvnw", "spring-boot:run"]