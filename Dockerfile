# Étape 1 : Utiliser une image openjdk légère
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré dans le conteneur
COPY target/bankingApp-backend-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port sur lequel l'application s'exécute
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]