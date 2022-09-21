# Gecko

Gecko is a user friendly applications for EV owners to help them consistently
create trips and plan these around charging stations.

## Authors
- Philip Dolbel
- Tara Lipscombe 
- Corentin Manti
- Lucas Redding
- Joshua Brown
- Phyu Wai Lwin

## Prerequisites
- JDK >= 17 [click here to get the latest stable OpenJDK release (as of writing this README)](https://jdk.java.net/18/)
- Maven [Download](https://maven.apache.org/download.cgi) and [Install](https://maven.apache.org/install.html)

## Importing project from VCS (Using IntelliJ)
IntelliJ has built-in support for importing projects directly from Version Control Systems (VCS) like GitLab.
To download and import your project:

- Launch IntelliJ and chose `Get from VCS` from the start-up window.
- Input the url of the project`https://eng-git.canterbury.ac.nz/seng202-2022/team-6`


## Importing Project from a folder (Using IntelliJ)
IntelliJ has built-in support for Maven (although if you turned the Maven plugin off when you were setting it up
you’ll need to re-enable it). To import your project:

- Launch IntelliJ and choose `Open` from the start-up window.
- Select the project folder.
- Select `Import project from external model` and make sure that Maven is highlighted.

**Note:** *If you run into dependency issues when running the app or the Maven pop up doesn't appear then open the Maven sidebar and click the Refresh icon labeled 'Reimport All Maven Projects'.*

## Build Project
1. Open a command line interface inside the project directory and run `mvn clean package` to build a .jar file. The file is located at `target/revolt-1.0.jar`

## Run App
- If you haven't already, Build the project.
- Open a command line interface inside the project directory and run `cd target` to change into the target directory.
- Run the command `java -jar revolt-1.0.jar`
- On the first run, you will need to import data from a csv file. An example has been provided.

## Finally...

Have fun :))

&copy; SENG202 Team 6 2022