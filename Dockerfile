FROM openjdk:21-slim
RUN ls -l
WORKDIR /app
RUN ls -l
RUN ./gradlew --no-daemon shadowJar
CMD ls -l
COPY /app/build/libs/app-1.0-SNAPSHOT-all.jar /
ENV JAVA_OPTS "-Xmx512M -Xms512M"
EXPOSE 7070
CMD ["java", "-jar", "app-1.0-SNAPSHOT-all.jar"]
