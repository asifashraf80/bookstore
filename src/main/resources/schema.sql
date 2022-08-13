
CREATE TABLE book_type (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  code VARCHAR(20) NOT NULL 
);

CREATE TABLE promotion (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  description VARCHAR(250) NOT NULL,
  code VARCHAR(20) NOT NULL 
);


CREATE TABLE book_type_promotion (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  book_type_id INT NOT NULL,
      CONSTRAINT fk_promotio_book_type
      FOREIGN KEY (book_type_id) REFERENCES book_type(id),
  promotion_id INT NOT NULL,
      CONSTRAINT fk_promotion_book_type_promotion
      FOREIGN KEY (promotion_id) REFERENCES promotion(id),
  discount_rate decimal (8,2) NOT NULL
);

CREATE TABLE book (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  author VARCHAR(250) NOT NULL,
  isbn VARCHAR(250) NOT NULL,
  price decimal (8,2) NOT NULL,
  deleted boolean not null default 0,
  current boolean not null default 1,
  book_type_id INT NOT NULL,
      CONSTRAINT fk_book_book_type
      FOREIGN KEY (book_type_id) REFERENCES book_type(id)  
);

CREATE TABLE ROLE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  ROLE VARCHAR(250) NOT NULL
);


CREATE TABLE USERS (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  email VARCHAR(250),
  password VARCHAR(250) NOT NULL
);

CREATE TABLE user_role (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id INT NOT NULL,
      CONSTRAINT fk_user_role_user
      FOREIGN KEY (user_id) REFERENCES users(id),
  role_id INT NOT NULL,
      CONSTRAINT fk_user_role_role
      FOREIGN KEY (role_id) REFERENCES role(id)
);


CREATE TABLE book_order (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  reference_number VARCHAR(12) NOT NULL,
	promotion_id INT DEFAULT NULL ,
      CONSTRAINT fk_promotion_order_promotion
      FOREIGN KEY (promotion_id) REFERENCES promotion(id) 
);

CREATE TABLE order_item (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  book_id INT NOT NULL,
      CONSTRAINT fk_order_item_book
      FOREIGN KEY (book_id) REFERENCES book(id),
  
	order_id INT NOT NULL,
      CONSTRAINT fk_order_item_order
      FOREIGN KEY (order_id) REFERENCES book_order(id),      
  discount_rate decimal (8,2) NOT NULL,      
  price decimal (8,2) NOT NULL
);

