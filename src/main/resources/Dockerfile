FROM openjdk:17
MAINTAINER com.world2meet

COPY ./naves-1.0.0.jar /opt/app/naves-1.0.0.jar 

EXPOSE 8185

WORKDIR /opt/app
ENTRYPOINT exec java -jar ./naves-1.0.0.jar 