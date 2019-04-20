sap.ui.define([
	"sap/m/Button",
	"sap/m/MessageToast"
], function (Button, MessageToast) {
	"use strict";

	sap.ui.require([
		"sap/ui/core/mvc/XMLView"
	], function (XMLView) {
		XMLView.create({viewName: "DamageCalculator.App"}).then(function (oView) {
			oView.placeAt("content");
		});
	});

});