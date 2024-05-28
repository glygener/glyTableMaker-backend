package org.glygen.tablemaker.persistence.table;

import java.util.List;

import org.glygen.tablemaker.persistence.glycan.Collection;

public class TableView {
	
	List<Collection> collections;
	List<TableColumn> colums;
	FileFormat fileFormat;
	String filename;
	
	public List<Collection> getCollections() {
		return collections;
	}
	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}
	public List<TableColumn> getColums() {
		return colums;
	}
	public void setColums(List<TableColumn> colums) {
		this.colums = colums;
	}
	public FileFormat getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	

}
