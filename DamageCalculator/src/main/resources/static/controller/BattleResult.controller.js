sap.ui.define([ "sap/ui/core/mvc/Controller", "sap/ui/model/json/JSONModel" ], function(Controller, JSONModel) {
	"use strict";

	return Controller.extend("DamageCalculator.controller.BattleResult", {
		onInit : function() {
			var sharedModel = this.getOwnerComponent().getModel("sharedModel");
			this.getView().setModel(sharedModel, "sharedModel");
		},
		
		formatBattleType : function(data) {
			var i18n = sap.ui.getCore().getModel("i18n").getResourceBundle();
			return i18n.getText(data);
		}
	});
});