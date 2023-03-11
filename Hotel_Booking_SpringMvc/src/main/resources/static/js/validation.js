function validateRegForm() {

	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	var email = $("#email").val();
	var password = $("#password").val();
	var idNumber = $("#idNumber").val();
	var phoneNumber = $("#phoneNumber").val();

	var regEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/g;
	var returnValue = true;

	if (firstName === "") {
		$("#firstNameError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#firstNameError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (lastName === "") {
		$("#lastNameError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#lastNameError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (email === "" || !regEmail.test(email)) {
		$("#emailError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#emailError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (password === "") {
		$("#passwordError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#passwordError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (idNumber === "" || idNumber.length < 6 || idNumber.length > 12) {
		$("#idNumberError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#idNumberError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (phoneNumber === "" || phoneNumber.length < 9 || phoneNumber.length > 15) {
		$("#phoneNumberError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#phoneNumberError").css({
			"visibility" : "hidden"
		});
	}
	;

	return returnValue;
};

function validateReservation() {

	var checkInDateStr = $("#checkInDateStr").val();
	var numberOfNights = $("#numberOfNights").val();

	var numberOfNightsNum = Number(numberOfNights);
	var returnValue = true;

	if (checkInDateStr === "") {
		$("#checkInDateError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#checkInDateError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (numberOfNights === "" || numberOfNightsNum < 1
			|| numberOfNightsNum > 30) {
		$("#numberOfNightsError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#numberOfNightsError").css({
			"visibility" : "hidden"
		});
	}
	;

	return returnValue;
};

function validateRoomCategory() {
	var name = $("#name").val();
	var price = $("#price").val();
	var wc = $("#wc").val();
	var wifi = $("#wifi").val();
	var tv = $("#tv").val();
	var bar = $("#bar").val();

	var priceNum = Number(price);
	var returnValue = true;

	if (name === "") {
		$("#nameError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#nameError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (price === "" || priceNum <= 0) {
		$("#priceError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#priceError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (wc === "") {
		$("#wcError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#wcError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (wifi === "") {
		$("#wifiError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#wifiError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (tv === "") {
		$("#tvError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#tvError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (bar === "") {
		$("#barError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#barError").css({
			"visibility" : "hidden"
		});
	}
	;

	return returnValue;
};

function validateRoom() {

	var roomNumber = $("#roomNumber").val();
	var roomCategory = $("#roomCategory").val();

	var roomNumberNum = Number(roomNumber);
	var returnValue = true;

	if (roomNumber === "" || roomNumberNum <= 0) {
		$("#roomNumberError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#roomNumberError").css({
			"visibility" : "hidden"
		});
	}
	;

	if (roomCategory === "") {
		$("#roomCategoryError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#roomCategoryError").css({
			"visibility" : "hidden"
		});
	}
	;

	return returnValue;
};

