sap.ui.define([ "sap/ui/core/mvc/Controller", "sap/ui/model/json/JSONModel" ], function(
		Controller, JSONModel) {
	"use strict";

	return Controller.extend("DamageCalculator.controller.BattleCalculator", {
		onInit : function() {
			let oModel = new JSONModel();
			oModel.setProperty("/input", this.oData);
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
		
		oData : {
			attacker : {
				"castleLevel": 32,
				"troopsAmount": 253330,
				"useMarchCapacityBoost": false,
				"useMarchCapacitySpell": false,
				"maxTier": 10,
				"troopAttack": 599.39,
				"troopDefense": 594.39,
				"troopHealth": 632.45,
				"infantryAttack": 844.7,
				"infantryDefense": 1051.75,
				"infantryHealth": 1118.12,
				"cavalryAttack": 1031.85,
				"cavalryDefense": 995.25,
				"cavalryHealth": 931.25,
				"distanceAttack": 986.05,
				"distanceDefense": 989.25,
				"distanceHealth": 1081.25,
				"artilleryAttack": 182.5,
				"artilleryDefense": 174.5,
				"artilleryHealth": 182.5,
				"troopDamage": 114.5,
				"infantryDamage": 186.0,
				"cavalryDamage": 230.6,
				"distanceDamage": 193.1,
				"artilleryDamage": 40.5,
				"troopDamageReduction": 96.0,
				"infantryDamageReduction": 203.5,
				"cavalryDamageReduction": 186.0,
				"distanceDamageReduction": 201.0,
				"artilleryDamageReduction": 40.5,
				"distanceVsInfantryDamage": 100.54,
				"distanceVsCavalryDamage": 88.0,
				"distanceVsArtilleryDamage": 0.0,
				"cavalryVsInfantryDamage": 96.04,
				"cavalryVsDistanceDamage": 92.54,
				"cavalryVsArtilleryDamage": 0.0,
				"infantryVsDistanceDamageReduction": 96.04,
				"infantryVsCavalryDamageReduction": 96.04,
				"infantryVsArtilleryDamageReduction": 4.5,
				"army": {
					"DISTANCE": [
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						200,
						0,
						0
					],
					"INFANTRY": [
						5,
						10,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						253330,
						0,
						0
					],
					"CAVALRY": [
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0
					],
					"ARTILLERY": [
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0
					]
				}
			},
			defender : {
				"castleLevel": 32,
				"troopsAmount": 251460,
				"useMarchCapacityBoost": false,
				"useMarchCapacitySpell": false,
				"maxTier": 10,
				"troopAttack": 675.83,
				"troopDefense": 665.99,
				"troopHealth": 690.38,
				"infantryAttack": 1210.21,
				"infantryDefense": 1315.01,
				"infantryHealth": 1417.80,
				"cavalryAttack": 1388.04,
				"cavalryDefense": 1300.35,
				"cavalryHealth": 1394.35,
				"distanceAttack": 1293.77,
				"distanceDefense": 1324.31,
				"distanceHealth": 1365.85,
				"artilleryAttack": 348.95,
				"artilleryDefense": 321.95,
				"artilleryHealth": 311.95,
				"troopDamage": 124.9,
				"infantryDamage": 186.0,
				"cavalryDamage": 203.5,
				"distanceDamage": 229.3,
				"artilleryDamage": 40.5,
				"troopDamageReduction": 96.0,
				"infantryDamageReduction": 203.5,
				"cavalryDamageReduction": 186.0,
				"distanceDamageReduction": 201.0,
				"artilleryDamageReduction": 40.5,
				"distanceVsInfantryDamage": 100.54,
				"distanceVsCavalryDamage": 88.04,
				"distanceVsArtilleryDamage": 0.0,
				"cavalryVsInfantryDamage": 88.6,
				"cavalryVsDistanceDamage": 4.5,
				"cavalryVsArtilleryDamage": 80.6,
				"infantryVsDistanceDamageReduction": 88.6,
				"infantryVsCavalryDamageReduction": 8.0,
				"infantryVsArtilleryDamageReduction": 4.5,
				"army": {
					"DISTANCE": [
						20,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						83820,
						0,
						0
					],
					"INFANTRY": [
						10,
						20,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						83820,
						0,
						0
					],
					"CAVALRY": [
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						83820,
						0,
						0
					],
					"ARTILLERY": [
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0
					]
				}
			}
		}

	});

});