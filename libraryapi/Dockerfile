# build
FROM maven:3.8.8-amazoncorretto-21-al2023 as build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

# run
FROM amazoncorretto:21.0.9
WORKDIR /app

COPY --from=build ./build/target/*.jar ./libraryapi.jar

EXPOSE 8080
EXPOSE 9090

ENV DATASOURCE_URL='jdbc:postgresql://librarydb-prod:5432/library'
ENV DATASOURCE_USERNAME='postgresprod'
ENV DATASOURCE_PASSWORD='postgresprod'
ENV GOOGLE_ID='client-id'
ENV GOOGLE_SECRET='client-id'

ENV SPRING_PROFILES_ACTIVE='production'

ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar libraryapi.jar