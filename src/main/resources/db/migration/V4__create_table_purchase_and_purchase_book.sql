CREATE TABLE IF NOT EXISTS purchase(
    id int auto_increment primary key,
    seller int not null,
    buyer int not null,
    nfe varchar(255),
    price decimal(10,2) not null,
    created_at DATETIME not null,
    FOREIGN KEY(seller) REFERENCES customer(id),
    FOREIGN KEY(buyer) REFERENCES customer(id)
);

CREATE TABLE IF NOT EXISTS purchase_book(
    purchase_id int not null,
    book_id int not null,
    FOREIGN KEY(purchase_id) REFERENCES purchase(id),
    FOREIGN KEY(book_id) REFERENCES book(id),
    PRIMARY KEY(purchase_id, book_id)
);