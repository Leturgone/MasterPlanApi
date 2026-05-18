# MasterPlanApi

[![Deploy Swagger Docs to GitHub Pages](https://github.com/Leturgone/MasterPlanApi/actions/workflows/deploy-swagger.yml/badge.svg)](https://github.com/Leturgone/MasterPlanApi/actions/workflows/deploy-swagger.yml)
[![pages-build-deployment](https://github.com/Leturgone/MasterPlanApi/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/Leturgone/MasterPlanApi/actions/workflows/pages/pages-build-deployment)

REST API для контроля выполнения планов мероприятий для приложения [MasterPlan](https://github.com/Leturgone/MasterPlan)

## Оглавление

- [Описание](#описание)
- [Технологический стек](#технологический-стек)
- [Установка](#установка)
- [Архитектура](#архитектура)
- [Схема БД](#схема-бд)
- [Использование](#использование)

## Описание

<img align="left" width="200" hspace="10" vspace="10" alt="изображение" src="https://github.com/user-attachments/assets/41c499bd-b156-44bc-96bd-7155961285d2" />

MasterPlanApi - серверная часть системы MasterPlan. Позволяет контролировать планы мероприятий, включая отслеживание статусов проверки отчетов и выполнения планов и задач из них. Кроме того позволяет руководителю отслеживать метрики исполнителей, включающих загруженность и количество назначенных задач.

<br><br><br><br><br><br>

## Технологический стек

- **Backend:** SpringBoot, Spring Modulith. Spring MVC
- **Работа с Exel файлами:** Apache POI
- **Язык программирования:** Kotlin
- **База данных:** PostgreSQL
- **Безопасность:** JWT, Bouncy Castle
- **Документация API:** Swagger UI
- **Тестирование:** JUnit, MockK
- **Мониторинг:** Garafana, Loki, Promtail
- **Архитектура:** Clean Architecture, Модульный монолит, MVC
- **Развертывание:** Docker

## Установка 

1. Склонировать репозиторий
```bash
git clone https://github.com/Leturgone/MasterPlanApi.git
```

2. Cd к директории
```bash
cd MasterPlanApi
```
3. Создать .env
```bash
nano .env
```

4. Заполнить переменные .env
```bash
DATABASE_USER=postgres
DATABASE_PASSWORD=password
MASTERPLAN_JWT_SECRET=your_jwt_secret
MASTERPLAN_FILES_STORAGE=your_files_storage_path
MASTERPLAN_FILES_STORAGE_ENCRYPT_KEY=your_files_storage_encrypt_key
```

6. Запустить контейнер
```bash
docker-compose up
```

## Архитектура

Используется архитектурный подход Модульный монолит. Сочетает подход микросервисной и монолитной архитектуры. Приложение развертывается как единое целое, но каждый модуль взаимодействует с друг другом используя 
публичные интерфейсы.

Приложение состоит из следующих модулей:
<img width="1689" height="700" alt="изображение" src="https://github.com/user-attachments/assets/2e2a3349-2f77-48d3-a2aa-8724ed6c46b0" />


## Схема БД

<img width="1461" height="1101" alt="СхемаБд3" src="https://github.com/user-attachments/assets/096072b2-6612-498a-b8ee-7f0124266229" />


## Использование 

Документация Swagger представлена после развертывания по адресу http://localhost:8080/swagger-ui/index.html#/ а также в gh pages

<img width="1740" height="995" alt="изображение" src="https://github.com/user-attachments/assets/4b815173-7095-4106-9047-b432cadf8c39" />


Дашборд с логами доступен по адресу http://localhost:3000


<img width="1915" height="1019" alt="изображение" src="https://github.com/user-attachments/assets/15296554-f99a-47bb-b8fd-c0b9ccd4b4fb" />



