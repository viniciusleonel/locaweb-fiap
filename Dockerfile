# Use a imagem oficial do Gradle com JDK 17 como base

FROM gradle:7.6-jdk17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de configuração do Gradle e o código-fonte para o contêiner
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
COPY src ./src

# Executa o build da aplicação
RUN ./gradlew build

# Usa uma imagem mais leve para o runtime
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado pelo build para o contêiner
COPY --from=build /app/build/libs/*.jar app.jar

# Expõe a porta que a aplicação irá rodar
EXPOSE 8080

# Define o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
