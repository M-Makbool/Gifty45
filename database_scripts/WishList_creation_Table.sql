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


