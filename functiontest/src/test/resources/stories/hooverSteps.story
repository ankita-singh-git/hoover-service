Meta:

Narrative:
As a
	service user
I want to
	navigate hoover by providing start coords, roomsize and dirt patches
So that
	the dirt patches are removed and I get a count of number of patches removed and final hoover position
	
Scenario : Navigate and clean dirt patches
Given a request to clean patches
When a navigate and clean request is recieved
Then a response with final hoover position and number of patches cleaned in returned