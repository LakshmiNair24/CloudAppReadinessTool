package com.cloudapp;

import java.util.stream.Stream;

public enum EC2 {
	PACKAGING(40),
	DB_COMPONENT(10),
	MESSAGE_EVENT(10),
	DOCKER(40);
	
    private final Integer value;
    
    public static String getClassName() {
    	return EC2.class.getSimpleName();
    }

    EC2(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
	
    
    public static String[] names() {
        return Stream.of(EC2.values()).map(EC2::name).toArray(String[]::new);
    }

	
	
}
