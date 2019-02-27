package blog.demo


import grails.rest.*
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional

// Curd on Blog domain, user with role Admin can perform curd, user with role User can only fetch blog list
@Transactional
class BlogController extends RestfulController<Blog>{

    static responseFormats = ['json']

    BlogController() {
        super(Blog)
    }

    @PreAuthorize("hasAuthority('admin')")
    def save() {
        super.save()
    }

    @PreAuthorize("hasAuthority('admin')")
    def update() {
        super.update()
    }

    @PreAuthorize("hasAuthority('admin')")
    def delete() {
        super.delete()
    }
}