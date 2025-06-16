FROM tomcat:9.0-jdk17

# Remove default webapps and deploy our WAR
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/app.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
