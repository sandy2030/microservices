#start with base image containing java runtime
FROM openjdk:17-jdk-slim
#information about who maintains the image
MAINTAINER sandip.new2@gmail.com
# add tha application jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar
#execute the application
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]