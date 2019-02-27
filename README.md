# keycloak
Demo for configuring Keycloak auth with grails rest app and react based frontend

# Prerequisite

- Key-cloack 4.8.3.Final  server installed
- Node/Npm
- Java (1.8)
- Grails 3.3.9
- Gradle
 
# Setup


1: Start keycloak (should be on 8180 port, [else need to modify keyclock.json in backend as well as frontend project])

2. Import  `realm-import.json` as realm import in keyclock

4. Start the grails application:
`cd blog-demo`

 You must need to modifies 2 properties inside `blog-demo/grails-app/conf/application.yml` under keycloak node
 current values are:

    admin: admin
    
    password: admin
    
 Set `admin` value is the value of your `administration login` of keyclock
 also `password` value is the value of your `administration password` of keyclock
 
 we need it to perform bootstraps users from grails app
 
Now run app :
    
`grails run-app`

5. Start the react application

`go to frontend dir`

`npm install`

`npm start`

5. Go to [localhost:3000](localhost:3000) and login using user/pass which are mentioned below.

login users:

|username | password   | group        |
|---------|------------|--------------|
|user     |  password  | users-group  |
|user2    |  password  | users-group  |
|admin    |  password  | admins-group |

groups:

### users-group -> can only see post
### admins-group -> can see post and add post

* open localhost:3000
* It will redirect you to keyclock, once you login from above credentials you will see react Ui where 2 tabs will appears
* 1st one is User and 2nd one is  Admin
* if login user is part of users-group and it will try to add blog using admin tab, when user will submit blog from model then its will get 401 (un authentication notification), admin 
tab is visible for users-group to demonstrate resource authentication(As users-group users will not have access for create blog resource)
* if you will login through user which is part of admins-group, can see the list of post and can add the post using admin tab
