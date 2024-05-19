FROM gradle:8.7.0-jdk21

ENV JAVA_OPTS=""

COPY build/libs/app-1.0-SNAPSHOT-all.jar app-1.0-SNAPSHOT-all.jar

EXPOSE 7070

ENTRYPOINT ["java","-jar","/app-1.0-SNAPSHOT-all.jar"]
