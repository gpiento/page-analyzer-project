FROM gradle:8.7.0-jdk21

WORKDIR /app

COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradlew .

COPY src src

RUN ./gradlew --no-daemon build

ENV JAVA_OPTS "-Xmx512M -Xms512M"

EXPOSE 7070

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar"]
