# ---------- build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiamos el wrapper y preparamos
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Cache de dependencias
RUN ./mvnw -B -DskipTests dependency:go-offline

# Copiamos el código fuente y construimos el jar
COPY src/ src/
RUN ./mvnw -B -DskipTests clean package

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos solo el jar final del build
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto del backend
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
