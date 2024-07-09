Drone Managament System:
This is a Java backend project for Drone Managment. This provides the services for registering a drone, updating position of drone and get the current position of drone.

Technologies used:
Java 17
Maven
SpringBoot 3.3.1
Spring Tool Suite 4
H2 DB
Lombok
Junit,Mockito
Postman

Follow the below instructions to run and test the application in your local workspace.
1. Clone the repository from Master branch.
2. Import the project in local IDE.
3. Build it.
4. Run the application as Spring Boot app.
For Eclipse IDE, run the main class with @SpringBootApplication as Java Application.
5. Endpoints can be tested using Postman

API detals:
1. Register a Drone
POST localhost:8080/dronemanagement/
Request Json:
{
    "name": "testdrone2",
    "position": "2,2",
    "direction": "NORTH"
}
Success response : 201
{
    "name": "testdrone2",
    "position": {
        "x": 2,
        "y": 2
    },
    "direction": "NORTH"
}

2. Move a Drone
PUT localhost:8080/dronemanagement/
Request Json :
{
"name":"testdrone2",
"position":"3,8"
}
Success response : 200 and list of positions/directions from current to updated one.
[
   {
      "name":"testdrone2",
      "position":{
         "x":2,
         "y":2
      },
      "direction":"NORTH"
   },
   {
      "name":"testdrone2",
      "position":{
         "x":2,
         "y":8
      },
      "direction":"EAST"
   },
   {
      "name":"testdrone2",
      "position":{
         "x":3,
         "y":8
      },
      "direction":"SOUTH"
   }
]

4. Get Current Drone position
GET localhost:8080/dronemanagement/{name}
Success response : 200
{
    "name": "testdrone2",
    "position": {
        "x": 2,
        "y": 2
    },
    "direction": "NORTH"
}
