package org.glygen.tablemaker.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.glygen.tablemaker.persistence.glycan.Glycan;
import org.glygen.tablemaker.persistence.glycan.UploadStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name="glycan_file_upload")
public class BatchUploadEntity {
	Long id;
	UploadStatus status;
	Date startDate;
	Date accessedDate;
	UserEntity user;
	String filename;
	String format;
	Integer duplicateCount=0;
	Integer existingCount=0;
	Collection<UploadErrorEntity> errors;
	Collection<Glycan> glycans;
	Collection<Glycan> existingGlycans = new ArrayList<>();
	
	@Id
	@GeneratedValue
	@Column(name="glycan_file_upload_id")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="status", nullable=false)
	public UploadStatus getStatus() {
		return status;
	}
	public void setStatus(UploadStatus status) {
		this.status = status;
	}
	
	
	@Column
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column
	@Temporal(TemporalType.DATE)
	public Date getAccessedDate() {
		return accessedDate;
	}
	public void setAccessedDate(Date accessedDate) {
		this.accessedDate = accessedDate;
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

    @OneToMany(mappedBy = "upload", cascade = CascadeType.ALL, orphanRemoval = true)
	public Collection<UploadErrorEntity> getErrors() {
		return errors;
	}

	public void setErrors(Collection<UploadErrorEntity> errors) {
		this.errors = errors;
		for (UploadErrorEntity err: errors) {
			err.setUpload(this);
		}
	}

	@Column
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@ManyToMany (mappedBy="uploadFiles")
	public Collection<Glycan> getGlycans() {
		return glycans;
	}

	public void setGlycans(Collection<Glycan> glycans) {
		this.glycans = glycans;
	}

	@Transient
	public Collection<Glycan> getExistingGlycans() {
		return existingGlycans;
	}

	public void setExistingGlycans(Collection<Glycan> existingGlycans) {
		this.existingGlycans = existingGlycans;
	}

	@Column
	public Integer getDuplicateCount() {
		return duplicateCount;
	}

	public void setDuplicateCount(Integer duplicateCount) {
		this.duplicateCount = duplicateCount;
	}

	@Column
	public Integer getExistingCount() {
		return existingCount;
	}

	public void setExistingCount(Integer existingCount) {
		this.existingCount = existingCount;
	}
}
