DROP TABLE IF EXISTS Product;

CREATE TABLE Product (
    id   	NUMBER      	NOT NULL AUTO_INCREMENT,
    name 	VARCHAR(128),
    category 	VARCHAR(128),
    amount 	INTEGER,
    creationdate Timestamp,
    updatedate 	Timestamp,
    PRIMARY KEY (id)
);
