# Regarding to issue with JDK9 we used JRE9
# https://github.com/docker-library/openjdk/issues/145#issuecomment-388108030
FROM openjdk:9-jre
RUN apt-get update
RUN apt-get install sudo
COPY ./target/web-application.jar /
ENTRYPOINT [ "sh", "-c", "java -XX:+UnlockExperimentalVMOptions -XX:+UseJVMCICompiler -XX:+EnableJVMCI -Djvmci.Compiler=graal -jar /web-application.jar" ]
VOLUME /app