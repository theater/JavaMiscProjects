sap.ui.define([
	"sap/ui/core/ComponentContainer"
], function (ComponentContainer) {
	"use strict";

	new ComponentContainer({
		name: "DamageCalculator",
		settings : {
			id : "calculator"
		},
		async: true
	}).placeAt("content");
});

