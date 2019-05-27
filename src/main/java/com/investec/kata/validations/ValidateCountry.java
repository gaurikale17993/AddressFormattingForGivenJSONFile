package com.investec.kata.validations;

import com.investec.kata.datamodels.Country;
import com.investec.kata.utility.FieldValidateUtility;

public class ValidateCountry {
	
	private FieldValidateUtility fieldValidateUtility = new FieldValidateUtility();
	
	public String isValidCountry(Country country) {
		if(fieldValidateUtility.isNullOrEmpty(country) || country.getName().isEmpty())
			return "Address must contain country data\n";
		return "";
	}
}
