FROM openjdk:8
EXPOSE 8081
ADD target/docker-jenkins-demo.jar docker-jenkins-demo.jar
ENTRYPOINT ["java","-jar","docker-jenkins-demo,jar"]