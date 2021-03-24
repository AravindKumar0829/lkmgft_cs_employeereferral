FROM tomcat:8.5
# COPY path-to-your-application-war path-to-webapps-in-docker-tomcat
COPY /target/1003_CS_EmployeeReferral-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/
#COPY APIWeb-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/
RUN chmod +x /usr/local/tomcat/webapps/1003_CS_EmployeeReferral-0.0.1-SNAPSHOT.war
#RUN chmod +x /usr/local/tomcat/webapps/APIWeb-0.0.1-SNAPSHOT.war
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
EXPOSE 8080
