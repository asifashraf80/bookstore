INSERT INTO 
	book_type (code, name) 
VALUES
  	('BT001', 'Fiction'),
  	('BT002', 'Comic');
  	
INSERT INTO 
	promotion (code, description) 
VALUES
  	('PRAug2022', 'Promotion for August 2022');

INSERT INTO 
	book_type_promotion (book_type_id, promotion_id,discount_rate) 
VALUES
  	(1, 1, 10)
  	;  	