create database masterplan_db;
\c masterplan_db postgres;
psql \! chcp 1251


CREATE TABLE app_user (
    id SERIAL PRIMARY KEY, 
    login VARCHAR(45) NOT NULL UNIQUE, 
    password_hash VARCHAR(255) NOT NULL
    );


CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    title VARCHAR(45) NOT NULL UNIQUE
);


CREATE TABLE app_user_has_role (
    app_user_id INT NOT NULL,
    role_id INT NOT NULL ,
    PRIMARY KEY (app_user_id, role_id),
    CONSTRAINT fk_app_user_has_role_app_user
    FOREIGN KEY (app_user_id)
    REFERENCES app_user(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT fk_app_user_has_role_app_role
    FOREIGN KEY (role_id)
    REFERENCES role(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE plan_status (
    id SERIAL PRIMARY KEY,
    status VARCHAR(45) NOT NULL UNIQUE
);


CREATE TABLE report_status (
    id SERIAL PRIMARY KEY,
    status VARCHAR(45) NOT NULL UNIQUE
);


CREATE TABLE task_status (
    id SERIAL PRIMARY KEY,
    status VARCHAR(45) NOT NULL UNIQUE
);


CREATE TABLE document (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    path VARCHAR(255) NOT NULL
);


CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    patronymic VARCHAR(45),
    director_id INT,
    app_user_id INT NOT NULL,
    CONSTRAINT fk_employee_director
    FOREIGN KEY (director_id)
    REFERENCES employee(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    CONSTRAINT fk_employee_app_user
    FOREIGN KEY (app_user_id)
    REFERENCES app_user(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE plan (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    plan_status_id INT NOT NULL,
    director_id INT,
    document_id INT,
    CONSTRAINT fk_plan_plan_status
    FOREIGN KEY (plan_status_id)
    REFERENCES plan_status(id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    CONSTRAINT fk_plan_director
    FOREIGN KEY (director_id)
    REFERENCES employee(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    CONSTRAINT fk_plan_document
    FOREIGN KEY (document_id)
    REFERENCES document(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);


CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    end_date TIMESTAMP,
    task_status_id INT NOT NULL,
    plan_id INT NOT NULL,
    document_id INT,
    CONSTRAINT fk_task_task_status
    FOREIGN KEY (task_status_id)
    REFERENCES task_status(id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    CONSTRAINT fk_task_plan
    FOREIGN KEY (plan_id)
    REFERENCES plan(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT fk_task_document
    FOREIGN KEY (document_id)
    REFERENCES document(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);


CREATE TABLE task_report (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    c_date TIMESTAMP NOT NULL,
    edit_date TIMESTAMP DEFAULT NULL,
    description VARCHAR(255),
    report_status_id INT NOT NULL,
    employee_id INT NOT NULL,
    task_id INT NOT NULL,
    document_id INT NOT NULL,
    CONSTRAINT fk_task_report_report_status
    FOREIGN KEY (report_status_id)
    REFERENCES report_status(id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    CONSTRAINT fk_task_report_employee
    FOREIGN KEY (employee_id)
    REFERENCES employee(id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    CONSTRAINT fk_task_report_task
    FOREIGN KEY (task_id)
    REFERENCES task(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT fk_task_report_document
    FOREIGN KEY (document_id)
    REFERENCES document(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE plan_report (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    c_date TIMESTAMP NOT NULL,
    edit_date TIMESTAMP DEFAULT NULL,
    description VARCHAR(255),
    report_status_id INT NOT NULL,
    employee_id INT NOT NULL,
    plan_id INT NOT NULL,
    document_id INT NOT NULL,
    CONSTRAINT fk_plan_report_report_status
    FOREIGN KEY (report_status_id)
    REFERENCES report_status(id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    CONSTRAINT fk_plan_report_employee
    FOREIGN KEY (employee_id)
    REFERENCES employee(id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    CONSTRAINT fk_plan_report_plan
    FOREIGN KEY (plan_id)
    REFERENCES plan(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT fk_plan_report_document
    FOREIGN KEY (document_id)
    REFERENCES document(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE admin_request (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    c_date TIMESTAMP NOT NULL,
    sender_id INT NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_admin_request_employee
    FOREIGN KEY (sender_id)
    REFERENCES employee(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE admin_answer (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(45) NOT NULL,
    admin_request_id INT NOT NULL,
    CONSTRAINT fk_admin_answer_admin_request
    FOREIGN KEY (admin_request_id)
    REFERENCES admin_request(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE executor_has_task (
    executor_id INT NOT NULL,
    task_id INT NOT NULL,
    PRIMARY KEY (executor_id, task_id),
    CONSTRAINT fk_executor_has_task_employee
    FOREIGN KEY (executor_id)
    REFERENCES employee(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT fk_executor_has_task_task
    FOREIGN KEY (task_id)
    REFERENCES task(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);




CREATE INDEX idx_employee_director ON employee(director_id);
CREATE INDEX idx_employee_app_user ON employee(app_user_id);


CREATE INDEX idx_plan_plan_status ON plan(plan_status_id);
CREATE INDEX idx_plan_director ON plan(director_id);
CREATE INDEX idx_plan_document ON plan(document_id);

CREATE INDEX idx_task_task_status ON task(task_status_id);
CREATE INDEX idx_task_plan ON task(plan_id);
CREATE INDEX idx_task_document ON task(document_id);


CREATE INDEX idx_task_report_report_status ON task_report(report_status_id);
CREATE INDEX idx_task_report_employee ON task_report(employee_id);
CREATE INDEX idx_task_report_task ON task_report(task_id);
CREATE INDEX idx_task_report_document ON task_report(document_id);



CREATE INDEX idx_executor_has_task_task ON executor_has_task(task_id);
CREATE INDEX idx_executor_has_task_employee ON executor_has_task(executor_id);

CREATE INDEX idx_plan_report_employee ON plan_report(employee_id);
CREATE INDEX idx_plan_report_plan ON plan_report(plan_id);
CREATE INDEX idx_plan_report_document ON plan_report(document_id);
CREATE INDEX idx_plan_report_report_status ON plan_report(report_status_id);

CREATE INDEX idx_admin_request_sender ON admin_request(sender_id);

CREATE INDEX idx_app_user_has_role_role ON app_user_has_role(role_id);
CREATE INDEX idx_app_user_has_role_app_user ON app_user_has_role(app_user_id);


CREATE INDEX idx_admin_answer_admin_request ON admin_answer(admin_request_id);