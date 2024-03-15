package org.glygen.tablemaker.persistence.glycan;

import java.util.ArrayList;
import java.util.Date;

import org.glygen.tablemaker.persistence.BatchUploadEntity;
import org.glygen.tablemaker.persistence.UserEntity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="glycans")
@XmlRootElement (name="glycan")
@JsonSerialize
public class Glycan {
    Long glycanId;
    String glytoucanID;
    String wurcs;
    String glycoCT;
    String gws;
    String glytoucanHash;
    String error;
    Double mass;
    Date dateCreated;
    RegistrationStatus status;
    UserEntity user;
    java.util.Collection<GlycanInCollection> glycanCollections;
    java.util.Collection<GlycanTag> tags;
    byte[] cartoon;
    java.util.Collection<BatchUploadEntity> uploadFiles;
    
    /**
     * @return the glycanId
     */
    @Id
    @Column(name="glycanid", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="glycan_seq")
    @SequenceGenerator(name="glycan_seq", sequenceName="GLYCAN_SEQ", initialValue=1)
    public Long getGlycanId() {
        return glycanId;
    }
    /**
     * @param glycanId the glycanId to set
     */
    public void setGlycanId(Long glycanId) {
        this.glycanId = glycanId;
    }
    
    /**
     * @return the glytoucanID
     */
    @Column(name="glytoucanid", unique = false, nullable = true, length = 8)
    public String getGlytoucanID() {
        return glytoucanID;
    }
    /**
     * @param glytoucanID the glytoucanID to set
     */
    public void setGlytoucanID(String glytoucanID) {
        this.glytoucanID = glytoucanID;
    }
    /**
     * @return the wurcs
     */
    @Column(name="wurcs", columnDefinition="text")
    public String getWurcs() {
        return wurcs;
    }
    /**
     * @param wurcs the wurcs to set
     */
    public void setWurcs(String wurcs) {
        this.wurcs = wurcs;
    }
    /**
     * @return the glycoCT
     */
    @Column(name="glycoct", columnDefinition="text")
    public String getGlycoCT() {
        return glycoCT;
    }
    /**
     * @param glycoCT the glycoCT to set
     */
    public void setGlycoCT(String glycoCT) {
        this.glycoCT = glycoCT;
    }
    /**
     * @return the gws
     */
    @Column(name="gws", columnDefinition="text")
    public String getGws() {
        return gws;
    }
    /**
     * @param gws the gws to set
     */
    public void setGws(String gws) {
        this.gws = gws;
    }
    /**
     * @return the mass
     */
    @Column(name="mass")
    public Double getMass() {
        return mass;
    }
    /**
     * @param mass the mass to set
     */
    public void setMass(Double mass) {
        this.mass = mass;
    }
    /**
     * @return the dateCreated
     */
    @Column(name="datecreated")
    @Temporal(TemporalType.DATE)
    public Date getDateCreated() {
        return dateCreated;
    }
    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    /**
     * @return the status
     */
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    public RegistrationStatus getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }
    /**
     * @return the user
     */
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userid", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
    public UserEntity getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }
    /**
     * @return the glytoucanHash
     */
    @Column(name="glytoucanhash")
    public String getGlytoucanHash() {
        return glytoucanHash;
    }
    /**
     * @param glytoucanHash the glytoucanHash to set
     */
    public void setGlytoucanHash(String glytoucanHash) {
        this.glytoucanHash = glytoucanHash;
    }
    /**
     * @return the error
     */
    @Column(name="error", columnDefinition="text")
    public String getError() {
        return error;
    }
    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }
    /**
     * @return the glycanCollections
     */
    @OneToMany(mappedBy = "glycan")
    public java.util.Collection<GlycanInCollection> getGlycanCollections() {
        return glycanCollections;
    }
    /**
     * @param glycanCollections the glycanCollections to set
     */
    public void setGlycanCollections(java.util.Collection<GlycanInCollection> glycanCollections) {
        this.glycanCollections = glycanCollections;
    }
    /**
     * @return the cartoon
     */
    @Transient
    public byte[] getCartoon() {
        return cartoon;
    }
    /**
     * @param cartoon the cartoon to set
     */
    public void setCartoon(byte[] cartoon) {
        this.cartoon = cartoon;
    }
    
    @ManyToMany
	public java.util.Collection<GlycanTag> getTags() {
		return tags;
	}
	public void setTags(java.util.Collection<GlycanTag> tags) {
		this.tags = tags;
	}
	
	@ManyToMany
	public java.util.Collection<BatchUploadEntity> getUploadFiles() {
		return uploadFiles;
	}
	public void setUploadFiles(java.util.Collection<BatchUploadEntity> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}
	
	public void addUploadFile (BatchUploadEntity upload) {
		if (uploadFiles == null) 
			uploadFiles = new ArrayList<BatchUploadEntity>();
		uploadFiles.add(upload);
	}
}
