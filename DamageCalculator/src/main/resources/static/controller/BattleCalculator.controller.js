sap.ui.define([ "sap/ui/core/mvc/Controller", "sap/ui/model/json/JSONModel", "DamageCalculator/util/RestUtil" ], function(
		Controller, JSONModel, RestUtil) {
	"use strict";

	return Controller.extend("DamageCalculator.controller.BattleCalculator", {
		onInit : function() {
			let oModel = new JSONModel();
			let oData = RestUtil.getData("/rest/battle/defaultValues");
			oModel.setProperty("/input", oData);
			this.getView().setModel(oModel, "battle");
		},
		
		onCalculatePress : function() {
			let model = this.getView().getModel("battle");
			let postBody = model.getProperty("/input");
			let that = this;
			let aData = jQuery.ajax({
				type: "POST",
				data: JSON.stringify(postBody),
				contentType: "application/json",
				url: "/rest/battle/calculate",
				dataType: "json",
				async: false,
				success: function(data, textStatus, jqXHR) {
					let sharedModel = that.getOwnerComponent().getModel("sharedModel");
					sharedModel.setProperty("/battleResult", data);
					sharedModel.setProperty("/visibilityBattleResult", true);
					sap.ui.core.UIComponent.getRouterFor(that).navTo("battleResult");
				},
				error: function(error) {
					console.log(error);
				}
			});
		},

		formatInputAsJSON : function(data) {
			return JSON.stringify(data, undefined, 2);
		},

		onCodeEditorChange : function(event) {
			let eventSource = event.getSource();
			let id = eventSource.getIdForLabel().split("--")[1];
			let modifiedField = "attackerEditor" === id ? "attacker" : "defender";
			let valueAsString = eventSource.getValue();
			let dataValue = JSON.parse(valueAsString);
			let model = this.getView().getModel("battle");
			model.setProperty("/input/" +modifiedField, dataValue);
		},
		
	});

});