FROM openjdk:11-jdk 
WORKDIR /license
WORKDIR /apps
ADD Enrollment-Server.jar /apps/app.jar
ENV TZ=Asia/Taipei
RUN echo $TZ > /etc/timezone
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/apps/app.jar"]
