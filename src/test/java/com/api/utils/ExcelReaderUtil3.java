package com.api.utils;

import java.util.Iterator;

import com.api.request.model.CreateJobPayload;
import com.dataproviders.api.bean.CreateJobBean;

public class ExcelReaderUtil3 {
	public static void main(String args[]) {

		Iterator<CreateJobBean> iterator = ExcelReaderUtil2.loadTestData("testData/PhoenixTestData.xlsx",
				"CreateJobTestData", CreateJobBean.class);
		CreateJobBean bean;
		CreateJobPayload createJobPayload;
		while (iterator.hasNext()) {

			bean = iterator.next();
			createJobPayload = CreateJobBeanMapper.mapper(bean);
			System.out.println(createJobPayload);

		}

	}
}
