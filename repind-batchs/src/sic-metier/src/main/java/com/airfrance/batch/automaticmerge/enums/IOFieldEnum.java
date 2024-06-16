package com.airfrance.batch.automaticmerge.enums;

public enum IOFieldEnum {
	ELEMENT_DUPLICATE("elementDuplicate"),
	NB_GINS("nbGins"),
	GIN_TARGET("ginTarget"),
	GIN_SOURCE("ginSource"),
	MERGE_DATE("mergeDate"),
	MERGE_DATE_AS_STRING("mergeDateAsString"),
	GINS("gins");

	private final String value;

	IOFieldEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
