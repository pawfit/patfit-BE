FROM openjdk:17-jdk

# 로컬에서 빌드된 JAR 파일 복사
COPY build/libs/*SNAPSHOT.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
