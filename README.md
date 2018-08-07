# inr
## Developers notes - front end
### To set up the INR front-end...
- Ensure [Node.js](https://nodejs.org/en/) is installed and set-up on your machine
- Clone the [inr repository](https://github.com/gpit-futures/inr.git) using git clone (or your favourite GUI tool) using the URL https://github.com/gpit-futures/inr.git to an appropriate local directory.
- Open your chosen console (Node.js Command Prompt/Git-Bash/etc.).
- Navigate to {downloaded location/inr/ui} and run the following commands:
- - npm install
- - npm run dev - (To start the application locally.)
### Dependencies
- A working version of the [framework](https://github.com/gpit-futures/frame) to host the INR module
- A working version of the [core module](https://github.com/gpit-futures/pulse) in order to be able to select a patient context to be used by INR.
- A working [authentication server](https://github.com/gpit-futures/auth-server) to login to the framework.
- A working version of the message server for inter module communication. 
## Developer notes - back end
### To set up the INR server in a Java IDE...
- Ensure [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html), [Git](https://git-scm.com/downloads) and [Maven](https://maven.apache.org/download.cgi) are installed and set-up on your machine (including your Java IDE of choice e.g. Eclipse)
 - Clone the [inr repository](https://github.com/gpit-futures/inr.git) using git clone (or your favourite GUI tool) using the URL https://github.com/gpit-futures/inr.git to an appropriate local directory.
 - Import the project into your IDE as a Maven project.  The pom.xml file containing the build configuration required for import can be found in the local directory created above.
### To run via the Java IDE
 - The main class is com.answerdigital.irn.Application.  Simply run this class as a standard Java application.
 ### To run via an executable JAR file
 - In a console, Navigate to the root directory of the inr project as defined above (the directory containing the pom.xml file).
 - Build the project using the 'mvn clean install' command.
 - The JAR file to execute can be found in the /target directory.
### Dependencies
- A working version of the [framework](https://github.com/gpit-futures/frame) to host the INR module
- A working version of the [core module](https://github.com/gpit-futures/pulse) in order to be able to select a patient context to be used by INR.
- A working [authentication server](https://github.com/gpit-futures/auth-server) to login to the framework.
- A working version of the message server for inter module communication. 
### Configuration
All configuration for the application can be found in... **{root_directory}/src/main/resources/application.properties**
Below is a description of each properties function...

#### application.name
The display name of this module (Temporary. On integration with the buying catalogue this data will be available within the JWT)
#### server.servlet.contextPath
The application context following the host/port in the URL.  In this case it is /inr
#### server.port
The port on which the server is listening for connections.
#### security.basic.enabled
Whether Spring basic security is enabled.  This should always be set to false as authentication is handled by single-sign-on.
#### spring.rabbitmq.host
The host URL of the message server.
#### spring.rabbitmq.port
The port on which the message queue is listening for connections.
#### spring.rabbitmq.username
The username for authenticating access to the message queue.
#### spring.rabbitmq.password
The password for authenticating access to the message queue.
#### *.namespace
The namespace of each unique entity in case identifiers are common between them.
#### *.created.key
The routing key (to the message queue) for entity creation events.
#### *.updated.key
The routing key (to the message queue) for entity update events.
#### *.exchange.key
The exchange key (to identify the message queue exchange of interest) to bind to before messages can be published or consumed.
## Installation notes
To install the INR module directly simply download the latest version of the [framework](https://github.com/gpit-futures/frame/releases) from Git Hub and login as either user.  
