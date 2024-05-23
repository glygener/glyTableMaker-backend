package org.glygen.tablemaker.persistence.table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GlycanColumns {
	GLYTOUCANID ("GlytoucanID"),
	MASS ("Mass"),
	CARTOON ("Cartoon");
	
	String label;
	
	@JsonCreator
	public static GlycanColumns forValue(String value) {
		if (value.equals("GlytoucanID"))
			return GLYTOUCANID;
		else if (value.equals("Mass"))
			return MASS;
		else if (value.equals("Cartoon"))
            return CARTOON;
		return GLYTOUCANID;
	}
	
	private GlycanColumns(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	@JsonValue
    public String external() { return label; }
}
