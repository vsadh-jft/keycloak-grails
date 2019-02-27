package blog.demo

import javassist.NotFoundException
import org.springframework.security.access.AccessDeniedException

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "500"(controller: "error", action: "error500")
        "500"(controller: "error", action: "error401",
                exception: AccessDeniedException)
        "404"(controller: "error", action: "error404")
        "500"(controller: "errors", action: "error403",
                exception: NotFoundException)
    }
}
