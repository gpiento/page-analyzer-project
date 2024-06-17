.DEFAULT_GOAL := build-run
.PHONY: build app

setup:
	./gradlew wrapper --gradle-version 8.7

clean:
	./gradlew clean

build:
	./gradlew build

install:
	./gradlew install

run-dist: install
	@./build/install/app/bin/app

run: build
	@java -jar ./build/libs/app-1.0-SNAPSHOT-all.jar

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

check-deps:
	./gradlew dependencyUpdates -Drevision=release

build-run: build install run-dist
