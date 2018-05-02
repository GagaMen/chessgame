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

Client
------
Build Client:
``./gradlew :client:build``