-- Users Table
CREATE TABLE Users (
    User_login VARCHAR2(50) PRIMARY KEY,
    user_email VARCHAR2(100),
    user_name VARCHAR2(100),
    id NUMBER UNIQUE,
    telephone VARCHAR2(20),
    is_deleted CHAR(1),
    Gender VARCHAR2(10),
    Password VARCHAR2(100),
    DOB DATE
);

