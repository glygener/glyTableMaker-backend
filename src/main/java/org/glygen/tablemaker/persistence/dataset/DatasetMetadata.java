package org.glygen.tablemaker.persistence.dataset;

import org.glygen.tablemaker.persistence.glycan.Datatype;
import org.glygen.tablemaker.persistence.table.GlycanColumns;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
public class DatasetMetadata {
	
	Long id;
	Datatype datatype;
	GlycanColumns glycanColumn;
	String value;
    String valueUri;
 	String valueId;
 	
 	DatasetVersion dataset;

 	@Id
    @GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = Datatype.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "datatypeid")
	public Datatype getDatatype() {
		return datatype;
	}

	public void setDatatype(Datatype datatype) {
		this.datatype = datatype;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="glycancolumn")
	public GlycanColumns getGlycanColumn() {
		return glycanColumn;
	}

	public void setGlycanColumn(GlycanColumns glycanColumn) {
		this.glycanColumn = glycanColumn;
	}

    @Column (name="value", columnDefinition="text")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(length=4000)
	public String getValueUri() {
		return valueUri;
	}

	public void setValueUri(String valueUri) {
		this.valueUri = valueUri;
	}

	@Column
	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	@ManyToOne(targetEntity = DatasetVersion.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "versionId", foreignKey = @ForeignKey(name = "FK_VERIFY_DATASET"))
    @XmlTransient  // so that from the metadata we should not go back to dataset - prevent cycles
	@JsonIgnore
	public DatasetVersion getDataset() {
		return dataset;
	}

	public void setDataset(DatasetVersion dataset) {
		this.dataset = dataset;
	}
 	
 	

}