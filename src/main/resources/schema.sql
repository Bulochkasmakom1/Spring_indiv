    create table participant (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          child VARCHAR(50),
                          date_birth DATE,
                          city VARCHAR(50),
                          country VARCHAR(50),
                          parent VARCHAR(50),
                          email_address VARCHAR(50),
                          phone_number VARCHAR(50)
);