FROM openjdk:11-jre-slim

ADD ./dd-java-agent.jar /etc/dd-java-agent.jar
ADD ./app.jar app.jar

# javaagent 파라미터는 무조건 앞에
ENTRYPOINT ["java", "-javaagent:/etc/dd-java-agent.jar", "-Ddd.logs.injection=true", "-Ddd.profiling.enabled=true", "-Ddd.trace.sample.rate=1","-jar","app.jar"]
