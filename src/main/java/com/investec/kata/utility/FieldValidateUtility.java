package com.investec.kata.utility;

public class FieldValidateUtility {
	public boolean isNullOrEmpty(Object field) {
		return (null == field || field.toString().isEmpty());
	}
}
