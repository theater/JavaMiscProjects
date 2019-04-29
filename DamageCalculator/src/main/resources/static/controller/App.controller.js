sap.ui.define([ "sap/ui/core/mvc/Controller" ], function(
		Controller, MessageToast) {
	"use strict";

	return Controller.extend("DamageCalculator.controller.App", {
		
		onInit : function() {
			let sharedModel = this.getOwnerComponent().getModel("sharedModel");
			sharedModel.setProperty("/visibilityWolfResult", false);
			sharedModel.setProperty("/visibilityBattleResult", false);

			this.getView().setModel(sharedModel, "sharedModel");
		},
		
		wolfCalcPress : function() {
			let oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("wolf");
		},
		
		wolfCalcJsonPress : function() {
			let oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("wolfJson");
		},

		wolfResultPress : function() {
			let oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("wolfResult");
		},
		
		battleCalcPress : function() {
			let oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("battle");
		},
		
		battleCalcJsonPress : function() {
			let oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("battleJson");
		},
		
		battleResultPress : function() {
			let oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("battleResult");
		}
	});

});