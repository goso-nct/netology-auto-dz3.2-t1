AQA-19    
Домашнее задание к занятию «3.2. SQL»  

Задача №1 - Скоро deadline  

**Prerequisites**:  

- java 11
- git
- docker-compose  

**Установка и запуск**  

1. клонировать репозитроий

2. перейти в рабочую папку

3. открыть терминал, выполнить docker-compose up

4. открыть терминал, выполнить java -jar ./artifacts/app-deadline.jar

5. открыть терминал, выполнить 

   для Windows: gradlew.bat test -Dselenide.headless=true

   для Linux: ./gradlew test -Dselenide.headless=true

**Перезапуск**  

- Закрыть терминалы пп. 4 и 5, выполнить шаги 4, 5.

**Завершение**
1. Закрыть терминалы пп. 4 и 5
2. В терминале п.3 выполнить docker-compose down и закрыть его