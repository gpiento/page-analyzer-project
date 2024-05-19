FROM openjdk:21-slim as builder
WORKDIR /app
COPY . /
RUN ./gradlew --no-daemon shadowJar

FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /build/libs/app-1.0-SNAPSHOT-all.jar /
ENV JAVA_OPTS "-Xmx512M -Xms512M"
EXPOSE 7070
CMD ["java", "-jar", "app-1.0-SNAPSHOT-all.jar"]
