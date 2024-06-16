package com.afklm.spring.security.habile.properties;

import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class HttpVerbsAuthorities {

    private static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    /**
     * List of {@link HttpMethod} stating the http verbs for which access will be granted 
     * for the list of defined authorities, specified as either Habile profiles (and mapped
     * as Spring Security roles) or more specific permissions (mapped as generic Spring 
     * Security authorities).
     * All other {@link HttpMethod} that are not on this list will have their access denied.
     */
    private List<HttpMethod> verbs;
    /**
     * List of Habile profiles (later mapped as Spring Security roles) granting access to the
     * specific http verbs defined. Must start with <b>P&#95;</b>
     */
    private List<String> roles;

    /**
     * List of permissions (mapped as Spring Security authorities) granting access to the 
     * specific http verbs defined.
     */
    private List<String> permissions;

    public List<HttpMethod> getVerbs() {
        return verbs;
    }

    public void setVerbs(List<HttpMethod> verbs) {
        this.verbs = verbs;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public static String[] mergeRolesAndPermissions(List<String> roles, List<String> permissions) {
        return Stream.concat(
                Optional.ofNullable(roles)
                    .map(list -> list.stream())
                    .orElse(Stream.empty())
                    .map(role -> DEFAULT_ROLE_PREFIX + role),
                Optional.ofNullable(permissions)
                    .map(list -> list.stream())
                    .orElse(Stream.empty()))
            .distinct()
            .toArray(String[]::new);
    }

    public static String getRolesAndOrPermissionsListing(List<String> roles, List<String> permissions) {
        final boolean hasRoles = !CollectionUtils.isEmpty(roles);
        final boolean hasPermissions = !CollectionUtils.isEmpty(permissions);
        StringBuilder builder = new StringBuilder();
        if (hasRoles) {
            builder.append("roles ").append('\'').append(roles).append('\'');
            if (hasPermissions) {
                builder.append(" or ");
            }
        }
        if (hasPermissions) {
            builder.append("permissions ").append('\'').append(permissions).append('\'');
        }
        return builder.toString();
    }
}
