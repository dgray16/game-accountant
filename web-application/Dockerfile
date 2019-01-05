# Take into account that in current version of this application Heroku is not deploying with Docker
# This file is just a template for future improvements
FROM openjdk:11-jre
RUN apt-get update
RUN apt-get install sudo
COPY ./target/web-application.jar /
ENTRYPOINT [ "sh", "-c", "java -XX:+UnlockExperimentalVMOptions -XX:+UseJVMCICompiler -XX:+EnableJVMCI -Djvmci.Compiler=graal -jar /web-application.jar" ]
VOLUME /app