FROM dreg.klm.com:5000/base/java:openjdk-17

ENV APP_NAME="manage-doctor-attributes" \
    JAVA_OPTS="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true --add-opens java.base/java.lang=ALL-UNNAMED"

COPY ./target/*.jar ./app.jar

EXPOSE 8080

CMD ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES $KERBEROS_JVM_OPTS -jar app.jar"]
