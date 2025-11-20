package pg.eti.kask.jee.quickr.autentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "QuickrAPIRealm")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/QuickrAPI",
        callerQuery = "select password from users where login = ?",
        groupsQuery = "select role from users_roles where id = (select id from users where login = ?)"
)
public class AuthenticationConfig {
}
