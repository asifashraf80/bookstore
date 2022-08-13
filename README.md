# bookstore
Book store implemented with SpringBoot

# Note
All Required Reference data will be created by application at startup, Application is utilizing H2 DB 

# Prerequsite
Java 8 <br>
Maven 3.6+ <br>
Docker (if you intend to deploy in containerized environment)

# How to Run
<b>Option 1: Command Line </b>
	mvn clean install -DskipTests
	java -jar librarymgmt-0.0.1-SNAPSHOT.jar
	<br>
<b>Option 2: Docker Container</b>
	docker build .
	
# Valid Book Types
  BTOO1 - Fiction
  BT002 - Comic
  
# Valid Promotion Code
 PRAug2022

# Swagger UI endpoint
http://localhost:8085/library/swagger-ui.html#/

# Sample Payloads
<b>New Book</b>
{
  "author": "Sajid Khan",
  "bookTypeCode": "BT001",
  "description": "First Book Ever",
  "isbn": "ISBN1",
  "name": "First Book",
  "price": 1000
}
 
 <b>Update Book</b>
{
	"id":1
  "author": "Sajid Khan",
  "bookTypeCode": "BT001",
  "description": "First Book Ever",
  "isbn": "ISBN1",
  "name": "First Book",
  "price": 1000
}

<b>Search Book</b>
{
    "pageNo": 0,
    "pageSize": 20,
     "filters": [
       {
         "id":"name",
         "value":"Book"
       }
     ]
} <br>Allowed filter ids are name,description,author,isbn

<b>Checkout Books</b>
{
  "bookIds": [
    5,
    4
  ],
  "promocode":"PRAug2022" 
}
<br> Promo Code is optional
