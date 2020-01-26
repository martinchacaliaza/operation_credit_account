FROM openjdk:8
VOLUME /tmp
EXPOSE 8006
ADD ./target/microservicios.mongodb.banco.operation_credit_account-0.0.1-SNAPSHOT.jar opercuentascredito.jar
ENTRYPOINT ["java","-jar","/opercuentascredito.jar"]