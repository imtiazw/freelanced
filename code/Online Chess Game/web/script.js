// JavaScript Document

  	function validateSignInForm()
  	{
	  var letternumber = /^[A-Za-z0-9]+$/;
	  var valid = true;
	  if(document.signinform.userid.value == "")
	  {
		  document.getElementById('signinrowuserid').innerHTML = "Enter user id";
		  valid = false;
	  }
	  
	  else if(!document.signinform.userid.value.match(letternumber))
	  {
		  document.getElementById('signinrowuserid').innerHTML = "Enter valid userid";
		  valid = false;
	  }
	  
	  if(document.signinform.password.value == "")
	  {
		  document.getElementById('signinrowpassword').innerHTML = "Enter password";
		  valid = false;
	  }
	  
	  return valid;
  	}
  
function validateProfile()
{
	var letter= /^[A-Za-z]+$/;
	var letternumber= /^[A-Za-z0-9]+$/;
	var numbers= /^[0-9]+$/;
	var valid = true;
	if(!document.signupform.firstname.value.match(letter))
	{
		document.getElementById('rowfirstname').innerHTML = "Use letters only";
		valid = false;
	}
	
	if(!document.signupform.lastname.value.match(letter))
	{
		document.getElementById('rowlastname').innerHTML = "Use only letters";
		valid = false;
	}
	
	if(!document.signupform.userid.value.match(letternumber))
	{
		document.getElementById('rowuserid').innerHTML = "Must contain characters and numbers";
		valid = false;
	}
	
	if(!document.signupform.password.value)
	{
		document.getElementById('rowpassword').innerHTML = "Password is mandatory";
		valid = false;
	}
	
	if(!document.signupform.contact.value.match(numbers) && document.signupform.contact.value)
	{
		document.getElementById('rowcontact').innerHTML = "Use numbers only";
		valid = false;
	}
	
	if(!(document.signupform.Gender[0].checked || document.signupform.Gender[1].checked))
	{
		document.getElementById('rowgender').innerHTML = "Select Gender";
		valid = false;
	}
	
	if(!validateEmail(document.signupform.primaryemail))
	{
		document.getElementById('rowprimaryemail').innerHTML = "Invalid email format";
		valid = false;
	}
	
	if(!validateEmail(document.signupform.secondaryemail) && document.signupform.secondaryemail.value)
	{
		document.getElementById('rowsecondaryemail').innerHTML = "Invalid email format";
		valid = false;
	}
	
	if(document.getElementById('date').options[document.getElementById('date').selectedIndex].value == 'date')
	{
		document.getElementById('rowdate').innerHTML = "Select date";
		valid = false;
	}
	
	if(document.getElementById('month').options[document.getElementById('month').selectedIndex].value == 'month')
	{
		document.getElementById('rowmonth').innerHTML = "Select month";
		valid = false;
	}
	
	if(document.getElementById('year').options[document.getElementById('year').selectedIndex].value == 'year')
	{
		document.getElementById('rowyear').innerHTML = "Select year";
		valid = false;
	}
	return valid;
}

	function validateEmail(input)
  	{
		 var x=input.value;
		 var atpos=x.indexOf("@");
		 var dotpos=x.lastIndexOf(".");
		 if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length)
		 {
			 return false;
		 }
		 else
		 {
			 return true;
		 }
  	}
//-->