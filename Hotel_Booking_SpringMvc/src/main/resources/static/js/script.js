window.onload = redirectHome;

function redirectHome() {
	$("#ajaxLoadedContent").load("/home");
}

function redirectContact() {
	$("#ajaxLoadedContent").load("/contactInfo");
}

function redirectLogin() {
	$("#ajaxLoadedContent").load("/login");
}

function redirectRegister() {
	$("#ajaxLoadedContent").load("/register");
}

function redirectAllGuests() {
	$("#ajaxLoadedContent").load("/admin/allGuests");
}

function redirectGuestDetails(guestId) {
	$("#ajaxLoadedContent").load("/admin/guestDetails/" + guestId);
}

function redirectAllCategories() {
	$("#ajaxLoadedContent").load("/admin/allRoomCategories");
}

function redirectAddCategory() {
	$("#ajaxLoadedContent").load("/admin/createRoomCategory");
}

function redirectCategoryDetails(categoryId) {
	$("#ajaxLoadedContent").load("/admin/categoryDetails/" + categoryId);
}

function redirectUpdateCategory(categoryId) {
	$("#ajaxLoadedContent").load("/admin/updateRoomCategory/" + categoryId);
}

function deleteCategory(categoryId) {
	$("#ajaxLoadedContent").load("/admin/deleteRoomCategory" + categoryId);
}

function redirectAllRooms() {
	$("#ajaxLoadedContent").load("/admin/allRooms");
}

function redirectAddRoom() {
	$("#ajaxLoadedContent").load("/admin/createRoom");
}

function redirectRoomDetails(roomId) {
	$("#ajaxLoadedContent").load("/admin/roomDetails/" + roomId);
}

function redirectUpdateRoom(roomId) {
	$("#ajaxLoadedContent").load("/admin/updateRoom/" + roomId);
}

function redirectUserReservations() {
	$("#ajaxLoadedContent").load("/guests/allUserReservations");
}

function redirectAllReservations() {
	$("#ajaxLoadedContent").load("/admin/allReservations");
}

function redirectAllActiveReservations() {
	$("#ajaxLoadedContent").load("/admin/allActiveReservations");
}

function redirectAllExpiredReservations() {
	$("#ajaxLoadedContent").load("/admin/allExpiredReservations");
}

function redirectReservationDetails(reservationId) {
	$("#ajaxLoadedContent").load("/admin/reservationDetails/" + reservationId);
}

function redirectBookReservation() {
	$("#ajaxLoadedContent").load("/guests/bookReservation");
}

function redirectAllNotes() {
	$("#ajaxLoadedContent").load("/admin/allNotes");
}

function redirectAllNotesFromToday() {
	$("#ajaxLoadedContent").load("/admin/allNotesToday");
}

function redirectNoteDetails(noteId) {
	$("#ajaxLoadedContent").load("/admin/noteDetails/" + noteId);
}

function redirectSwitchRoom(reservationId) {
	$("#ajaxLoadedContent").load(
			"/admin/switchReservationRoom/" + reservationId);
}

function redirectAccountDetails(){
	$("#ajaxLoadedContent").load("/guests/accountDetails");
}

function loginInterceptor(formName) {
	var $form = $("#" + formName);

	$form.on('submit', function(e) {
		e.preventDefault();

		$.ajax({
			url : "http://localhost:8080/login",
			type : 'post',
			data : $form.serialize(),
			success : function(response) {
				confirmLoginPass();
			},
			error : function(error) {
				alert("Failed");

			}
		});

	});
};

function confirmLoginPass() {
	$.ajax({
		url : "http://localhost:8080/loginPassConfirm",
		type : "POST",
		success : function(response) {
			window.location.href = "/";
		},
		error : function(error) {
			$("#ajaxLoadedContent").load("/loginErrorPage");
		}
	});
}

function formInterceptor(formName) {
	var $form = $("#" + formName);

	$form.on('submit', function(e) {
		e.preventDefault();
		if (validateRegForm()) {
			$.ajax({
				url : "http://localhost:8080/register",
				type : 'post',
				data : $form.serialize(),
				success : function(response) {
					$("#ajaxLoadedContent").load("/registerComplete");
				},
				error : function(error) {
					$("#ajaxLoadedContent").load("/registerFail");

				}
			});
		}
		;
	});
};

function categoryInterceptor(formName) {
	var $form = $("#" + formName);

	$form.on('submit', function(e) {
		e.preventDefault();
		if (validateRoomCategory()) {
			$.ajax({
				url : "http://localhost:8080/admin/createRoomCategory",
				type : "POST",
				data : $form.serialize(),
				success : function(response) {
					$("#ajaxLoadedContent").load("/admin/allRoomCategories");
				},
				error : function(error) {
					alert("Failed");

				}
			});
		}
		;
	});
};

