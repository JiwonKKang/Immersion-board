FROM arm64v8/eclipse-temurin:17-jdk as build
WORKDIR /workspace/app

COPY . .

RUN ./gradlew build -x test
RUN mkdir build/extracted && (java -Djarmode=layertools -jar build/libs/sbb-1.0.0.jar extract --destination build/extracted)

FROM arm64v8/eclipse-temurin:17-jdk as build
VOLUME /tmp
ARG EXTRACTED=/workspace/app/build/extracted
COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]