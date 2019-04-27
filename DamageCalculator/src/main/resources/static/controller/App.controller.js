sap.ui.define([ "sap/ui/core/mvc/Controller" ], function(
		Controller, MessageToast) {
	"use strict";

	return Controller.extend("DamageCalculator.controller.App", {
		
		onInit : function() {
			var sharedModel = this.getOwnerComponent().getModel("sharedModel");
			sharedModel.setProperty("/visibilityWolfResult", false);

			this.getView().setModel(sharedModel, "sharedModel");
		},
		
		wolfCalcPress : function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("wolf");
		},
		
		wolfCalcJsonPress : function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("wolfJson");
		},

		wolfResultPress : function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("wolfResult");
		},
		
		battleCalcPress : function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("battle");
		},
		
		battleCalcJsonPress : function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("battleJson");
		}
	});

});