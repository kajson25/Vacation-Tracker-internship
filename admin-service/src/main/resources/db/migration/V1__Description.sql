DROP TABLE vacation;
DROP TABLE useddays;
DROP TABLE employee;
DROP TABLE admin;

CREATE TABLE IF NOT EXISTS admin (
                            admin_id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS employee (
                            employee_id SERIAL PRIMARY KEY,
                            email VARCHAR(255) NOT NULL,
                            password VARCHAR(255) NOT NULL,
                            admin_id INT,
                            FOREIGN KEY (admin_id) REFERENCES admin(admin_id)
);


CREATE TABLE IF NOT EXISTS vacation (
                            vacation_id SERIAL PRIMARY KEY,
                            noOfDays INT NOT NULL,
                            year INT NOT NULL,
                            employee_id INT,
                            FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS useddays (
                            useddays_id SERIAL PRIMARY KEY,
                            begindate DATE NOT NULL,
                            enddate DATE NOT NULL,
                            employee_id INT,
                            FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE
);

INSERT INTO admin (admin_id) VALUES (1);
INSERT INTO employee (email, password, admin_id) VALUES ('emp1@example.com', 'password123', 1);
INSERT INTO employee (email, password, admin_id) VALUES ('emp2@example.com', 'password234', 1);
INSERT INTO vacation (noOfDays, year, employee_id) VALUES (20, 2019, 1);
INSERT INTO vacation (noOfDays, year, employee_id) VALUES (10, 2019, 2);
INSERT INTO useddays (beginDate, endDate, employee_id) VALUES ('Friday, August 30, 2019','Wednesday, September 11, 2019', 1);
