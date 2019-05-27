package com.investec.kata.main;

import com.investec.kata.datamodels.AddressModel;
import com.investec.kata.datamodels.ErrorMessage;
import com.investec.kata.exceptions.InvalidAddressFormatException;
import com.investec.kata.validations.ValidateAddressLines;
import com.investec.kata.validations.ValidateCountry;
import com.investec.kata.validations.ValidatePostalCode;
import com.investec.kata.validations.ValidateProvinceOrState;

public class ValidateAddress {
	
	private ValidatePostalCode validatePostalCode = new ValidatePostalCode();
	private ValidateCountry validateCountry = new ValidateCountry();
	private ValidateAddressLines validateAddressLines = new ValidateAddressLines();
	private ValidateProvinceOrState validateProvinceOrState = new ValidateProvinceOrState();
	
	public void isAddressValid(AddressModel addressModel) throws InvalidAddressFormatException {
		ErrorMessage errorMessage = new ErrorMessage();
		String message = "";
		errorMessage.setCode(addressModel.getId());
		message +=  validatePostalCode.isValidPostalCode(addressModel.getPostalCode()) +
					validateCountry.isValidCountry(addressModel.getCountry()) +
					validateAddressLines.isValidAddressDetails(addressModel.getAddressLineDetail()) +
					validateProvinceOrState.isValidProvinceOrState(addressModel.getCountry(), addressModel.getProvinceOrState());
						
		if(message.length() > 0) {
			message += "for Address ID: " + errorMessage.getCode();
			errorMessage.setMessage(message);
			throw new InvalidAddressFormatException(errorMessage.getMessage());
		}
	}
	
	
	
	
	
	
}
