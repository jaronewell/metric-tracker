FROM openjdk:11
WORKDIR /app
COPY target/metric-tracker-0.0.1-SNAPSHOT.jar .
EXPOSE 8082
CMD java -jar metric-tracker-0.0.1-SNAPSHOT.jar