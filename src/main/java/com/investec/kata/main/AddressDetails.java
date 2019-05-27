package com.investec.kata.main;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.investec.kata.datamodels.AddressLineDetail;
import com.investec.kata.datamodels.AddressModel;
import com.investec.kata.datamodels.Country;
import com.investec.kata.datamodels.ErrorMessage;
import com.investec.kata.datamodels.ProvinceOrState;


public class AddressDetails {

	private String colon = " : ";
	private String comma = ", ";
	private String hyphen = " - ";
	private String blank = "";
	private String ZA = "ZA";
	
	public List<AddressModel> mapJsonListToPOJO(String filePath) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readValue(new File(filePath), JsonNode.class);
		List<AddressModel> addresses = mapper.convertValue(jsonNode, new TypeReference<List<AddressModel>>(){});
		return addresses;
	}

	public String prettyPrintAddress(AddressModel address) throws Exception {
		String prettyPrintAddress = type(address) + addressLine1(address.getAddressLineDetail()) + addressLine2(address.getAddressLineDetail()) +
				cityOrTown(address)+ validateProvinceOrState(address.getCountry(), address.getProvinceOrState()) + address.getPostalCode() + validateCountry(address.getCountry(), address.getPostalCode(), address.getProvinceOrState());
		return prettyPrintAddress;
	}

	public Object validateCountry(Country country, String postalCode, ProvinceOrState provinceOrState) {
		if(!isNullOrEmpty(country)) {
			validateProvinceOrState(country, provinceOrState);
			if(!isNullOrEmpty(country.getName())) {
				if(!blank.equals(validatePostalCode(postalCode)))
					return hyphen + country.getName();
				return country.getName();
			}
			return  blank;
		}
		return methodErrorMessage("Address must contain country data");
	}

	public Object validatePostalCode(String postalCode) {
		if (isNullOrEmpty(postalCode) || !StringUtils.isNumeric(postalCode) ||
				0 == Integer.parseInt(postalCode))
			return methodErrorMessage("Invalid postal code,it must be numberic");
		return null;
	}

	private Object validateProvinceOrState(Country country, ProvinceOrState provinceOrState) {
		if(!isNullOrEmpty(provinceOrState)) {
			if (!isNullOrEmpty(provinceOrState.getName()))
				return provinceOrState.getName()+ hyphen;
			else if (!isNullOrEmpty(provinceOrState.getCode()) && ZA.equals(country.getCode())) {
				return methodErrorMessage("Province must for country code ZA");
			}
			return blank;
		}
		else if(ZA.equals(country.getCode()))
			return methodErrorMessage("Province must for country code ZA");
		return blank;
	}

	private Object methodErrorMessage(String string) {
		ErrorMessage methodErrorMessage = new ErrorMessage();
		methodErrorMessage.setErrorMessage(string);
		return methodErrorMessage;
	}

	public String cityOrTown(AddressModel address) {
		return !isNullOrEmpty(address.getCityOrTown()) ? address.getCityOrTown() + hyphen : blank ;
	}

	public String addressLine2(AddressLineDetail address) {
		if(!isNullOrEmpty(address)) {
			if(!isNullOrEmpty(address.getLine2())) {
				if(!blank.equals(addressLine1(address)))
					return (comma + address.getLine2()) + hyphen;
				return address.getLine2() + hyphen;
			}
			if(!blank.equals(addressLine1(address)))
				return hyphen;
			return blank;
		}
		return blank;
	}

	public String addressLine1(AddressLineDetail address) {
		if(!isNullOrEmpty(address))
			return !isNullOrEmpty(address.getLine1()) ? 
				address.getLine1() : blank;
		return blank;
	}

	public String type(AddressModel address) {
		if(!isNullOrEmpty(address.getType()))
			return!isNullOrEmpty(address.getType().getName()) ? address.getType().getName() + colon : blank;
		return blank;
	}
	
	public boolean isNullOrEmpty(Object field) {
		if(null == field || field.toString().isEmpty())
			return true;
		return false;
	}

	public String prettyPrintAllAddresses(List<AddressModel> addresses) throws Exception {
		String result = blank;
		for (AddressModel addressModel : addresses) {
			validateAddress(addressModel);
			result += prettyPrintAddress(addressModel)+"\n"; 
		}
		return result;
	}

	public void validateAddress(AddressModel addressModel) throws Exception {
		ErrorMessage errorMessage = new ErrorMessage();
		String message = blank;
		errorMessage.setErrorCode(addressModel.getId());
		if(validatePostalCode(addressModel.getPostalCode()) instanceof ErrorMessage)
			message +=((ErrorMessage) (validatePostalCode(addressModel.getPostalCode()))).getErrorMessage() + "\n";
		if(validateCountry(addressModel.getCountry(), addressModel.getPostalCode(), addressModel.getProvinceOrState()) instanceof ErrorMessage)
			message +=((ErrorMessage) (validateCountry(addressModel.getCountry(), addressModel.getPostalCode(), addressModel.getProvinceOrState()))).getErrorMessage() + "\n";
		if(validateAddressDetails(addressModel.getAddressLineDetail()) instanceof ErrorMessage)
			message +=((ErrorMessage) (validateAddressDetails(addressModel.getAddressLineDetail()))).getErrorMessage() + "\n";
		System.out.println(message.length());
		if(message.length() > 0) {
			message += "for Address ID: " + errorMessage.getErrorCode();
			errorMessage.setErrorMessage(message);
			throw new Exception(errorMessage.getErrorMessage());
		}
	}

	public String prettyPrintAddressOfType(List<AddressModel> addresses, String type) throws Exception {
		String result = blank;
		for (AddressModel addressModel : addresses) {
			if(!isNullOrEmpty(addressModel.getType()) && type.equals(addressModel.getType().getName()))
			result += prettyPrintAddress(addressModel)+"\n"; 
		}
		return result;
	}

	public Object validateAddressDetails(AddressLineDetail addressLineDetail){
		if(!isNullOrEmpty(addressLineDetail)) {
			if(!isNullOrEmpty(addressLineDetail.getLine1()) || !isNullOrEmpty(addressLineDetail.getLine2()))
				return addressLine1(addressLineDetail) + addressLine2(addressLineDetail);
			return methodErrorMessage("Please provide atleast one address line");
		}
		return methodErrorMessage("Please provide atleast one address line");
	}

	public void validateCountryProvince(Country country, ProvinceOrState provinceOrState) throws Exception {
		String message = blank;
		if(ZA.equals(country.getCode())) {
			message = ((ErrorMessage)validateProvinceOrState(country, provinceOrState)).getErrorMessage();
			throw new Exception(message);
		}
		
	}
}
