package org.glygen.tablemaker.persistence;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class GlygenUser extends User {

	private static final long serialVersionUID = -3531439484732724601L;

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String affiliation;
    private final String affiliationWebsite;
    private final Boolean tempPassword;

    public GlygenUser(String username, String password, boolean enabled,
        boolean accountNonExpired, boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities,
        String firstName, String lastName, String email, String affiliation,
        String affiliationWebsite, Boolean tempP) {

            super(username, password, enabled, accountNonExpired,
               credentialsNonExpired, accountNonLocked, authorities);

            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.affiliation = affiliation;
            this.affiliationWebsite = affiliationWebsite;
            this.tempPassword = tempP;
    }

    public static long getSerialversionuid() {
       return serialVersionUID;
    }

    public String getFirstName() {
       return firstName;
    }

    public String getLastName() {
       return lastName;
    }
    
    public String getAffiliation() {
		return affiliation;
	}
    
    public String getAffiliationWebsite() {
		return affiliationWebsite;
	}
    
    public Boolean getTempPassword() {
		return tempPassword;
	}
    
    public String getEmail() {
		return email;
	}
}
