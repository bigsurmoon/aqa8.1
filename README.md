#### Запуск контейнера:
docker-compose up -d
#### Запуск SUT:
java -jar app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app_db -P:jdbc.user=kaas -P:jdbc.password=1911
