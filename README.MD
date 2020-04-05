<h2>Travel bot</h2>

<strong>bot name:</strong> oleg_travelbot

<strong>bot username:</strong> travel_oleg_bot

<strong>bot token:</strong> 1001541449:AAHb8V78969JuDTrONF9er8ugrP0XVmhgyc

To start working with bot, you have to fire it on your local PC using your favourite IDE. 

But first you must create a database with name "resliv_test" on your local mongo instance, after that create a 
collection with name "cities" on your newly created database, then you can fill the collection using 
the provided REST API or by adding some data manually to mongodb from 'city.json' file.

Provided REST API Methods:
<ul>
<li>GET "/cities/" - list cities, presented in database</li>
<li>GET "/cities/{id}" - finds city with provided id</li>
<li>GET "/cities/name/{name}" - finds city by name</li>
<li>POST "/cities/" - adds the city, passed with request body, to the database, if it doesn't already exist</li>
<li>PUT "/cities/{id}" - replaces the current text data for the city with the provided id</li>
<li>DELETE "/cities/{id}" - deletes the city with the provided id from the database</li>
</ul>

Some API request for Postman tool presented in 'resliv_city.postman_collection.json' file.

<i>Bot speaks russian!</i>

Used technology stack: SpringBoot, JPARepository, MongoDb, REST API, TelegramBot



