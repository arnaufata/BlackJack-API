# Utilitza una imatge oficial de Java com a base
FROM openjdk:17-jdk-slim

# Estableix el directori de treball dins del contenidor
WORKDIR /app

# Copia el fitxer JAR generat al directori de treball
COPY target/blackjack-api-0.0.1-SNAPSHOT.jar app.jar

# Defineix el comandament per executar la teva aplicació
ENTRYPOINT ["java", "-jar", "app.jar"]