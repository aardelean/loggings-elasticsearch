FROM openjdk:8u102-jre
ENV SERVICE_NAME=loggings-elasticsearch
ENV PORT=9402
ENV ES_HOST=localhost
ENV JAVA_OPTS='-Xms256m -Xmx512m -XX:MaxMetaspaceSize=128m -XX:+PrintCommandLineFlags'
ADD build/libs/loggings-elasticsearch-1.0-SNAPSHOT.jar app.jar
ADD entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
