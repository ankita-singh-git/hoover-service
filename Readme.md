## Steps to run the service 
### Run on docker container
1. `docker build -t hoover-service:1.0 .` -- to create an image using the jar in target folder and tag it as hoover-service:1.0
2. `docker run -p 8080:8080 hoover-service:1.0` -- to run the image on port 8080

### Run using maven and spring boot commands
mvn spring-boot:run

The swagger ui can be found here- http://localhost:8080/swagger-ui/#

## What this service does?

This service navigates a hoover through a room based on: 

1. room dimensions as X and Y coordinates, identifying the top right corner of the room rectangle. This room is split up in a grid based on these dimensions; a room that has dimensions X: 5 and Y: 5 has 5 columns and 5 rows, so 25 possible hoover positions. The bottom left corner(X:0, Y:0) is the point of origin for our coordinate system. 

2. locations of patches of dirt, also defined by X and Y coordinates identifying the bottom left corner of those grid positions. 

3. an initial hoover position (X and Y coordinates) 

4. driving instructions (as cardinal directions where e.g. N - "go north", E - "go east", S - "go south", W - "go west") 
 
###Sample json input: 
{ 
  "roomSize" : {
		"x": 5,
		"y": 5
	}, 
  "coords" : {
		"x": 1,
		"y": 2
	}, 
  "patches" : [ 
    {
		"x": 1,
		"y": 0
	}, 
    {
		"x": 2,
		"y": 2
	}, 
    {
		"x": 2,
		"y": 3
	}
  ], 
  "instructions" : "NNESEESWNWW" 
}

roomSize - room dimension 
coords -  robot start position(coordinates) 
patches - describe location(coordinates) of dirts 
instructions  - driving instruction 
 

###Sample json output 
{
	"coords": {
		"x": 1,
		"y": 3
	},
	"patches": 1
}

coords  - final robot position 
patches - number of cleaned patches 

##Assumptions
 
1. The room is rectangular, has no obstacles except the room walls. All locations in the room will be clean except for the locations of the patches of dirt presented in the program input. 

2. Placing the hoover on a patch of dirt removes the patch of dirt so that patch is then clean for the remainder of the program run.  