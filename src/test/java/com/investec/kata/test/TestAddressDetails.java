package com.investec.kata.test;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.investec.kata.datamodels.AddressLineDetail;
import com.investec.kata.datamodels.AddressModel;
import com.investec.kata.datamodels.ProvinceOrState;
import com.investec.kata.exceptions.InvalidAddressFormatException;
import com.investec.kata.main.AddressFormatting;
import com.investec.kata.main.MapJsonToObjectModel;
import com.investec.kata.main.ValidateAddress;
import com.investec.kata.validations.ValidateProvinceOrState;

@Test
public class TestAddressDetails {
	private MapJsonToObjectModel mapJsonToObjectModel;
	private AddressFormatting addressFormatting;
	private String addressesFilePath;
	private String addressFilePath;
	private String prettyPrintAddress;
	private ObjectMapper mapper;
	private ValidateAddress validateAddress;
	
	@BeforeTest
	public void init() {
		mapper = new ObjectMapper();
		mapJsonToObjectModel = new MapJsonToObjectModel();
		addressFormatting = new AddressFormatting();
		new ValidateProvinceOrState();
		addressesFilePath = "addresses.json";
		addressFilePath = "address.json";		
		validateAddress = new ValidateAddress();
	}
	
	@Test(expectedExceptions=FileNotFoundException.class)
	public void shouldThrowFileNotFoundExceptionForInvalidFilePath() throws Exception {
		mapJsonToObjectModel.mapJsonListToPOJO("");		
	}
	
	public void mapAddressesJsonToListOfAddressModel() throws Exception {
		JsonNode jsonNode = mapper.readValue(new File(addressesFilePath), JsonNode.class);
		List<AddressModel> addresses = mapper.convertValue(jsonNode, new TypeReference<List<AddressModel>>(){});
		assertEquals(mapJsonToObjectModel.mapJsonListToPOJO(addressesFilePath).toString(), addresses.toString());
	}
	
	@Test(expectedExceptions=JsonParseException.class)
	public void shouldThrowExceptionIfFileContainsInvalidJson() throws IOException {
		mapJsonToObjectModel.mapJsonListToPOJO("invalidaddress.json");
	}
	
	public void shouldReturnAdressInMentionedFormat() throws Exception{
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		prettyPrintAddress = "Physical Address : Address 1, Line 2 - City 1 - Eastern Cape - 1234 - South Africa";
		assertEquals(addressFormatting.provideAddressInRequiredFormat(address), prettyPrintAddress);
	}
	
	public void skipNullEmptyZeroInPrettyPrintAddress() throws Exception{
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		prettyPrintAddress = "Physical Address : Address 1, Line 2 - City 1 - Eastern Cape - 1234 - South Africa";
		assertEquals(addressFormatting.provideAddressInRequiredFormat(address), prettyPrintAddress);
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
		assertEquals(addressFormatting.prettyPrintAllAddresses(addresses),prettyPrintAddress);
	}
	
	public void prettyPrintAddressOfType() throws Exception{
		JsonNode jsonNode = mapper.readValue(new File(addressesFilePath), JsonNode.class);
		List<AddressModel> addresses = mapper.convertValue(jsonNode, new TypeReference<List<AddressModel>>(){});
		String type = "Physical Address";
		prettyPrintAddress = "Physical Address : Address 1, Line 2 - City 1 - Eastern Cape - 1234 - South Africa\n";
		assertEquals(addressFormatting.prettyPrintAddressOfType(addresses,type),prettyPrintAddress);
	}
	
	@Test(expectedExceptions = InvalidAddressFormatException.class, expectedExceptionsMessageRegExp = "Invalid postal code,it must be numberic\nfor Address ID: 1")
	public void nonNumericPostalCodeThrowsException() throws Exception {
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		address.setPostalCode("abc");
		validateAddress.isAddressValid(address);
	}
	
	@Test(expectedExceptions = InvalidAddressFormatException.class, expectedExceptionsMessageRegExp = "Address must contain country data\nfor Address ID: 1")
	public void noCountryDetailsThrowsException() throws Exception {
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		address.setCountry(null);
		validateAddress.isAddressValid(address);
	}
	
	@Test(expectedExceptions = InvalidAddressFormatException.class, expectedExceptionsMessageRegExp = "Please provide atleast one address line\nfor Address ID: 1")
	public void noAddressDetailThrowsException() throws Exception {
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		address.setAddressLineDetail(null);
		validateAddress.isAddressValid(address);
	}
	
	@Test(expectedExceptions = InvalidAddressFormatException.class, expectedExceptionsMessageRegExp = "Province must for country code ZA\nfor Address ID: 1")
	public void noProvinceForZAThrowsException() throws Exception{
		AddressModel  address = mapper.readValue(new File(addressFilePath), AddressModel.class);
		address.setProvinceOrState(null);
		validateAddress.isAddressValid(address);
	}
	
	@Test(expectedExceptions = InvalidAddressFormatException.class, expectedExceptionsMessageRegExp = "Please provide atleast one address line\nfor Address ID: 2")
	public void invalidateAddressesThrowsEcxeption() throws Exception{
		JsonNode jsonNode = mapper.readValue(new File(addressesFilePath), JsonNode.class);
		List<AddressModel> addresses = mapper.convertValue(jsonNode, new TypeReference<List<AddressModel>>(){});
		assertEquals(addressFormatting.prettyPrintAllAddresses(addresses),prettyPrintAddress);
	}
}
