FROM openjdk:17-alpine
WORKDIR /app
COPY src/main/java/com/ericxlima/ProcessMonitor /app
RUN javac Main.java
CMD [ "java", "Main" ]
