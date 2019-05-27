package com.investec.kata.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.investec.kata.datamodels.AddressModel;

public class MapJsonToObjectModel {
	
	public List<AddressModel> mapJsonListToPOJO(String filePath) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readValue(new File(filePath), JsonNode.class);
		return mapper.convertValue(jsonNode, new TypeReference<List<AddressModel>>(){});
	}
}
