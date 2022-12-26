FROM fabric8/java-ubi-openjdk11-jdk
COPY PixelImageService.jar /usr/app/PixelImageService.jar
WORKDIR /usr/app
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "PixelImageService.jar"]