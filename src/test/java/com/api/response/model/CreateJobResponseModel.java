package com.api.response.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@Data
@NoArgsConstructor 
//No arg constructor will be needed for jacson/serialization/deserialization

public class CreateJobResponseModel {

	private String message;
	private CreateJobDataModel data;
}
