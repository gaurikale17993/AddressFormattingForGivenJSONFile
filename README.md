# AddressFormattingForGivenJSONFile
  Given the addresses.json file which contains an array of address, perform validations and formatting
  
 1.Create required data models so that we can map the given addresses.json to it.
 
 2.If the json file is not found at the given location throw FileNotFoundException.
 
 3.If the file is found at the given location map the list of addresses in the json to the list of appropriate Data Model.
 
 4.If addresses.json contain invalid json throw JsonParseException.
 
 5.pretty print version of an address in the format given :
  (Type: Line details - city - province/state - postal code – country)
 
 6.null empty or postal code=0 values must not be present in the address format.
 
 7.pretty print all addresses in the given format.

 8.search address by type and print its details

 9.check if address contains numeric postal code, if not display appropriate error message.

 10.check if address contains country, if not display appropriate error message.

 11.check if address contains at least one address line, if not display appropriate error message.

 12.Check if country code is ZA province field is present, if not display appropriate error message.

 13.Validate all the addresses in given json such that if invalid appropriate error message is displayed for given id.