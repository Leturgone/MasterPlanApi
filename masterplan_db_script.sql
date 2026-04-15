CREATE TABLE app_user (
    id UUID PRIMARY KEY,
    login VARCHAR(45) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
    );


CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    title VARCHAR(45) NOT NULL UNIQUE
);


CREATE TABLE app_user_has_role (
    app_user_id UUID NOT NULL,
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

CREATE TABLE admin_request_status (
    id SERIAL PRIMARY KEY,
    status VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE document (
    id UUID PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    path VARCHAR(255) NOT NULL
);


CREATE TABLE employee (
    id UUID PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    patronymic VARCHAR(45),
    director_id UUID,
    app_user_id UUID NOT NULL,
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
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    plan_status_id INT NOT NULL,
    director_id UUID,
    document_id UUID,
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
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    end_date DATE NOT NULL,
    task_status_id INT NOT NULL,
    plan_id UUID NOT NULL,
    document_id UUID,
    urgency REAL NOT NULL,
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
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    c_date TIMESTAMP NOT NULL,
    edit_date TIMESTAMP DEFAULT NULL,
    description VARCHAR(255) DEFAULT NULL,
    report_status_id INT NOT NULL,
    employee_id UUID NOT NULL,
    task_id UUID NOT NULL,
    document_id UUID NOT NULL,
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
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    c_date TIMESTAMP NOT NULL,
    edit_date TIMESTAMP DEFAULT NULL,
    description VARCHAR(255) DEFAULT NULL,
    report_status_id INT NOT NULL,
    employee_id UUID NOT NULL,
    plan_id UUID NOT NULL,
    document_id UUID NOT NULL,
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
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    c_date TIMESTAMP NOT NULL,
    sender_id UUID NOT NULL,
    admin_request_status_id INT NOT NULL,
    CONSTRAINT fk_admin_request_employee
    FOREIGN KEY (sender_id)
    REFERENCES employee(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT fk_admin_admin_request_status
    FOREIGN KEY (admin_request_status_id)
    REFERENCES admin_request_status(id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);


CREATE TABLE admin_answer (
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    admin_request_id UUID NOT NULL,
    CONSTRAINT fk_admin_answer_admin_request
    FOREIGN KEY (admin_request_id)
    REFERENCES admin_request(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE executor_has_task (
    executor_id UUID NOT NULL,
    task_id UUID NOT NULL,
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


INSERT INTO app_user (id,login,password_hash)  VALUES
('019c657e-8b2a-72de-af27-24d1643de4ab','MASTERPLAN_ADMIN',
'$argon2id$v=19$m=32768,t=2,p=1$4DAKDHFB27kDKDaA0hVHlQ$GCWWdeHfj6kLWA35jtZ11N2R6wB8TDYxvP0Gq7FmFbI');

INSERT INTO role (title) VALUES
('ADMIN'),
('EMPLOYEE'),
('DIRECTOR');

INSERT INTO app_user_has_role VALUES
('019c657e-8b2a-72de-af27-24d1643de4ab', 1),
('019c657e-8b2a-72de-af27-24d1643de4ab', 2);

INSERT INTO employee (id, name, surname, patronymic, director_id, app_user_id) VALUES
('019c658f-a8bb-7fcb-accf-f0c2af73dea6', 'ADMIN', 'MASTERPLAN', NULL, NULL, '019c657e-8b2a-72de-af27-24d1643de4ab');

INSERT INTO task_status (status) VALUES
('COMPLETED'),
('IN_PROGRESS'),
('NOT_STARTED');

INSERT INTO plan_status (status) VALUES
('COMPLETED'),
('IN_PROGRESS'),
('NOT_STARTED');

INSERT INTO report_status (status) VALUES
('NOT_CHECKED'),
('CHECKED'),
('CHECKING'),
('TO_UPDATE');

INSERT INTO admin_request_status (status) VALUES
('COMPLETED'),
('IN_PROGRESS'),
('NOT_STARTED');