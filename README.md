RESTful Chessgame
=================
This project was developed as part of my diploma thesis at the Hochschule für Technik und Wirtschaft (<https://www.htw-dresden.de/startseite.html>). 
The written elaboration can be found under ``doc/diploma`` and was created with the Latex thesis template developed by Matthias Pospiech. (<http://www.matthiaspospiech.de/latex/vorlagen/allgemein/>)

The project can be separated by two modules which are developed with Kotlin. Gradle serves as build tool. 
As compilation target, the client has JavaScript and the server JVM.

Table of Content
----------------
* [Requirements](#requirements)
* [Getting Started](#getting-started)
* [Server](#server)
    * [Run Server](#run-server)
    * [Server Configuration](#server-configuration)
    * [Entry Points](#entry-points)
    * [Content Negotiation](#content-negotiation)
* [Client](#client)
    * [Run Client](#run-client)
    * [Client Configuration](#client-configuration)

Requirements
---------------
* Java 8 JRE (to start the project)
* Java 8 SDK (to build the Project)


Getting Started
---------------
Pull repository:

``git clone git@github.com:GagaMen/chessgame.git``

Move into project folder:

``cd chessgame``

Build the project:

``./gradlew build``

Start server with client as static resources:

``java -jar server/build/libs/chessgame-server-1.0.0.war``

Server
------
Build Server:
``./gradlew :server:build``

### Run Server
* with gradle: ``./gradlew :server:bootRun``
* with jar: ``java -jar server/build/libs/chessgame-server-1.0.0.jar`` (without static resources)
* with war: ``java -jar server/build/libs/chessgame-server-1.0.0.war`` (with static resources -> client module)

### Server Configuration
The Server starts on port ``8080`` by default. If you want to change the port override the ``server.port`` config entry 
in the ``application.yml`` file, which is located under ``src/main/resources``. 
You have to rebuild the project if you overwrites the configuration.

### Entry Points
> ***``{id}`` is a placeholder for a number!***

|URI                     | Methode | Usage 
|------------------------|---------|--------------------------------------------------------------------------------|
|/                       | GET     | Get static resources (client module). This only works if you run the .war file.|
|/players                | GET     | Get a list of all registered players                                           |
|/players                | POST    | Create a player                                                                |
|/players/{id}           | GET     | Get a single player                                                            |
|/players/{id}           | DELETE  | Delete a single player                                                         |
|/players/{id}           | PATCH   | Update a single player                                                         |
|/matches                | GET     | Get a list of all registered matches                                           |
|/matches                | POST    | Create a match                                                                 |
|/matches/{id}           | GET     | Get a single match                                                             |
|/matches/{id}           | DELETE  | Delete a single match                                                          |
|/matches/{id}/draws     | GET     | Get a list of draws from a match                                               |
|/matches/{id}/pieceSets | GET     | Get the pieceSets from a match                                                 |
|/draws                  | GET     | Get a list of all registered draws                                             |
|/draws                  | POST    | Create a draw                                                                  |
|/draws/{id}             | GET     | Get a single draw                                                              |

#### POST */players*
Parameters:
* name: String
* password: String

Send parameters via ``application/x-www-form-urlencoded``:
* ``name=Test&password=123456``

Send parameters via ``application/json``:
* ``{ "name": "Test", "password": "123456" }``

#### PATCH */players/{id}*
Parameters:
* password: String

Send parameters via ``application/x-www-form-urlencoded``:
* ``password=123456``

Send parameters via ``application/json``:
* ``{ "password": "123456" }``

### GET */matches* and */matches/{id}*
Parameters:
* includePieceSets (optional: default value is true)
    > If true match or matches contains all information about pieceSets
* includeHistory (optional: default value is true)
    > If true match or matches contains all draws

You can request this information later via GET request to URI ``/matches/{id}/pieceSets`` and ``/matches/{id}/draws``.

Send parameters via URL:
* ``http://localhost:8080/matches?includePieceSets=false``
* ``http://localhost:8080/matches/555555?includeHistory=false``
* ``http://localhost:8080/matches?includePieceSets=false&includeHistory=false``

#### POST */matches*
Parameters:
* playerWhiteId: Int
* playerBlackId: Int

Send parameters via ``application/x-www-form-urlencoded``:
* ``playerWhiteId=1&playerBlackId=2``

Send parameters via ``application/json``:
* ``{ "playerWhiteId": 1, "playerBlackId": 2 }``

#### POST */draws*
Parameters:
* matchId: Int
* drawCode: String (SAN -> <https://en.wikipedia.org/wiki/Algebraic_notation_%28chess%29>)
* startColumn: Int (optional)
* startRow: Int (optional)

Send parameters via ``application/x-www-form-urlencoded``:
* ``matchId=1&drawCode=a4&startColumn=1&startRow=2``

Send parameters via ``application/json``:
* ``{ "matchId": 1, "drawCode": "a4", "startColumn": 1, "startRow": 2 }``

|Column | as Int|
| :---: | :---: |
|a      | 1     |
|b      | 2     |
| ...   | ...   |
|g      | 7     |
|h      | 8     |

### Content Negotiation
The server offers three options to handle content negotiation:
1.  Suffix Strategy
    * ``http:localhost:8080/players.json`` 
    * ``http:localhost:8080/players.xml``
    
2. Parameter Strategy
    * ``http:localhost:8080/players?mediaType=json``
    * ``http:localhost:8080/players?mediaType=xml``
    
3. Accept Header Strategy
    * Accept Header: ``application/json``
    * Accept Header: ``application/xml``
    
> see also <http://www.baeldung.com/spring-mvc-content-negotiation-json-xml>


Client
------
Build Client:
``./gradlew :client:build``

### Run Client
All what you have to do is to copy the ``dist`` folder, which is created after run the build task, to your web server.

### Client Configuration
The client config ``configuration.json`` is located under ``src/main/resources/config``. Here you can overwrite
the default server domain, if you want to implement your own for example or if you want to use another server.
The default server domain is ``http://localhost:8080``, which is the same url than the server. If you chance the server 
port don't forgot to adjust the ``serverRootUrl`` in the client config.