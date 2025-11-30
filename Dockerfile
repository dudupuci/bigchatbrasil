# Build stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copiar pom pai primeiro
COPY pom.xml .

# Copiar arquivos pom.xml dos módulos
COPY domain/pom.xml domain/
COPY application/pom.xml application/
COPY infrastructure/pom.xml infrastructure/

# Baixar dependências
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY domain/src domain/src
COPY application/src application/src
COPY infrastructure/src infrastructure/src

# Build do projeto
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Criar usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar JAR do build stage
COPY --from=build /app/infrastructure/target/*.jar app.jar

# Expor porta
EXPOSE 8080

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/actuator/health || exit 1

# Executar aplicação com profile docker
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]


