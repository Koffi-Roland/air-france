# Spring Security Habile Demo application for Spring Boot

This is a sample spring boot application used for demo purpose.

It defines two fake roles and authorities and uses the simul habile proxy endpoint to work.

The spring profile `local` is required to start the application in local mode.

This application requires the simul habile proxy to run using the `config/demoSimulConfig.json`
configuration file from this module.

The main purpose of this application is to be a baseline for the integration tests with cucumber.


## Execute the application locally

### Build
Go to parent directory: `cd ..`

Compile the project: `mvn install -DskipTests`


### Start Habile proxy
You need to start habile simul proxy with the application configuration file:

Go to directory: `cd ../spring-security-habile-simul`

Just run following command: `mvn spring-boot:run -Dspring-boot.run.arguments=--config.simul=../spring-security-habile-demo-boot/config/demoSimulConfig.json`

This will start on port 8001 the Habile simul proxy.
You may check that the Habile simul proxy is running with following url:
http://localhost:8001/mock/authorities

You will see the list of user/password with associated profiles.


You can then check following URL:
http://localhost:8001/mock/me

You will be prompted with a basic authentication popup, pick one user from previous step and check that current logged user is returned.

To logout, just invoke the following URL:
http://localhost:8001/mock/logout

### Start this application
Go to subdirectory: `cd ..\spring-security-habile-demo-boot`

Just run following command: `mvn spring-boot:run -Dspring-boot.run.profiles=local`

You can then check following URL:
http://localhost:8001/me

You will be prompted with a login form page, pick one user from previous step and check that current logged user is returned and also its profiles.

You have public routes defined, you can check that without previous authentication you have access to the page http://localhost:8001/public/data without any authentication request.

It returns a page with a static string: **NoData**

You also have anonymous routes defined, you can check that without previous authentication you have access to the page http://localhost:8001/anonymous/custom-me without any authentication request.

The page displayed indicates that you are logged as **anonymous**.

### Websocket section
This demo application introduces a websocket part. The websocket part is secured, for connection, subscription and publication you need to be authenticated.

The endpoint to register is **/api/websocket** You need to provide a X-XSRF-TOKEN.

You can subscribe to **/topic/date** to receive messages. There is a GET HTTP endpoint available at **/api/askForAMessage** that will send a message to all connected clients.

You can send a message to the server to the **/hello** topic. The server will log your message. Please note that this endpoint has a higher security level, you need to be authenticated and also have **P_XXXXX_ADMIN** or **P_XXXXX_USER** role.

An angular demo application is available [here](https://bitbucket.devnet.klm.com/projects/JRAF/repos/demo-websockets-angular/browse)


