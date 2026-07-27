# Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd.
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests dependency:go-offline
COPY src src
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests package

FROM eclipse-temurin:21-jre-alpine
LABEL org.opencontainers.image.vendor="上海如静知华信息科技有限公司"
WORKDIR /app
COPY --from=build /workspace/target/zhuatech-wms.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
