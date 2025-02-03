-- Items Table
DROP table if exists Items;
CREATE TABLE Items (
    Item_id NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    Price NUMBER(10,2),
    Category VARCHAR2(50),
    Status VARCHAR2(20)
);

