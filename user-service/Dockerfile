# syntax=docker/dockerfile:1

# 构建上下文必须是仓库根目录
# docker build -f user-service/Dockerfile -t user-service:latest .

FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build

# 多模块 reactor 需要所有模块 POM，只复制 user-service 依赖链的源码
COPY pom.xml ./
COPY common/pom.xml common/
COPY common/src common/src
COPY framework/pom.xml framework/
COPY framework/src framework/src
COPY user-service/pom.xml user-service/
COPY user-service/src user-service/src
COPY auth-service/pom.xml auth-service/
COPY tenant-service/pom.xml tenant-service/
COPY order-service/pom.xml order-service/
COPY gateway/pom.xml gateway/

RUN --mount=type=cache,target=/root/.m2 mvn -B -q -pl user-service -am package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -Duser.timezone=Asia/Shanghai"

RUN apk add --no-cache tzdata \
    && addgroup -S app \
    && adduser -S app -G app

COPY --from=build --chown=app:app /build/user-service/target/user-service-*.jar app.jar
USER app

EXPOSE 8081

HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget -qO- http://127.0.0.1:8081/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
