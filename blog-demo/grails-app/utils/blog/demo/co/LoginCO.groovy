package blog.demo.co

import grails.validation.Validateable

class LoginCO implements Validateable {
    String userName;
    String password;


    static constraints = {
        userName blank: false
        password blank: false
    }
}
