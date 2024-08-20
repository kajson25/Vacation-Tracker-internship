DROP TABLE Admin CASCADE ;
DROP TABLE Vacation ;
DROP TABLE UsedDays CASCADE ;
DROP TABLE Employee CASCADE ;

CREATE TABLE IF NOT EXISTS Admin (
                            admin_id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Employee (
                            employee_id SERIAL PRIMARY KEY,
                            email VARCHAR(255) NOT NULL,
                            password VARCHAR(255) NOT NULL,
                            adminId INT,
                            FOREIGN KEY (adminId) REFERENCES Admin(admin_id)

);


CREATE TABLE IF NOT EXISTS Vacation (
                          vacation_id SERIAL PRIMARY KEY,
                          noOfDays INT NOT NULL,
                          year INT NOT NULL,
                          employee_id INT,
                          FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS UsedDays (
                          useddays_id SERIAL PRIMARY KEY,
                          begindate DATE NOT NULL,
                          enddate DATE NOT NULL,
                          employee_id INT,
                          FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE CASCADE
);

-- Add an Admin example
INSERT INTO admin (admin_id) VALUES (1);
INSERT INTO employee (email, password, adminId) VALUES ('emp3@example.com', 'password345', 1);
INSERT INTO vacation (noOfDays, year, employee_id) VALUES (15, 2019, 2);
INSERT INTO useddays (beginDate, endDate, employee_id) VALUES ('Monday 2024-01-01', 'Friday 2024-01-05', 1);

DELETE FROM employee
WHERE employee_id >= 100 AND employee_id <= 500;