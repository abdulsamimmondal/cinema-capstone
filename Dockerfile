# Use Amazon-mirrored Tomcat image to avoid Docker Hub rate limits
FROM public.ecr.aws/docker/library/tomcat:9.0-jdk17

# Remove default apps and deploy your WAR
RUN rm -rf /usr/local/tomcat/webapps/*
COPY cinema.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
