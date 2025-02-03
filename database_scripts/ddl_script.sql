-- Users Table
DROP TABLE IF EXISTS 
CREATE TABLE Users (
    User_login VARCHAR2(50) PRIMARY KEY,
    user_email VARCHAR2(100) UNIQUE,
    user_name VARCHAR2(100),
    id NUMBER UNIQUE,
    telephone VARCHAR2(20),
    is_deleted CHAR(1),
    Gender VARCHAR2(10),
    Password VARCHAR2(100),
    DOB DATE
);

-- Items Table
DROP TABLE IF EXISTS
CREATE TABLE Items (
    Item_id NUMBER identity PRIMARY KEY,
    Name VARCHAR2(100),
    Price NUMBER(10,2),
    Category VARCHAR2(50),
    Status VARCHAR2(20)
);

-- Friends Table
DROP TABLE IF EXISTS
CREATE TABLE Friends (
    User_login VARCHAR2(50),
    Friend_login VARCHAR2(50),
    Status VARCHAR2(20),
    PRIMARY KEY (User_login, Friend_login),
    FOREIGN KEY (User_login) REFERENCES Users(User_login),
    FOREIGN KEY (Friend_login) REFERENCES Users(User_login)
);

-- Wishing_list Table
DROP TABLE IF EXISTS
CREATE TABLE Wishing_list (
    Item_id NUMBER,
    User_login VARCHAR2(50),
    Wish_Date DATE,
    Status VARCHAR2(20),
    PRIMARY KEY (Item_id, User_login, Wish_Date),
    FOREIGN KEY (Item_id) REFERENCES Items(Item_id),
    FOREIGN KEY (User_login) REFERENCES Users(User_login)
);
DROP TABLE IF EXISTS
CREATE TABLE Contributers (
    Item_id NUMBER,
    User_login VARCHAR2(50),
    Wish_Date DATE,
    Contributee_id NUMBER,
    Contribution_date DATE,
    Contribution_amount NUMBER(10,2),
    PRIMARY KEY (Item_id, User_login, Wish_Date, Contributee_id, Contribution_date),
    FOREIGN KEY (Item_id) REFERENCES Items(Item_id),
    FOREIGN KEY (User_login) REFERENCES Users(User_login)
);
