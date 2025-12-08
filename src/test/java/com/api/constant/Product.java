package com.api.constant;
//ENUM is a class which can store constant
//IN ENUM , CONTRACTORS ARE PRIVATE
public enum Product {
	
	NEXUS_2(1),PIXEL(2);


int code;
private Product(int code)
{
	this.code = code;
}

public int getCode()
{
	return code;
}

}