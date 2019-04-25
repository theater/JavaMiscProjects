sap.ui.define([ "sap/ui/core/mvc/Controller", "sap/ui/model/json/JSONModel" ], function(
		Controller, JSONModel) {
	"use strict";

	return Controller.extend("DamageCalculator.controller.WolfCalculator", {
		onInit : function() {
			var oData = {
					"castleLevel" : 31,
					"troopsAmount" : 222730,
					"useMarchCapacityBoost" : true,
					"useMarchCapacitySpell" : true,
					"maxTier" : 10,
					"troopAttack" : 572.84,
					"infantryAttack" : 765.5,
					"cavalryAttack" : 859.65,
					"distanceAttack" : 953.25,
					"artilleryAttack" : 170.5,
					"troopDamage" : 112,
					"infantryDamage" : 155.8,
					"cavalryDamage" : 208.45,
					"distanceDamage" : 186,
					"artilleryDamage" : 40.5,
					"distanceVsInfantryDamage" : 12.5,
					"cavalryVsInfantryDamage" : 8
				};
			var oModel = new JSONModel();
			oModel.setProperty("/input", oData);
			this.getView().setModel(oModel, "wolf");
		},

		onCalculatePress : function() {
			var model = this.getView().getModel("wolf");
			var postBody = model.getProperty("/input");
			var aData = jQuery.ajax({
				type: "POST",
				data: JSON.stringify(postBody),
				contentType: "application/json",
				url: "/rest/wolf/calculate",
				dataType: "json",
				async: false,
				success: function(data, textStatus, jqXHR) {
					model.setProperty("/armies", data.armies);
				},
				error: function(error) {
					console.log(error);
				}
			});
		},

		onHomePress : function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("home");
		},

		huio : function (data) {
			var data1=data;
			console.log(data);
		}

	});

});