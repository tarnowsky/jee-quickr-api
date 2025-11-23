package pg.eti.kask.jee.quickr.autentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@ApplicationScoped
@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(loginPage = "/authentication/custom/login.xhtml", errorPage = "/authentication/custom/login_error.xhtml"))
@DatabaseIdentityStoreDefinition(dataSourceLookup = "jdbc/QuickrAPI", callerQuery = "select password from users where login = ?", groupsQuery = "select role from users_roles where id = (select id from users where login = ?)")
public class AuthenticationConfig {
}
