# Usar imagem do Ubuntu
FROM ubuntu:latest

# Atualizar repositórios e instalar o Java e o Maven
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Configurar variáveis de ambiente do Java e do Maven
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV MAVEN_HOME=/usr/share/maven

# Copiar os arquivos do projeto para o contêiner
COPY . /app

# Definir o diretório de trabalho como /app
WORKDIR /app

# Executar o comando 'mvn package' para construir o projeto
RUN mvn clean package

# Comando para iniciar o projeto Spring Boot
CMD ["java", "-jar", "target/birt-0.0.1-SNAPSHOT.jar"]
