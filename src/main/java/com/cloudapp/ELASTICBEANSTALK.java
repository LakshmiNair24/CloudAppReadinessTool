package com.cloudapp;

import java.util.stream.Stream;
/**
 * Weightage of the component if it deployed in Elastic Beanstalk
 * @author lakshmi
 *
 */
public enum ELASTICBEANSTALK {
	PACKAGING(60),
	DB_COMPONENT(10),
	NON_DB_COMPONENT(10),
	NO_MESSAGE_EVENT(10),
	MESSAGE_EVENT(0),
	NO_DOCKER(10),
	DOCKER(0);
	
    private final Integer value;
    
    ELASTICBEANSTALK(Integer value) {
        this.value = value;
    }
    
    public Integer getValue() {
        return value;
    }

    public static String getClassName() {
    	return ELASTICBEANSTALK.class.getSimpleName();
    }
    
    public static String[] names() {
        return Stream.of(ELASTICBEANSTALK.values()).map(ELASTICBEANSTALK::name).toArray(String[]::new);
    }

	
	
}
