package org.glygen.tablemaker.persistence;

import java.util.Collection;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="users")
@XmlRootElement (name="user")
@JsonSerialize
@JsonIgnoreProperties({"password"})
public class UserEntity implements Comparable<UserEntity>{

	private Long userId;
	private String username;
	private String password;
	private Boolean enabled;
	private String firstName;
    private String lastName;
    private String email;
    private String groupName;
    private String department;
    private String affiliation;
    private String affiliationWebsite;
    private Boolean tempPassword;
    private UserLoginType loginType;
    private Collection<RoleEntity> roles;
    
    @Id
    @Column(name="userid", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_seq")
    @SequenceGenerator(name="user_seq", sequenceName="USER_SEQ", initialValue=10, allocationSize=50)
    public Long getUserId() {
		return userId;
	}

    public void setUserId(Long userId) {
		this.userId = userId;
	}
    
	/**
	 * @return the username
	 */
    @Column(name="username", unique = true, nullable = false, length = 255)
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	@Column(name="password", length=255)
	@JsonIgnore
	@XmlTransient
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the enabled
	 */
	@Column(name="enabled")
	public Boolean getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the firstName
	 */
	@Column(name="firstname")
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	@Column(name="lastname")
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	@Column(name="email", unique=true, nullable=false)
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the affiliation
	 */
	@Column(name="affiliation")
	public String getAffiliation() {
		return affiliation;
	}
	/**
	 * @param affiliation the affiliation to set
	 */
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	/**
	 * @return the affiliationWebsite
	 */
	@Column(name="affiliationwebsite")
	public String getAffiliationWebsite() {
		return affiliationWebsite;
	}
	/**
	 * @param affiliationWebsite the affiliationWebsite to set
	 */
	public void setAffiliationWebsite(String affiliationWebsite) {
		this.affiliationWebsite = affiliationWebsite;
	}
	/**
	 * @return the tempPassword
	 */
	@Column(name="tempPassword")
	public Boolean getTempPassword() {
		return tempPassword;
	}
	/**
	 * @param tempPassword tempPassword flag to set
	 */
	public void setTempPassword(Boolean t) {
		this.tempPassword = t;
	}
    
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinTable(name = "user_roles", joinColumns = { 
			@JoinColumn(name = "userid", nullable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "roleid", 
					nullable = false) })
    public Collection<RoleEntity> getRoles() {
		return roles;
	}
    
	public void setRoles(Collection<RoleEntity> roles) {
		this.roles = roles;
	} 
	
	public boolean hasRole (String roleName) {
	    if (roles != null) {
    	    for (RoleEntity role: roles) {
    	        if (role.getRoleName().equalsIgnoreCase(roleName))
    	            return true;
    	    }
    	    
	    } 
	    return false;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="logintype")
	public UserLoginType getLoginType() {
		return loginType;
	}
	
	public void setLoginType(UserLoginType loginType) {
		this.loginType = loginType;
	}

    /**
     * @return the groupName
     */
	@Column(name="groupname")
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the department
     */
    @Column(name="department")
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

	@Override
	public int compareTo(UserEntity o) {
		return username.compareTo(o.username);
	}
}
