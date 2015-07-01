
function getToken() {

	var urn = $('#urn').val();
	// Add configuration for one or more providers.
	jso_configure({
		"PaymentServiceHub" : {
			client_id : "restapp",
			redirect_uri : "http://localhost:8080/PaymentServiceHub/Vendor.html",
			authorization : "http://localhost:8080/PaymentServiceHub/oauth/authorize",
		}
	});
	// Perform a data request
	$.oajax({
		url : "http://localhost:8080/PaymentServiceHub/psh/tokens",
		jso_provider : "PaymentServiceHub", // Will match the config identifier
		jso_scopes : [ "read" ], // List of scopes (OPTIONAL)
		jso_allowia : true, // Allow user interaction (OPTIONAL, default: false)
		dataType : 'json',
		contentType : 'application/json',
		crossDomain : true,
		type : 'POST',
		data : JSON.stringify({
			"urn" : urn
		}),
		success : function(result) {
			console.log({
				response : result
			});
			$('#message').text(result.token);
		}
	});
	jso_wipe();
}