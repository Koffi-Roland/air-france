FROM dreg.klm.com:5000/base/java:openjdk-17

ENV APP_NAME="search-individual" \
    TNS_FILE_PATH_TEST_ACCEPTANCE="/app/REPIND/TNS/TEST_ACCEPTANCE" \
    TNS_FILE_PATH_PROD="/app/REPIND/TNS/PROD" \
    JAVA_OPTS="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true --add-opens java.base/java.lang=ALL-UNNAMED"

COPY ./target/*.jar ./app.jar

COPY ./target/classes/TNS/TEST_ACCEPTANCE/tnsnames.ora /app/REPIND/TNS/TEST_ACCEPTANCE/tnsnames.ora
COPY ./target/classes/TNS/PROD/tnsnames.ora /app/REPIND/TNS/PROD/tnsnames.ora

EXPOSE 8080

CMD ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES $KERBEROS_JVM_OPTS -jar app.jar"]
