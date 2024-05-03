FROM xldevops/jdk17-alpine

WORKDIR /app

COPY target/Sb_docker_app.jar /app

EXPOSE 8080

CMD ["java", "-jar", "Sb_docker_app.jar"]