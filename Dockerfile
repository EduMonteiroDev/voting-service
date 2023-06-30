FROM maven:3.9.2 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /app/src/
RUN mvn package -DskipTests

FROM amazoncorretto:17

WORKDIR /app

# Copia o arquivo JAR da sua aplicação para o diretório de trabalho
COPY --from=builder /app/target/votingservice-0.0.1-SNAPSHOT.jar .

# Define o comando para executar a aplicação
CMD ["java", "-jar", "/app/votingservice-0.0.1-SNAPSHOT.jar"]