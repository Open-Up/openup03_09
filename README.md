# Running this example

```
mvn clean install
mvn exec:java
```

# Issuing commands

```
curl -XPOST http://127.0.0.1:4567/products/toto -d '12'
curl -XGET http://127.0.0.1:4567/purchase/toto
curl -XGET http://127.0.0.1:4567/account
curl -XGET http://127.0.0.1:4567/products
```

# Following questions

## Topic zero : Get to know the application

Question 1 : Clone this repository

Question 2 : run it (JRE 8 + maven 3 required)

Question 3 : Create products and make purchases

 - Is it fast ?

We will now read the code. The code is composed of :

 - A web server : src/main/java/com/linagora/openup/cqrs/web/WebServer.java
 - A business layer : src/main/java/com/linagora/openup/cqrs/logic/PurchaseManager.java. The current implementation is a classic CRUD that simulates delays. We want to get ride
 of such delays with a CQRS pattern. Implementation will use a separated business class that will simply wrap PurchaseManager with a Kafka queue. Is can be found here :
 src/main/java/com/linagora/openup/cqrs/logic/cqrs/CQRSPurchaseManager.java
 - A simple In Memory storage layer : src/main/java/com/linagora/openup/cqrs/storage

## Topic one : Move application to the CQRS pattern

Question 0 : Install kafka using spotify/kafka docker image and create a topic.

Question 1 & 2 : src/main/java/com/linagora/openup/cqrs/logic/cqrs/CQRSPurchaseManager.java

Question 3 : src/main/java/com/linagora/openup/cqrs/web/WebServer.java

 - What are are benefits ? Drawbacks ?

## Topic two : Replay

Question 1 : Restart your application. List the product and read the account. What do you observe ? Then create again your products.

Question 2 : Following https://metabroadcast.com/blog/resetting-kafka-offsets reset your kafka topic to the beginning.

Question 3 : Read the account. What do you observe ?

## Topic three : composability

Question 1 : Add a notification feature about purchase. This can be done using class : src/main/java/com/linagora/openup/cqrs/notification/NotifyPurchase.java

Of course, we don't want this notification to slow down writes, and we can do it in parallel from purchase treatment.
