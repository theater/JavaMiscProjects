sap.ui.define([], function() {
	"use strict";

	return {

		getData : function(url) {
			return jQuery.ajax({
				type: "GET",
				contentType: "application/json",
				url: url,
				dataType: "json",
				async: false,
				success: function(data, textStatus, jqXHR) {
					return data;
				},
				error: function(error) {
					console.log(error);
				}
			}).responseJSON;
		}
	}
});