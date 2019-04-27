sap.ui.define([ "sap/ui/core/mvc/Controller" ], function(
		Controller) {
	"use strict";

	return Controller.extend("DamageCalculator.controller.WolfResult", {
		onInit : function() {
			var sharedModel = this.getOwnerComponent().getModel("sharedModel");
			this.getView().setModel(sharedModel, "sharedModel");
		}
	});
});