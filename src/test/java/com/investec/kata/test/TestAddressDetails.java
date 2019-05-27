package com.investec.kata.test;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.investec.kata.datamodels.AddressLineDetail;
import com.investec.kata.datamodels.AddressModel;
import com.investec.kata.datamodels.ProvinceOrState;
import com.investec.kata.main.AddressDetails;

@Test
public class TestAddressDetails {
	private AddressDetails addressDetails;
	private String addressesFilePath;
	private String addressFilePath;
	private String prettyPrintAddress;
	private ObjectMapper mapper;
	
	@BeforeTest
	public void init() {
		mapper = new ObjectMapper();
		addressDetails = new AddressDetails();
		addressesFilePath = "addresses.json";
		addressFilePath = "address.json";
	}
	@Test(expectedExceptions=FileNotFoundException.class)
	public void fileNotFoundException() throws Exception {
		addressDetails.mapJsonListToPOJO("");		
	}
	
	public void mapJsonToPOJO() throws Exception {
		JsonNode jsonNode = mapper.readValue(new File(addressesFilePath), JsonNode.class);
		List<AddressModel> addresses = mapper.convertValue(jsonNode, new TypeReference<List<AddressModel>>(){});
		assertEquals(addressDetails.mapJsonListToPOJO(addressesFilePath).toString(), addresses.toString());
	}
	
	public void prettyPrintAddress() throws Exception{
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		prettyPrintAddress = "Physical Address : Address 1, Line 2 - City 1 - Eastern Cape - 1234 - South Africa";
		assertEquals(addressDetails.prettyPrintAddress(address), prettyPrintAddress);
	}
	
	public void skipNullEmptyZeroInPrettyPrintAddress() throws Exception{
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		prettyPrintAddress = "Physical Address : Address 1, Line 2 - City 1 - Eastern Cape - 1234 - South Africa";
		assertEquals(addressDetails.prettyPrintAddress(address), prettyPrintAddress);
	}
	
	public void prettyPrintAllAddresses() throws Exception{
		JsonNode jsonNode = mapper.readValue(new File(addressesFilePath), JsonNode.class);
		List<AddressModel> addresses = mapper.convertValue(jsonNode, new TypeReference<List<AddressModel>>(){});
		prettyPrintAddress = "Physical Address : Address 1, Line 2 - City 1 - Eastern Cape - 1234 - South Africa\n" + 
				"Postal Address : Address 2 - City 2 - 2345 - Lebanon\n" + 
				"Business Address : Address 3 - City 3 - Gauteng - 3456 - South Africa\n";
		AddressModel address1 = addresses.get(1);
		AddressLineDetail addressLineDetail = new AddressLineDetail();
		addressLineDetail.setLine1("Address 2");
		address1.setAddressLineDetail(addressLineDetail);
		addresses.set(1, address1);
		
		AddressModel address2 = addresses.get(2);
		ProvinceOrState provinceOrState = new ProvinceOrState();
		provinceOrState.setCode(1);
		provinceOrState.setName("Gauteng");
		address2.setProvinceOrState(provinceOrState);
		addresses.set(2, address2);
		assertEquals(addressDetails.prettyPrintAllAddresses(addresses),prettyPrintAddress);
	}
	
	public void prettyPrintAddressOfType() throws Exception{
		JsonNode jsonNode = mapper.readValue(new File(addressesFilePath), JsonNode.class);
		List<AddressModel> addresses = mapper.convertValue(jsonNode, new TypeReference<List<AddressModel>>(){});
		String type = "Physical Address";
		prettyPrintAddress = "Physical Address : Address 1, Line 2 - City 1 - Eastern Cape - 1234 - South Africa\n";
		assertEquals(addressDetails.prettyPrintAddressOfType(addresses,type),prettyPrintAddress);
	}
	
	@Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "Invalid postal code,it must be numberic\nfor Address ID: 1")
	public void nonNumericPostalCodeThrowsException() throws Exception {
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		address.setPostalCode("abc");
		addressDetails.validateAddress(address);
	}
	
	@Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "Address must contain country data\nfor Address ID: 1")
	public void noCountryDetailsThrowsException() throws Exception {
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		address.setCountry(null);
		addressDetails.validateAddress(address);
	}
	
	@Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "Please provide atleast one address line\nfor Address ID: 1")
	public void noAddressDetailThrowsException() throws Exception {
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		address.setAddressLineDetail(null);
		addressDetails.validateAddress(address);
	}
	
	@Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "Province must for country code ZA")
	public void noProvinceForZAThrowsException() throws Exception{
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		address.setProvinceOrState(null);
		addressDetails.validateCountryProvince(address.getCountry(), address.getProvinceOrState());
	}
	
	@Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "Please provide atleast one address line\nfor Address ID: 2")
	public void invalidateAddressesThrowsEcxeption() throws Exception{
		JsonNode jsonNode = mapper.readValue(new File(addressesFilePath), JsonNode.class);
		List<AddressModel> addresses = mapper.convertValue(jsonNode, new TypeReference<List<AddressModel>>(){});
		assertEquals(addressDetails.prettyPrintAllAddresses(addresses),prettyPrintAddress);
	}
}
