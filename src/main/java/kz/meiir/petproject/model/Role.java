package kz.meiir.petproject.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
