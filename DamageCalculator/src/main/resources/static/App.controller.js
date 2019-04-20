sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/m/MessageToast"
], function (Controller, MessageToast) {
	"use strict";

	return Controller.extend("DamageCalculator.App", {
		wolfCalcPress : function () {
			MessageToast.show("Handle go to Wolfie here...");
		},
		wolfCalcJsonPress : function () {
			MessageToast.show("Handle go to Wolfie JSON here...");
		},
		battleCalcPress : function () {
			MessageToast.show("Handle go to Battle calculator here...");
		}
	});

});