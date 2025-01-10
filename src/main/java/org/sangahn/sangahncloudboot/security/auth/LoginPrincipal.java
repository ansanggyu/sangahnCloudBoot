package org.sangahn.sangahncloudboot.security.auth;

import java.security.Principal;

public class LoginPrincipal implements Principal {

    private final String userId;

    public LoginPrincipal(final String userId) { this.userId = userId; }

    @Override
    public String getName() { return userId; }
}
