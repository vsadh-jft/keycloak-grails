package blog.demo

import blog.demo.vo.UserVO

class BootStrap {

    KeyClockService keyClockService;

    def init = { servletContext ->
        // Bootstrap users
        List<UserVO> users = new ArrayList<>()
        users.add(new UserVO(userName: "user", firstName: "john",lastName: "User Doe", email: "user@localhost.com", group: "users-group", password:"password" ));
        users.add(new UserVO(userName: "admin", firstName: "Harry",lastName: "Admin Doe", email: "admin@localhost.com", group: "admins-group", password:"password" ));
        users.add(new UserVO(userName: "user2", firstName: "Bin",lastName: "Doe", email: "bin@user.com", group: "users-group", password:"password" ));

        users.each { it ->
            int status = keyClockService.createUser(it)
            if(status == 201) {
                log.info("user $it.userName successfully created in key-cloak");
            }
            else if (status == 409) {
                log.info("user $it.userName already exist in key-cloak");
            } else {
                log.info("user $it.userName unable to create user in key-cloak, status id : $status, please trace error log");
            }
        }

        //BootsStrap Blogs
        if(Blog.count == 0) {
            new Blog(title: "PUT", body: "PUT is idempotent, so you can cache the response.").save()
            new Blog(title: "HATEOAS", body: "it is a constraint of the REST application architecture that keeps the RESTful style architecture unique from most other network application architectures.").save()
        }
    }
    def destroy = {
    }
}
