# CustomSocialMediaApp

**Maven based Java MVC Web Project containing REST APIs for functionalities drawing few similarities with LinkedIn; SQLite is used as a backend database which is attached with the project itself**   

# How to deploy  

For building an eclipse web project :  

Do run this command in a folder where CustomSocialWebApp is placed.

[cutompath]$ mvn eclipse:eclipse -Dwtpversion=2.0  


# How to run: Use a tool like POSTMAN or run in a browser to capture JSON based response

For example:


http://localhost:8080/CustomSocialMediaApp/API/app/v1.1/messages/users/1?messageContains=Renovat

http://localhost:8080/CustomSocialMediaApp/API/app/v1.1/followers/users/2

*contextPath = http://localhost:8080/CustomSocialMediaApp/API/*

# Following are the (SocialAppController.java) controller methods with their mappings for each individual functionality:  

**1)  public List<Message> findAllMessegesOfUserWithFolloweesByUserId**  
	
Retrieves list of messages or newsfeed posted by userId = 1 and its followees or people it is following.

Method	URL	Action:  GET  	

*contextPath + /app/v1.1/messages/users/1?messageContains=Renovat*  
  


**2)  public List<User> findAllFollowersByUserId**  	

Retrieves list of followers of userId =2

Method	URL	Action:  GET  	

*contextPath + /app/v1.1/followers/users/2*  
  

**3)  public List<User> findAllFolloweesByUserId**    	
Retrieves list of people, to whom userId =2 is following

Method	URL	Action:  GET  	

*contextPath + /app/v1.1/followees/users/2*    
  


**4)  public void unfollowAnotherPerson**    

userId = 4 unfollows another user whose followId = 1

Method URL Action:  GET  	

*contextPath + /app/v1.1/users/unfollow?userId=4&followeeId=1*    
  
  

**5)  public void followAnotherPerson**  	

userId = 4 starts following another user whose followId = 1

Method	URL	Action:  GET  	

*contextPath + /app/v1.1/users/follow?userId=4&followeeId=1*    
  


**6)  public int findShortestDistanceBetweenUsers**  
	
Find the minimum distance between userId = 3 and another user whose id = 6

Method	URL	Action:  GET  	

*contextPath + /app/v1.1/users/shortestdistance?sourceId=3&destinationId=6*  
  


Graph for followees of userID =3 :  

1 -> 4 -> 2

2 -> 3 -> 4

3 -> 1 -> 2

4 -> 5 -> 2

5 -> 6

6    
    
  
So shortest path is via:  

3 ->  1 -> 4 -> 5 -> 6

Each directly connected edge is 1 hop !

**As we covered 4 hops:  Hence shortest path distance = 4** 
  


# DB Type : SQLite   

need browser like :  SQLite DB Browser to view tables  

DB name : SocialAppDB.sql  


# Database Schema Diagram : SocialAppDB

<img width="1280" alt="screen shot 2018-02-28 at 2 06 29 am" src="https://user-images.githubusercontent.com/30219858/36781883-0ccabe06-1c2c-11e8-8df4-823585d04a08.png">


 

