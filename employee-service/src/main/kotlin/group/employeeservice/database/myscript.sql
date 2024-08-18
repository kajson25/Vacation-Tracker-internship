DROP TABLE Admin CASCADE ;
DROP TABLE Vacation ;
DROP TABLE UsedDays CASCADE ;
DROP TABLE Employee CASCADE ;

CREATE TABLE IF NOT EXISTS Admin (
                       id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Employee (
                                        id SERIAL PRIMARY KEY,
                                        email VARCHAR(255) NOT NULL,
                                        password VARCHAR(255) NOT NULL,
                                        adminId INT,
                                        FOREIGN KEY (adminId) REFERENCES Admin(id)

);


CREATE TABLE IF NOT EXISTS Vacation (
                          vacation_id SERIAL PRIMARY KEY,
                          noOfDays INT NOT NULL,
                          employee_id INT,
                          FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS UsedDays (
                          id SERIAL PRIMARY KEY,
                          beginDay VARCHAR(16),
                          beginDate DATE NOT NULL,
                          endDay VARCHAR(16),
                          endDate DATE NOT NULL,
                          employeeId INT,
                          FOREIGN KEY (employeeId) REFERENCES Employee(id) ON DELETE CASCADE
);

-- Add an Admin example
INSERT INTO admin (id) VALUES (1);
INSERT INTO employee (id, email, password, adminId) VALUES (1, 'emp1@example.com', 'password123', 1);
INSERT INTO employee (id, email, password, adminId) VALUES (2, 'emp2@example.com', 'password234', 1);
INSERT INTO vacation (vacation_id, noOfDays, employee_id) VALUES (1, 10, 2);
INSERT INTO useddays (id, beginDay, beginDate, endDay, endDate, employeeId) VALUES (1, 'Monday', '2024-01-01', 'Friday', '2024-01-05', 1);

DELETE FROM employee
WHERE employee_id >= 100 AND employee_id <= 500;