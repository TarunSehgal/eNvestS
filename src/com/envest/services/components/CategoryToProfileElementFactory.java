package com.envest.services.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.envest.services.components.userprofile.cashFlowDataElement.DataElement;

@Component
public class CategoryToProfileElementFactory {

	private static final Logger LOGGER = Logger.getLogger(CategoryToProfileElementFactory.class.getName());
	
	private  Map<String, List<DataElement>> data = null;

	public CategoryToProfileElementFactory(){
		getBeanFromFactory("userProfileDataMapping");
	}

	private void  getBeanFromFactory(String beanId) {

		try{
			ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath*:categoryToProfileElementMapping.xml");
			if (ctx != null) {
				data = (Map<String,List<DataElement>>)ctx.getBean(beanId);
			}
		}catch(Exception ex){
			LOGGER.error("Exception occurred in getBeanFromFactory(String beanId) " , ex);
		}
	}

	public List<DataElement> getDataElementList(String id){
		return data.get(id);
	}

	public List<DataElement> getProfileData(){
		List<DataElement> list = new ArrayList<DataElement>(10);
		Collection<List<DataElement>> collection = data.values();
		Iterator<List<DataElement>> i = collection.iterator();
		while(i.hasNext()){
			list.addAll(i.next());
		}
		return list;	
	}
}
