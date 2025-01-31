-- Friends Table
CREATE TABLE Friends (
    User_login VARCHAR2(50),
    Friend_login VARCHAR2(50),
    Status VARCHAR2(20),
    PRIMARY KEY (User_login, Friend_login),
    FOREIGN KEY (User_login) REFERENCES Users(User_login),
    FOREIGN KEY (Friend_login) REFERENCES Users(User_login)
);
