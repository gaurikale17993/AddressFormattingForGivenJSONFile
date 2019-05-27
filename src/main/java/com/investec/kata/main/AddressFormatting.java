package com.investec.kata.main;

import java.util.List;

import com.investec.kata.datamodels.AddressLineDetail;
import com.investec.kata.datamodels.AddressModel;
import com.investec.kata.datamodels.ProvinceOrState;
import com.investec.kata.datamodels.Type;
import com.investec.kata.exceptions.InvalidAddressFormatException;
import com.investec.kata.utility.Constants;
import com.investec.kata.utility.FieldValidateUtility;


public class AddressFormatting {

	private FieldValidateUtility fieldValidateUtility = new FieldValidateUtility();
	private ValidateAddress validateAddress = new ValidateAddress();

	public String provideAddressInRequiredFormat(AddressModel address) throws InvalidAddressFormatException {
		validateAddress.isAddressValid(address);
		return (type(address.getType()) + addressLine1(address.getAddressLineDetail()) + addressLine2(address.getAddressLineDetail()) +
				cityOrTown(address.getCityOrTown())+ provinceOrState(address.getProvinceOrState()) + address.getPostalCode() + Constants.hyphen + address.getCountry().getName());
	}

	private String provinceOrState(ProvinceOrState provinceOrState) {
		if(!fieldValidateUtility.isNullOrEmpty(provinceOrState) && !provinceOrState.getName().isEmpty())
			return provinceOrState.getName() + Constants.hyphen;
		return Constants.blank;
	}

	public String cityOrTown(String cityOrTown) {
		return !fieldValidateUtility.isNullOrEmpty(cityOrTown) ? cityOrTown + Constants.hyphen : Constants.blank ;
	}

	public String addressLine2(AddressLineDetail address) {
			if(!fieldValidateUtility.isNullOrEmpty(address.getLine2())) {
				if(!Constants.blank.equals(addressLine1(address)))
					return (Constants.comma + address.getLine2()) + Constants.hyphen;
				return address.getLine2() + Constants.hyphen;
			}
			return Constants.hyphen;
	}

	public String addressLine1(AddressLineDetail address) {
		return !fieldValidateUtility.isNullOrEmpty(address.getLine1()) ? address.getLine1() : Constants.blank;
	}

	public String type(Type type) {
		if(!fieldValidateUtility.isNullOrEmpty(type))
			return!fieldValidateUtility.isNullOrEmpty(type.getName()) ? type.getName() + Constants.colon : Constants.blank;
		return Constants.blank;
	}

	public String prettyPrintAllAddresses(List<AddressModel> addresses) throws InvalidAddressFormatException {
		String result = Constants.blank;
		for (AddressModel addressModel : addresses) {
			validateAddress.isAddressValid(addressModel);
			result += provideAddressInRequiredFormat(addressModel)+"\n"; 
		}
		return result;
	}

	public String prettyPrintAddressOfType(List<AddressModel> addresses, String type) throws InvalidAddressFormatException{
		String result = Constants.blank;
		for (AddressModel addressModel : addresses) {
			if(!fieldValidateUtility.isNullOrEmpty(addressModel.getType()) && type.equals(addressModel.getType().getName()))
				result += provideAddressInRequiredFormat(addressModel)+"\n"; 
		}
		return result;
	}
}