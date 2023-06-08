## RestGlassfishHelloWorld

The purpose of this project is to provide a starting point when using Jakarta on a glassfish server.

It will have the minimal code to achieve an end-to-end functionality.

1. Run the `docker-compose.yml` to run the Glassfish server and other dependencies.
2. Run `mvn clean install` and copy `RestGlassfishHelloWorld-1.0-SNAPSHOT.war` into the `deployments` folder. 
3. You may need to delete all other files and folders such as `.autodeploystatus`, `.gitkeep_deployFailed` and `RestGlassfishHelloWorld-1.0-SNAPSHOT.war_deployed)`. 
4. Autodeployment should run in glassfish server container.
5. App: http://localhost:8080/RestGlassfishHelloWorld-1.0-SNAPSHOT/api/hello-world/aws
6. Admin: http://localhost:4848/