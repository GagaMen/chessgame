RESTful Chessgame
=================
This project has two modules which are developed with Kotlin that can be build using gradle. 
The client has JavaScript and the server has JVM as compile target.

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

|URI                   | Methode | Usage 
|----------------------|---------|--------------------------------------------------------------------------------|
|/                     | GET     | Get static resources (client module). This only works if you run the .war file.|
|/player               | GET     | Get a list of all registered players                                           |
|/player               | POST    | Create a player                                                                |
|/player/{id}          | GET     | Get a single player                                                            |
|/player/{id}          | DELETE  | Delete a single player                                                         |
|/player/{id}          | PATCH   | Update a single player                                                         |
|/match                | GET     | Get a list of all registered matches                                           |
|/match                | POST    | Create a match                                                                 |
|/match/{id}           | GET     | Get a single match                                                             |
|/match/{id}           | DELETE  | Delete a single match                                                          |
|/draw                 | GET     | Get a list of all registered draws                                             |
|/draw                 | POST    | Create a draw                                                                  |
|/draw/{id}            | GET     | Get a single draw                                                              |
|/match/{id}/draw      | GET     | Get a list of draws from a match                                               |
|/match/{id}/pieceSets | GET     | Get the pieceSets from a match                                                 |

#### POST */player*
Parameters:
* name: String
* password: String

Send parameters via ``application/x-www-form-urlencoded``:
> ``name=Test&password=123456``

Send parameters via ``application/json``:
> ``{ "name": "Test", "password": "123456" }``

#### PATCH */player/{id}*
Parameters:
* password: String

Send parameters via ``application/x-www-form-urlencoded``:
> ``password=123456``

Send parameters via ``application/json``:
> ``{ "password": "123456" }``

### GET */match* and */match/{id}*
Parameters:
* includePieceSets (optional: default value is true)
> If true match or matches contains all information about pieceSets
* includeHistory (optional: default value is true)
> If true match or matches contains all draws

You can request this information later via GET request to URI ``/match/{id}/pieceSets`` and ``/match/{id}/draw``.

Send parameters via URL:
> ``http://localhost:8080/match?includePieceSets=false``\
> ``http://localhost:8080/match/555555?includeHistory=false``\
> ``http://localhost:8080/match?includePieceSets=false&includeHistory=false``

#### POST */match*
Parameters:
* playerWhiteId: Int
* playerBlackId: Int

Send parameters via ``application/x-www-form-urlencoded``:
> ``playerWhiteId=1&playerBlackId=2``

Send parameters via ``application/json``:
> ``{ "playerWhiteId": 1, "playerBlackId": 2 }``

#### POST */draw*
Parameters:
* matchId: Int
* drawCode: String (SAN -> <https://en.wikipedia.org/wiki/Algebraic_notation_%28chess%29>)
* startColumn: Int (optional)
* startRow: Int (optional)

Send parameters via ``application/x-www-form-urlencoded``:
> ``matchId=1&drawCode=a4&startColumn=1&startRow=2``

Send parameters via ``application/json``:
> ``{ "matchId": 1, "drawCode": "a4", "startColumn": 1, "startRow": 2 }``

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
    > ``http:localhost:8080/player.json`` 
    
    > ``http:localhost:8080/player.xml``
    
2. Parameter Strategy
    > ``http:localhost:8080/player?mediaType=json``
    
    > ``http:localhost:8080/player?mediaType=xml``
    
3. Accept Header Strategy
    > Accept Header: ``application/json``
    
    > Accept Header: ``application/xml``
    
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