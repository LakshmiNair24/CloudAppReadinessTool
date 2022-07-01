package com.cloudapp;

public enum DeploymentStrategyEnum {
	
	EC2("EC2"),
	ELASTICBEANSTALK("EBS");

	private static final DeploymentStrategyEnum[] DEPOLYMENT_STRATEGY = DeploymentStrategyEnum.values();
    private final String value;

    DeploymentStrategyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

	public static DeploymentStrategyEnum[] getDepolymentStrategy() {
		return DEPOLYMENT_STRATEGY;
	}
}
