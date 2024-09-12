package org.glygen.tablemaker.persistence.dataset;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
public class DatasetVersion {
	
	Long versionId;
	String version;
	Date versionDate;
	String comment;
	License license;
	Boolean head;
	
	Collection<DatasetMetadata> data;
	Collection<DatasetError> errors;
	Dataset dataset;
	
	@Id
	@GeneratedValue
	public Long getVersionId() {
		return versionId;
	}
	public void setVersionId(Long id) {
		this.versionId = id;
	}
	
	@Column
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(name="versiondate")
    @Temporal(TemporalType.DATE)
	public Date getVersionDate() {
		return versionDate;
	}
	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}
	
	@Column(length=4000)
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@ManyToOne(targetEntity = License.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "id", foreignKey = @ForeignKey(name = "FK_VERIFY_LICENSE"))
	public License getLicense() {
		return license;
	}
	public void setLicense(License license) {
		this.license = license;
	}
	
	@ManyToOne(targetEntity = Dataset.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "datasetId", foreignKey = @ForeignKey(name = "FK_VERIFY_DATASET"))
    @XmlTransient  // so that from the version we should not go back to dataset - prevent cycles
	@JsonIgnore
	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	
	@OneToMany(mappedBy = "dataset", cascade=CascadeType.ALL, orphanRemoval = true)
	public Collection<DatasetMetadata> getData() {
		return data;
	}
	public void setData(Collection<DatasetMetadata> data) {
		this.data = data;
	}
	
	@OneToMany(mappedBy = "dataset", cascade=CascadeType.ALL, orphanRemoval = true)
	public Collection<DatasetError> getErrors() {
		return errors;
	}
	public void setErrors(Collection<DatasetError> errors) {
		this.errors = errors;
	}

	@Column
	public Boolean getHead() {
		return head;
	}
	
	public void setHead(Boolean head) {
		this.head = head;
	}
}