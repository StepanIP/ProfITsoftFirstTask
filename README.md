# Запуск програми

Програма запускається через клас Main і метод з аналогічною назвою. При запуску запускається Scanner(або зщитування з консолі) куда потрібно ввести поле за яким буде генеруватися статистика. Поля за якимим можна згенерувати статистику : genres, actors.

# Опис сутностей
## Основна сутність Фільми-режисер

### Унікальні:
title: Рядок, що містить назву фільму. Це інформація про заголовок або назву конкретного фільму.

director: Рядок, що містить ім'я режисера фільму. Це інформація про особу, яка в реальності керувала процесом створення фільму.

### Не унікальні (можна проводити статистику):
year: Ціле число, яке вказує на рік випуску фільму. Відображає рік, коли був випущений фільм. 

genres: Масив рядків, який містить список жанрів фільму. Це можуть бути різні жанри, до яких відноситься фільм, такі як екшн, пригоди, наукова фантастика тощо.

actors: Масив рядків, який містить список акторів, які зіграли у фільмі. Це інформація про осіб, які виконували ролі у фільмі.


# Технології

Java 17.08

maven

# Бібліотеки
Jackson Core

Jackson Dataformat XML

JUnit Jupiter

SLF4J API

Logback Classic


# Особливості реалізації
Програма уникає повного читання json файлу та його утримання в оперативній пам'яті шляхом використаня Jackson Streaming API для зщитування тільки одного токену за раз, також для більшої швидкодії не створювалися сутності для парсингу а весь код написаний за петодолгією KISS (Keep It Super Simple)


# Файли даних та результату файлу статистики по полю "genres":

https://github.com/StepanIP/ProfITsoftFirstTask/tree/master/src/main/resources/json 

https://github.com/StepanIP/ProfITsoftFirstTask/tree/master/src/main/resources/xml


# Швидкодія потоків:

Тест який перевіряє швидкодію знаходиться за посиланням і називається testParseJsonFiles_Success_MultipleThreads:

https://github.com/StepanIP/ProfITsoftFirstTask/blob/master/src/test/java/JSONParserTest.java

Дані які вдалося отримати під час дослідження:

Execution time with 1 thread(s): 74 ms

Execution time with 2 thread(s): 26 ms

Execution time with 4 thread(s): 23 ms

Execution time with 8 thread(s): 16 ms

Як можна побачити зі збільшенням кількості потоків час який потрібен для парсингу зменшується, особливо різницю можна побачити між 1 та 2 потоками, також сожна побачити що чим більше ми створюємо потоків то меншою стає різниця в часі виконання.




