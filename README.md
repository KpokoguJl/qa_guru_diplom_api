# Проект автоматизации тестирования API демо проекта petstore.swagger.io
Проект создан в рамках обучения в школе QA.GURU и представляет из себя часть выпускной работы.

<a name="оглавление"></a>
# Оглавление
1. [Технологии](#технологии)
2. [Описание проекта](#описание)
3. [Запуск тестов](#запуск)
    1. [Запуск тестов локально](#запуск_локально)
    2. [Запуск тестов в Jenkins](#запуск_дженкинс)
4. [Jenkins](#дженкинс)
5. [Результаты тестов в телеграм](#телеграм)
6. [Allure TestOps](#проект)
    1. [Проект](#проект)
    2. [Интеграция с Jenkins](#интеграция)
    3. [Dashboard](#дашборд)

<a name="технологии"></a>
# Использованны слудующие технологии:
<p align="center">
<img width="16%" title="Gradle" src="media/Gradle.svg">
<img width="16%" title="Java" src="media/Java.svg">
<img width="16%" title="JUnit5" src="media/JUnit5.svg">
<img width="16%" title="IntelliJ IDEA" src="media/Intelij_IDEA.svg">
<img width="14%" title="Rest Assured" src="media/RestAssured.svg">
<img width="16%" title="Allure Report" src="media/Allure_Report.svg">
<img width="16%" title="GitHub" src="media/GitHub.svg">
<img width="16%" title="Jenkins" src="media/Jenkins.svg">
<img width="15%" title="Allure TestOps" src="media/Allure-logo.svg">
</p>

[К оглавлению ⬆](#оглавление)
<a name="описание"></a>
# Описание проекта
Автоматизирована проверка двух ручек API:
- /pets - создание, обновление и удаление питомца из магазина,
- /users - создание, получение пользователя магазина.

Используются: 
- модели (Lombok), 
- спецификации (RequestSpecification), 
- генерация тестовых данных (Faker), 
- конфигурации (Owner),
- проверки (AssertJ)
- шаблоны логирования запросов (AllureRestAssured FILTERS). 

[К оглавлению ⬆](#оглавление)
<a name="запуск_локально"></a>
# Запуск тестов
Локальный запуск осуществляется командой: 
```
gradle clean test -Dthreads=4
```
Где `threads` - количество потоков параллельного запуска тестов.

<a name="запуск_дженкинс"></a>
Для запуска тестов в Jenkins используется следующая команда:
```
clean
test
-DbaseUrl=${BASEURL}
```
Где:
`${BASEURL}` - url API для теста (прменяется для прогона тестов на разном окружении).
 
> **Note:** *в Jenkins не используется многопоточный запуск, чтобы не нагружать ресурсы школы!* 

[К оглавлению ⬆](#оглавление)
<a name="дженкинс"></a>
# Запуск тестов в Jenkins выглядит следующим образом
Главная страница сборки
![](media/JenkinsJob.svg)

Выбор параметров сборки
![](media/JenkinsJobStart.svg)

Работа сборки
![](media/JenkinsJobWork.svg)

Отчет о выполнении тестов
![](media/AllureReport.svg)

Каждый тест, независимо от результата, состоит из:
- шагов, 
- лога запроса,
- лога ответа. 

![](media/AllureReportAll.svg)

Каждый запрос и ответ API логируется в удобном виде с помощью настраиваемых шаблонов
![](media/AllureReportResponseLog.svg)

Тесты запускаются в многопоточном режиме
![](media/Multithread.svg)

[К оглавлению ⬆](#оглавление)
<a name="телеграм"></a>
# По результатам работы тестов отправляется краткий отчет в Telegram
![](media/TelegramBot.svg)

[К оглавлению ⬆](#оглавление)
<a name="проект"></a>
# Создан проект в Allure TestOps
Тесты в проекте импортированы из кода, то есть не приходится писать тесты и автоматизировать их.
Достаточно написать автотест, а кейс в TMS всегда будет в актуальном состоянии. Так же, на проекте есть ручные тесты.
![](media/TestCases.svg)

[К оглавлению ⬆](#оглавление)
<a name="интеграция"></a>
# Настроена интеграция Jenkins и Allure TestOps
Запуск джоб осуществляется из интерфейса Allure TestOps
![](media/AllureJobs.svg)

Результаты работы джоб также отображаются в Allure TestOps
![](media/LaunchedJobAllure.svg)

[К оглавлению ⬆](#оглавление)
<a name="дашборд"></a>
# Настроен Dashboard с разными показателями
Отображаются графики тренда автоматизации, последний запуск и т.д.
![](media/Dashboard.svg)

