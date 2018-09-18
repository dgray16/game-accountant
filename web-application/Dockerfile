FROM openjdk:10-jre
RUN apt-get update
RUN apt-get install sudo
COPY ./target/web-application.jar /
ENTRYPOINT [ "sh", "-c", "java -XX:+UnlockExperimentalVMOptions -XX:+UseJVMCICompiler -XX:+EnableJVMCI -Djvmci.Compiler=graal -jar /web-application.jar" ]
VOLUME /app