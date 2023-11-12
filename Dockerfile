FROM openjdk:17-jdk-slim as builder

COPY . .

RUN ./gradlew :app:jsBrowserProductionWebpack

FROM nginx:alpine

COPY --from=builder /app/build/dist/js/productionExecutable /usr/share/nginx/html