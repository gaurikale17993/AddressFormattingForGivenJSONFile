package com.investec.kata.validations;

import com.investec.kata.datamodels.Country;
import com.investec.kata.datamodels.ProvinceOrState;
import com.investec.kata.utility.Constants;
import com.investec.kata.utility.FieldValidateUtility;

public class ValidateProvinceOrState {

	private FieldValidateUtility fieldValidateUtility = new FieldValidateUtility();

	public String isValidProvinceOrState(Country country, ProvinceOrState provinceOrState) {
		if((fieldValidateUtility.isNullOrEmpty(provinceOrState) || provinceOrState.getName().isEmpty()) && Constants.za.equals(country.getCode()))
			return "Province must for country code ZA\n";
		return "";
	}
}
