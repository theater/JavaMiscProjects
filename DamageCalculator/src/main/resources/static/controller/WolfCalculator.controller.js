sap.ui.define([ "sap/ui/core/mvc/Controller", "sap/ui/model/json/JSONModel", "DamageCalculator/util/RestUtil" ], function(
		Controller, JSONModel, RestUtil) {
	"use strict";

	return Controller.extend("DamageCalculator.controller.WolfCalculator", {
		onInit : function() {
			let oModel = new JSONModel();
			let oData = RestUtil.getData("/rest/wolf/defaultValues") 
			oModel.setProperty("/input", oData);
			this.getView().setModel(oModel, "wolf");
		},

		onCalculatePress : function() {
			let model = this.getView().getModel("wolf");
			let postBody = model.getProperty("/input");
			let that = this;
			let aData = jQuery.ajax({
				type: "POST",
				data: JSON.stringify(postBody),
				contentType: "application/json",
				url: "/rest/wolf/calculate",
				dataType: "json",
				async: false,
				success: function(data, textStatus, jqXHR) {
					let sharedModel = that.getOwnerComponent().getModel("sharedModel");
					sharedModel.setProperty("/armies", data.armies);
					sharedModel.setProperty("/visibilityWolfResult", true);
					sap.ui.core.UIComponent.getRouterFor(that).navTo("wolfResult");
				},
				error: function(error) {
					console.log(error);
				}
			});
		},

		onHomePress : function() {
			let oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("home");
		},
		
		formatInputAsJSON : function(data) {
			return JSON.stringify(data, undefined, 2);
		},

		onCodeEditorChange : function(event) {
			let valueAsString = event.getSource().getValue();
			let dataValue = JSON.parse(valueAsString);
			let model = this.getView().getModel("wolf");
			model.setProperty("/input", dataValue);
		}

	});

});