# AddressFormattingForGivenJSONFile
  Given the addresses.json file which contains an array of address, perform validations and formatting
  
  Given the attached addresses.json file which contains an array of address, do the following:

a. Write a function to return a pretty print version of an address in the format: 

Type: Line details - city - province/state - postal code – country

Physical Address: Address 1, Line 2 - City 1 - Eastern Cape - 1234 - South Africa

 // example Java function definition

  public String prettyPrintAddress(Address address) {

    // your code here

  }

 Steps:
 1.Create POJOs for given address json
 
 2.Check if json file exist at classpath
 
 3.Map json to POJO
 
 3.pretty print version of an address in the format
 
 4.check none of the fields in pretty printed address are null/empty/0 else skip them.
 
b. Write a function to pretty print all the addresses in the attached file

Steps:

1.Map json to POJO

2.pretty print all addresses in given format

c. Write a function to print an address of a certain type (postal, physical, business)

Steps:

1.Map json to POJO

2.Find address by type

3.Pretty print address of that type in given format


d. Write a function to validate an address

      i.A valid address must consist of a numeric postal code, a country, and at least one address line that is not blank or null.

      ii.If the country is ZA, then a province is required as well.

Steps:

1.Map json to POJO

2.check if address contains numeric postal code, if not display error message.

3.check if address contains country, if not display error message.

4.check if address contains atleast one address line, if not display error message.

5.Check if country code is ZA province fipublicvaeld is present, if not display error message.


e. For each address in the attached file, determine if it is valid or not, and if not give an indication of what is invalid in a message format of your choice.

Steps:

1.Map json to POJO

2.For every address apply above validation

3.If not valid display appropriate error message.
