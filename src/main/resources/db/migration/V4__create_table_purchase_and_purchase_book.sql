CREATE TABLE IF NOT EXISTS purchase(
    id int auto_increment primary key,
    customer_id int not null,
    book_id int not null,
    nfe varchar(255),
    price decimal(10,2) not null,
    createdAt DATETIME not null,
    FOREIGN KEY(customer_id) REFERENCES customer(id)
);

CREATE TABLE IF NOT EXISTS purchase_book(
    purchase_id int not null,
    book_id int not null,
    FOREIGN KEY(purchase_id) REFERENCES purchase(id),
    FOREIGN KEY(book_id) REFERENCES book(id),
    PRIMARY KEY(purchase_id, book_id)
);