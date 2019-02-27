package blog.demo

// Handling unmapped error
class ErrorController {
    static responseFormats = ['json']

    def error500() {
        respond(["error" : "Internal server error"],status: 500)
    }

    def error401() {
        respond(["error" : "Unauthorized"],status: 401)
    }

    def error403() {
        respond(["error" : "Forbidden"],status: 403)
    }

    def error404() {
        respond(["error" : "Not found"],status: 404)
    }
}
