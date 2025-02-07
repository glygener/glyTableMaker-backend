package org.glygen.tablemaker.view;

import java.util.List;

import org.glygen.tablemaker.persistence.dataset.DatasetGlycoproteinMetadata;

public class GlygenProteinMetadataRow {
	String rowId;
	List<DatasetGlycoproteinMetadata> columns;
	byte[] cartoon;
	String byonicString;
	String condensedString;
	
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public List<DatasetGlycoproteinMetadata> getColumns() {
		return columns;
	}
	public void setColumns(List<DatasetGlycoproteinMetadata> columns) {
		this.columns = columns;
	}
	
	public byte[] getCartoon() {
		return cartoon;
	}
	
	public void setCartoon(byte[] cartoon) {
		this.cartoon = cartoon;
	}
	public String getByonicString() {
		return byonicString;
	}
	public void setByonicString(String byonicString) {
		this.byonicString = byonicString;
	}
	public String getCondensedString() {
		return condensedString;
	}
	public void setCondensedString(String condensedString) {
		this.condensedString = condensedString;
	}
}
