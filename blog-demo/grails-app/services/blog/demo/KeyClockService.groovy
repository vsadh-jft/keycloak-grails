package blog.demo

import blog.demo.vo.UserVO
import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.GroupsResource
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.GroupRepresentation
import org.keycloak.representations.idm.UserRepresentation

import javax.ws.rs.core.Response

import static blog.demo.constent.AppConstant.*

@Transactional
@CompileStatic
class KeyClockService {

    GrailsApplication grailsApplication

    private String getClientId() {
        return grailsApplication.config.getProperty(CLIENT_ID)
    }

    private String getRealm() {
        return grailsApplication.config.getProperty(REALM)
    }

    private String getAdminPassword() {
        return grailsApplication.config.getProperty(ADMIN_PASSWORD)
    }

    private String getAdminUserName() {
        return grailsApplication.config.getProperty(ADMIN_USERNAME)
    }

    private String getAuthUrl() {
        return grailsApplication.config.getProperty(AUTH_URL)
    }

    private String getMasterRealm() {
        return grailsApplication.config.getProperty(MASTER_REALM)
    }

    private String getAdminCli() {
        return grailsApplication.config.getProperty(ADMIN_CLI)
    }


    // Create user in key-clock
    def createUser(UserVO userVO) {
            int statusId = 0;
            try {
                UsersResource userRessource = getUsersResource();
                UserRepresentation userRepresentation = new UserRepresentation();
                userRepresentation.setUsername(userVO.userName);
                userRepresentation.setEmail(userVO.email);
                userRepresentation.setFirstName(userVO.firstName);
                userRepresentation.setLastName(userVO.lastName);
                userRepresentation.setEnabled(true);
                Response result = userRessource.create(userRepresentation);
                statusId = result.getStatus();
                if (statusId == 201) {
                    String userId = result.getLocation().getPath().replaceAll('.*/([^/]+)$', '$1');
                    CredentialRepresentation passwordCred = new CredentialRepresentation();
                    passwordCred = new CredentialRepresentation();
                    passwordCred.setTemporary(false);
                    passwordCred.setType(CredentialRepresentation.PASSWORD);
                    passwordCred.setValue(userVO.getPassword())
                    userRessource.get(userId).resetPassword(passwordCred);
                    GroupsResource groupsResource = getGroupsResource()
                    List<GroupRepresentation> groupRepresentationList =  groupsResource.groups();
                    if(userVO.group) {
                        groupRepresentationList.each { it ->
                            if(it.name.equalsIgnoreCase(userVO.group)) {
                                println("Group id:  ${it.name} : ${it.id}")
                                userRessource.get(userId).joinGroup(it.id)
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return statusId;
    }

    private UsersResource getUsersResource() {
        Keycloak kc = getKeyCloak()
        RealmResource realmResource = kc.realm(getRealm());
        return realmResource.users();
    }

    private GroupsResource getGroupsResource() {
        Keycloak kc = getKeyCloak()
        RealmResource realmResource = kc.realm(getRealm());
        return realmResource.groups()
    }

    private Keycloak getKeyCloak() {
        return KeycloakBuilder.builder()
                .serverUrl(authUrl)
                .realm(getMasterRealm())
                .username(getAdminUserName())
                .password(getAdminPassword())
                .clientId(getAdminCli())
                .build()
    }

}
