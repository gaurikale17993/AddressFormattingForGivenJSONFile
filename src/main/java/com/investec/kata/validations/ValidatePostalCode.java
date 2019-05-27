package com.investec.kata.validations;

import org.apache.commons.lang3.StringUtils;

import com.investec.kata.utility.FieldValidateUtility;

public class ValidatePostalCode {

	private FieldValidateUtility fieldValidateUtility = new FieldValidateUtility();
	
	public String isValidPostalCode(String postalCode) {
		if (fieldValidateUtility.isNullOrEmpty(postalCode) || !StringUtils.isNumeric(postalCode) ||
				0 == Integer.parseInt(postalCode))
			return "Invalid postal code,it must be numberic\n";
		return "";
	}
}
