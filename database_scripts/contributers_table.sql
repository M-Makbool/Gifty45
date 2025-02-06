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