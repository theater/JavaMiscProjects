sap.ui.define([ "sap/ui/core/mvc/Controller", "sap/m/MessageToast" ], function(
		Controller, MessageToast) {
	"use strict";

	return Controller.extend("DamageCalculator.controller.App", {
		wolfCalcPress : function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("wolf");
		},
		wolfCalcJsonPress : function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("wolfJson");
		},
		battleCalcPress : function() {
			MessageToast.show("Handle go to Battle calculator here...");
		}
	});

});