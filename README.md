# Student Practicum Automatic Estimation
## Business case
Speaking about the evaluation of knowledge by independent experts, we can say that the human factor takes a lot of time for the estimation. It promotes unfair competition among students, because from the teacher's point of view, it is more typical to receive a higher grade for an excellent student than the others.
Automation of knowledge verification will not only save a lot of time, but also eliminate subjective evaluation. The teacher can easily adjust the evaluation criteria for different groups of students.
## Security scanning status
[![CodeQL](https://github.com/kolesroma/student-practicum-automatic-estimation/actions/workflows/codeql.yml/badge.svg)](https://github.com/kolesroma/student-practicum-automatic-estimation/actions/workflows/codeql.yml)
## Technical details
This is a full-stack application that uses Java 11 and Spring Boot framework for web-application development with MVC approach.
## How to run the application locally?
### Initial set up
Download and setup java on your PC:
- check it by running `java --version` command in cmd
### Run steps
1. Download package from this repository
2. Go to the folder where you downloaded .jar archive
3. Open cmd and execute `java -jar {archive_name}.jar`
4. Your application should deploy on 8080 port of localhost
5. Open your browser and go to http://localhost:8080/home
### Notes
See [How to Install JDK in Windows 11](https://makeuseof.com/windows-11-install-java-jdk/) for more details.
## Useful links
- [Deployed application](https://practicum-estimation.herokuapp.com/)
## Database
### General description
In database is used flyway migration approach.
### Persistence entities relations 
![Entity Diagram](https://github.com/kolesroma/student-practicum-automatic-estimation/blob/main/docs/entity-diagram.png)
