FROM maven:3-jdk-8
RUN git clone https://github.com/ainilili/ratel.git
WORKDIR /ratel
RUN mvn install package

FROM openjdk:8-slim
COPY --from=0  /ratel/landlords-server/target/*.jar /opt
WORKDIR /opt
ENTRYPOINT ["bash", "-c"]
CMD ["java -jar *.jar -p 1024"]
