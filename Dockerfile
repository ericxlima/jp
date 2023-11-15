FROM openjdk:17-alpine
WORKDIR /app
COPY . /app
RUN javac JP.java
CMD [ "java", "JP" ]
