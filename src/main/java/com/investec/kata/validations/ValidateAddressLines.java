package com.investec.kata.validations;

import com.investec.kata.datamodels.AddressLineDetail;
import com.investec.kata.utility.FieldValidateUtility;

public class ValidateAddressLines {

	private FieldValidateUtility fieldValidateUtility = new FieldValidateUtility();
	
	public String isValidAddressDetails(AddressLineDetail addressLineDetail){
		if(fieldValidateUtility.isNullOrEmpty(addressLineDetail) || (addressLineDetail.getLine1().isEmpty() && addressLineDetail.getLine2().isEmpty()))
			return "Please provide atleast one address line\n";
		return "";
	}
}
