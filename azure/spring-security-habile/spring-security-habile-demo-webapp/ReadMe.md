# Spring Security Habile Demo application for Spring

This is a sample spring application used for demo purpose.
It is packaged as a WAR and should be run within a Tomcat container.

It defines two fake roles and authorities and uses the simul habile proxy endpoint to work.

This application requires the simul habile proxy to run using the `config/demoSimulConfig.json`
configuration file from this module.

##Note
This project does use spring boot runtime jars since some annotation are spring boot dependant.

## Execute the application locally

### Start Habile proxy
You need to start habile simul proxy with the application configuration file.

Go to directory: `cd ../spring-security-habile-simul`

Just run following command: `mvn spring-boot:run -Dspring-boot.run.arguments=--config.simul=../spring-security-habile-demo-webapp/config/demoSimulConfig.json`
 
This will start on port 8001 the Habile simul proxy.
You may check that the Habile simul proxy is running with following url:
http://localhost:8001/mock/authorities

You will see the list of user/password with associated profiles.


You can then check following URL:
http://localhost:8001/mock/me

You will be prompted with a basic authentication popup, pick one user from previous step and check that current logged user is returned.

To logout, just invoke the following URL:
http://localhost:8001/mock/logout

## Start Demo
Go to subdirectory: `cd ..\spring-security-habile-demo-webapp`

Deploy the WAR file in your tomcat on port 8080.

You can then check following url (considering the WAR file is deployed at the root context of tomcat):
http://localhost:8001/me

You will be prompted with a basic authentication popup, pick one user from previous step and check that current logged user is returned and also its profiles.
