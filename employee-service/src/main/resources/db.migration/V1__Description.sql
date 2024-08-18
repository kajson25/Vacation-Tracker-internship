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