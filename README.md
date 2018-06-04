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
* [AI Server](#ai-server)
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

Or use docker compose:
* ``docker-compose build``
* ``docker-compose up -d``

Server
------
Build Server:
``./gradlew :server:build``

### Run Server
* with gradle: ``./gradlew :server:bootRun``
* with jar: ``java -jar server/build/libs/chessgame-server-1.0.0.jar`` (without static resources)
* with war: ``java -jar server/build/libs/chessgame-server-1.0.0.war`` (with static resources -> client module)
> You have to configure the chess ai server root url and run it separately, because the chess ai server isn't included
in the .jar or .war file. (see [AI Server](#ai-server)) 
#### with Docker
* ``docker-compose build`` (only the first time and after updates necessary)
  * If new updates only affected the chess server you can also rebuild with the following command: 
  ``docker-compose build chess-server`` for more performance.
* ``docker-compose up -d`` (use your docker ip with port ``8080`` by default)
  * If you want to start the server without the client you have to change the ``service.chess-server.build.dockerfile``
  config entry to ``dockerfile-chess-server``
* ``docker-compose down`` (shutdown the containers)
* ``docker-compose down -v`` (shutdown the containers and removing volumes)
> Note: The database will be persist by default over multiple starts of the server using docker. If you use 
``docker-compose down -v`` the database will be reseted.

#### Or Server only (without ai server)
* ``docker build -t chess-server -f dockerfile-chess-server .`` (without static resources)
* ``docker build -t chess-server -f dockerfile-chess-server-client .`` (with static resources)
* ``docker run --name chess-server -d -p 8080:8080 chess-server``
> Don't forgot to configure the ai server root url otherwise it isn't possible to play against the ai player.

### Server Configuration
The Server starts on port ``8080`` by default. If you want to change the port override the ``server.port`` config entry 
in the ``application.yml`` file, which is located under ``src/main/resources``. 
You have to rebuild the project if you overwrites the configuration.
> If you are using docker and you change the default spring port, you have to change also the ``services.chess-server.ports`` 
config entry in the ``docker-compose.yml`` file. For example if the new port is ``8000`` the ``services.chess-server.ports``
entry have to be ``8000:8080``.

### Entry Points
> ***``{id}`` is a placeholder for a number!***

|URI                         | Methode | Usage 
|----------------------------|---------|--------------------------------------------------------------------------------|
|/                           | GET     | Get static resources (client module). This only works if you run the .war file.|
|/api                        | GET     | Root entry point of the RESTApi                                                |
|/api/players                | GET     | Get a list of all registered players                                           |
|/api/players                | POST    | Create a player                                                                |
|/api/players/{id}           | GET     | Get a single player                                                            |
|/api/players/{id}           | DELETE  | Delete a single player                                                         |
|/api/players/{id}           | PATCH   | Update a single player                                                         |
|/api/matches                | GET     | Get a list of all registered matches                                           |
|/api/matches                | POST    | Create a match                                                                 |
|/api/matches/{id}           | GET     | Get a single match                                                             |
|/api/matches/{id}           | DELETE  | Delete a single match                                                          |
|/api/matches/{id}/draws     | GET     | Get a list of draws from a match                                               |
|/api/matches/{id}/pieceSets | GET     | Get the pieceSets from a match                                                 |
|/api/draws                  | GET     | Get a list of all registered draws                                             |
|/api/draws                  | POST    | Create a draw                                                                  |
|/api/draws/ai               | POST    | Create a draw from ai server                                                   |
|/api/draws/{id}             | GET     | Get a single draw                                                              |

#### POST */api/players*
Parameters:
* name: String
* password: String

Send parameters via ``application/x-www-form-urlencoded``:
* ``name=Test&password=123456``

Send parameters via ``application/json``:
* ``{ "name": "Test", "password": "123456" }``

#### PATCH */api/players/{id}*
Parameters:
* password: String

Send parameters via ``application/x-www-form-urlencoded``:
* ``password=123456``

Send parameters via ``application/json``:
* ``{ "password": "123456" }``

#### GET */api/matches* and */api/matches/{id}*
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

#### POST */api/matches*
Parameters:
* playerWhiteId: Int
* playerBlackId: Int

Send parameters via ``application/x-www-form-urlencoded``:
* ``playerWhiteId=1&playerBlackId=2``

Send parameters via ``application/json``:
* ``{ "playerWhiteId": 1, "playerBlackId": 2 }``

#### POST */api/draws*
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

#### POST */api/draws/ai*
Parameters:
* matchId Int

Send parameters via ``application/x-www-form-urlencoded``:
* ``matchId=1``

Send parameters via ``application/json``:
* ``{ "matchId": 1 }``

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

### Documentation
Visit the document page <https://gagamen.github.io/chessgame/>

Alternative you can build the javadoc with the following task: ``./gradlew :server:dokka``.
The files are generated in the ``doc/javadoc`` folder. 

AI Server
---------
If you want to start the AI server without docker on an different server for example, then clone the git project from
<https://github.com/ncksllvn/chess-api> and follow the instruction of this repository.
After setup the ai server you have to change the ``app.aiServerRootUrl`` config entry in the ``application.yml`` file, 
which is located under ``src/main/resources`` and rebuild the project.
> The default value of this config entry is ``http://chess-ai:5000`` which is intended for the docker setup

### Run Ai Server with Docker
* ``docker build -t chess-ai -f dockerfile-chess-ai .``
* ``docker run --name chess-ai -d -p 5000:5000 chess-ai``

After that you can request your default docker ip with ``docker-machine ip default`` and open that with the port ``5000``
in your browser. You can call ``docker stop chess-ai`` to stop and ``docker start chess-ai`` to restart the container.

Client
------
Build Client:
``./gradlew :client:build``

### Run Client
All what you have to do is to copy the ``dist`` folder, which is created after run the build task, to your web server.

#### with Docker
* ``docker build -t chess-client -f dockerfile-chess-client .``
* ``docker run --name chess-client -d -p 8080:80 chess-client``

After that you can request your default docker ip with ``docker-machine ip default`` and open that with the port ``8080``
in your browser. You can call ``docker stop chess-client`` to stop and ``docker start chess-client`` to restart the container.

> Don't forget to change the ``serverRootUrl`` if you ara using the client separately. (see [Client Configuration](#client-configuration))

If you pull updates you have to remove first the existing container with the ``docker rm chess-client`` command and then you 
have to repeat the first two commands above.

### Client Configuration
* ``{ "useWindowLocation": true, "serverRootUrl": "", pollingDelayTime: 5000 }``

The client config ``configuration.json`` is located under ``src/main/resources/config``. Here you can overwrite
the server domain, if you want to implement your own for example or if you want to use another server.
By default the server use the window location to set the server root url. If you want to override the server root url 
set the value ``useWindowLocation`` to ``false`` and the ``serverRootUrl`` to your own url. The ``pollingDelayTime`` 
config entry defines the delay of the polling task, which automatically starts if a match is started. This task listen for 
updates and re-render the view if any exists.