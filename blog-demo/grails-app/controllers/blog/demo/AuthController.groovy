package blog.demo

// For supporting rest based auth with key-clock
class AuthController {
    static responseFormats = ['json']

    AuthService authService;

    def login(LoginCO loginCO) {
        String token = authService.getTokenForUser(loginCO);
        if(token) {
            return respond(["token" : token])
        } else {
            respond(["error" : "Invalid username/password", status: 400])
        }
    }


}
