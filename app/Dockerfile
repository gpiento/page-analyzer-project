FROM gradle:8.7.0-jdk21

WORKDIR /app

COPY / .
#COPY app/gradle gradle
#COPY app/build.gradle .
#COPY app/settings.gradle .
#COPY app/gradlew .
#COPY app/config/checkstyle/checkstyle.xml config/checkstyle/checkstyle.xml

#COPY app/src src

RUN ./gradlew --no-daemon shadowJar

ENV JAVA_OPTS "-Xmx512M -Xms512M"

EXPOSE 7070

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar"]
