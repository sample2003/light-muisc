FROM ubuntu:22.04
ENV TZ=Asia/Shanghai
ENV JAVA_DIR=/usr/local
COPY ./jdk17.tar.gz $JAVA_DIR/
COPY ./music.jar /app.jar
RUN cd $JAVA_DIR \ && \
    tar -xf ./jdk17.tar.gz \
    && mv ./jdk-17.0.11 ./java17
ENV JAVA_HOME=$JAVA_DIR/java17
ENV PATH=$PATH:$JAVA_HOME/bin
ENTRYPOINT ["java", "-jar", "/app.jar"]