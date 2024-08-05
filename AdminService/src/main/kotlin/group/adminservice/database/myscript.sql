DROP TABLE Admin;

DROP TABLE Vacation;
DROP TABLE UsedDays;
DROP TABLE Employee;

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
                          id SERIAL PRIMARY KEY,
                          noOfDays INT NOT NULL,
                          employeeId INT,
                          FOREIGN KEY (employeeId) REFERENCES Employee(id) ON DELETE CASCADE
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
INSERT INTO Admin (id) VALUES (1);
INSERT INTO Employee (id, email, password, adminId) VALUES (1, 'emp1@example.com', 'password123', 1);

-- Add an Employee example
INSERT INTO Employee (id, email, password, adminId) VALUES (2, 'emp2@example.com', 'password234', 1);
INSERT INTO Vacation (id, noOfDays, employeeId) VALUES (1, 10, 2);
INSERT INTO UsedDays (id, beginDay, beginDate, endDay, endDate, employeeId) VALUES (1, 'Monday', '2024-01-01', 'Friday', '2024-01-05', 1);
