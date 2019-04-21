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
			var oModel = new JSONModel(oData);
			this.getView().setModel(oModel);
		},

	});

});