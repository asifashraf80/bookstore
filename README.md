# bookstore
Book store implemented with SpringBoot

# Note
All Required Reference data will be created by application at startup

# Valid Book Types
  BTOO1 - Fiction
  BT002 - Comic
  
# Valid Promotion Code
 PRAug2022

# Swagger UI endpoint
http://localhost:8085/library/swagger-ui.html#/

# Sample Payloads
<b>New request</b>
{
  "author": "Sajid Khan",
  "bookTypeCode": "BT001",
  "description": "First Book Ever",
  "isbn": "ISBN1",
  "name": "First Book",
  "price": 1000
}
 
 <b>Update request</b>
{
	"id":1
  "author": "Sajid Khan",
  "bookTypeCode": "BT001",
  "description": "First Book Ever",
  "isbn": "ISBN1",
  "name": "First Book",
  "price": 1000
}

<b>Search request</b>
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

<b>Checkout request</b>
{
  "bookIds": [
    5,
    4
  ],
  "promocode":"PRAug2022" 
}
<br> Promo Code is optional