function updateReservation(formName, reservationId) {
	var $form = $("#" + formName);

	$form.on('submit', function(e) {
		e.preventDefault();
		if (validateRoomId()) {
			$.ajax({
				url : "http://localhost:8080/admin/updateReservation/"
						+ reservationId,
				type : "POST",
				data : $form.serialize(),
				success : function(response) {
					redirectAllReservations();
				},
				error : function(error) {
					alert("Failed");
				}
			});
		}
		;
	});
};

function roomInterceptor(formName) {
	var $form = $("#" + formName);

	$form.on('submit', function(e) {
		e.preventDefault();
		if (validateRoom()) {
			$.ajax({
				url : "http://localhost:8080/admin/createRoom",
				type : "POST",
				data : $form.serialize(),
				success : function(response) {
					$("#ajaxLoadedContent").load("/admin/allRooms");
				},
				error : function(error) {
					$("#ajaxLoadedContent").load("/admin/roomNumberError");

				}
			});
		}
		;
	});
};

function reservationInterceptor(formName, roomId) {
	var $form = $("#" + formName);

	$form.on('submit', function(e) {
		e.preventDefault();
		$.ajax({
			url : "http://localhost:8080/guests/createReservation/" + roomId,
			type : "POST",
			data : $form.serialize(),
			success : function(response) {
				$("#ajaxLoadedContent").load("/guests/allUserReservations");
			},
			error : function(error) {
				alert("Failed");
			}
		});
	});
};

function redirectLogout() {
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/loggedout",
		beforeSend : function(xhr) {
			xhr.overrideMimeType("text/plain; charset=x-user-defined");
		},
		success : function(data) {
			window.location.href = "/";
		},
		error : function(error) {
			alert("Logout error");

		}
	});
}

function deleteCategory(categoryId) {
	if (confirm('Are you sure you want to clear this category?\nThis will affect all rooms and reservations related!')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/admin/deleteRoomCategory/"
					+ categoryId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectAllCategories();
			},
			error : function(error) {
				alert("Failed");
			}
		});
	}
	;
};

function deleteRoom(roomId) {
	if (confirm('Are you sure you want to clear this room?\nThis will affect all reservations related!')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/admin/deleteRoom/" + roomId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectAllRooms();
			},
			error : function(error) {
				alert("Failed");
			}
		});
	}
	;
};

function deleteReservation(reservationId) {
	if (confirm('Are you sure you want to cancel this reservation?')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/guests/deleteReservation/"
					+ reservationId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectUserReservations();
			},
			error : function(error) {
				alert("Failed");
			}
		});
	}
	;
};

function deleteReservationAdmin(reservationId) {
	if (confirm('Are you sure you want to remove this reservation?')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/admin/deleteReservation/"
					+ reservationId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectAllReservations();
			},
			error : function(error) {
				alert("Failed");
			}
		});
	}
	;
};

function deleteNote(noteId) {
	if (confirm('Are you sure you want to remove this note?')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/admin/deleteNote/" + noteId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectAllNotes();
			},
			error : function(error) {
				alert("Failed");
			}
		});
	}
	;
};

function deleteAllNotes() {
	if (confirm('Are you sure you want to clear all notes?')) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/admin/deleteAllNotes",
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectAllNotes();
			},
			error : function(error) {
				alert("Failed");
			}
		});
	}
	;
};

function deleteGuest(guestId) {
	if (confirm("Are you sure you want to remove this guest?\nIt will affect all related reservations!")) {
		$.ajax({
			type : "GET",
			url : "http://localhost:8080/admin/deleteGuest/" + guestId,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=x-user-defined");
			},
			success : function(data) {
				redirectAllGuests();
			},
			error : function(error) {
				alert("Failed");
			}
		});
	}
	;
};

function ValidatePassword() {
	var password = document.getElementById("password").value;
	var confirmpass = document.getElementById("confirmpass").value;
	if (password != confirmpass) {
		alert("Password does Not Match.");
		return false;
	}
	return true;
};

function validateRoomId() {
	var roomId = $("#roomId").val();
	var returnValue = true;

	if (roomId === "") {
		$("#roomIdError").css({
			"visibility" : "visible"
		});
		returnValue = false;
	} else {
		$("#roomIdError").css({
			"visibility" : "hidden"
		});
	}
	;

	return returnValue;
}

function validateNumber(e) {
	const pattern = /^\d{0,4}(\.\d{0,4})?$/g;

	return pattern.test(e.key)
};
