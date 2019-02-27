package blog.demo

class Blog {
    String title;
    String body;

    static constraints = {
        title blank: false
        body blank: true
    }
}
