# Code Challenge
# Decisions
1. The project has built using Java 11 and Spring Boot
2. Postman has been used for mock HTTP requests.
3. A object-oriented programming approach was implemented.
# Assumptions
1. For the national registry identification external service it was assumed that it's a get request with a personal identification number as path param.
2. For the judicial records external service it was assumed that it's a get request with a personal identification number as path param.
3. For converting a lead into a prospect it was assumed that it's a post request that moves the sales lead stage into the prospect stage of the sales pipeline. The body request for this service is the next:
```json
{
  "score": 100,
  "convertToProspect": true
}
```
4. For the prospect qualification internal service it was assumed that it's a post request with a personal identification number as path param and with the next body request:
```json
{
    "personValidation": true,
    "judicialvalidation": true
}
```

# Excute 

To compile the project please run:
```console
gradle clean build
```
To execute the project please run:
```console
gradle bootRun -Pargs=--person.pin=2532903920502,--test.run=false  
```
To run test cases for the project, please run:
```console
gradle bootRun -Pargs=--person.pin=test,--test.run=true 
```
# Additional information
1. Diagrams on demo/diagrams folder.
