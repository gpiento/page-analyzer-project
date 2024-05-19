FROM eclipse-temurin:21-jdk
WORKDIR /app
RUN ./gradlew --no-daemon shadowJar
CMD ls -l
COPY /app/build/libs/app-1.0-SNAPSHOT-all.jar /
ENV JAVA_OPTS "-Xmx512M -Xms512M"
EXPOSE 7070
CMD ["java", "-jar", "app-1.0-SNAPSHOT-all.jar"]
