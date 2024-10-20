FROM quay.io/wildfly/wildfly:latest

WORKDIR /opt/jboss/wildfly

COPY target/kurs-2-laboration-2-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080

ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]