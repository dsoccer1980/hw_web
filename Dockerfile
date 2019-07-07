FROM maven:3.6.1-jdk-11

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
COPY ./package.json $PROJECT_DIR
COPY ./webpack.config.js $PROJECT_DIR
RUN mvn dependency:resolve

COPY ./node_modules/ $PROJECT_DIR/node_modules
ADD ./src/ $PROJECT_DIR/src
RUN mvn -B clean install -DskipTests

FROM openjdk:11

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target $PROJECT_DIR/

EXPOSE 8080

CMD ["java", "-jar", "/opt/project/hw_docker.jar"]