DROP TABLE IF EXISTS Contributers ;
DROP TABLE IF EXISTS Wishing_list;
DROP TABLE IF EXISTS Friends;
DROP TABLE IF EXISTS Items;
DROP TABLE IF EXISTS Users;
-- Users Table
CREATE TABLE Users (
    User_login VARCHAR2(50) PRIMARY KEY,
    user_email VARCHAR2(100),
    user_name VARCHAR2(100),
    id NUMBER UNIQUE,
    telephone VARCHAR2(20),
    is_deleted BOOLEAN,
    Gender VARCHAR2(10),
    Password VARCHAR2(100),
    balance NUMBER(10,2), 
    DOB DATE
);

-- Items Table
CREATE TABLE Items (
    Item_id NUMBER generated always as identity PRIMARY KEY,
    Name VARCHAR2(100),
    Price NUMBER(10,2),
    Category VARCHAR2(50),
    Status VARCHAR2(20)
);

-- Friends Table
CREATE TABLE Friends (
    User_login VARCHAR2(50),
    Friend_login VARCHAR2(50),
    Status VARCHAR2(20),
    PRIMARY KEY (User_login, Friend_login),
    FOREIGN KEY (User_login) REFERENCES Users(User_login),
    FOREIGN KEY (Friend_login) REFERENCES Users(User_login)
);

-- Wishing_list Table
CREATE TABLE Wishing_list (
    Item_id NUMBER,
    User_login VARCHAR2(50),
    Wish_Date DATE,
    Status VARCHAR2(20),
    PRIMARY KEY (Item_id, User_login, Wish_Date),
    FOREIGN KEY (Item_id) REFERENCES Items(Item_id),
    FOREIGN KEY (User_login) REFERENCES Users(User_login)
);
CREATE TABLE Contributers (
    Item_id NUMBER,
    User_login VARCHAR2(50),
    Wish_Date DATE,
    Contributee_login VARCHAR2(50),
    Contribution_date DATE,
    Contribution_amount NUMBER(10,2),
    PRIMARY KEY (Item_id, User_login, Wish_Date, Contributee_login, Contribution_date),
    FOREIGN KEY (Item_id) REFERENCES Items(Item_id),
    FOREIGN KEY (User_login) REFERENCES Users(User_login)
);
