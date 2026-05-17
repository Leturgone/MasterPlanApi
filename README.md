## MasterPlanApi

REST API для контроля выполнения планов мероприятий для приложения [MasterPlan](https://github.com/Leturgone/MasterPlan)

## Описание

MasterPlanApi - серверная часть системы MasterPlan. Позволяет контролировать планы мероприяти


Swagger doc - http://localhost:8080/swagger-ui/index.html#/

# Instalation 

1. Clone repository
```bash
git clone https://github.com/Leturgone/MasterPlanApi.git
```

2. Cd to directory
```bash
cd MasterPlanApi
```
3. Create .env
```bash
nano .env
```

4. Enter your variables in .env
```bash
DATABASE_USER=postgres
DATABASE_PASSWORD=password
MASTERPLAN_JWT_SECRET=your_jwt_secret
MASTERPLAN_FILES_STORAGE=your_files_storage_path
MASTERPLAN_FILES_STORAGE_ENCRYPT_KEY=your_files_storage_encrypt_key
```

6. Execute container
```bash
docker-compose up
```
