sap.ui.define([ "sap/ui/core/mvc/Controller", "sap/m/MessageToast" ], function(
		Controller, MessageToast) {
	"use strict";

	return Controller.extend("DamageCalculator.controller.Home", {
		wolfCalcPress : function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("wolf");
		},
		wolfCalcJsonPress : function() {
			MessageToast.show("Handle go to Wolfie JSON here...");
		},
		battleCalcPress : function() {
			MessageToast.show("Handle go to Battle calculator here...");
		}
	});

});