sap.ui.define([ "sap/ui/core/UIComponent",
		"sap/ui/model/resource/ResourceModel" ], function(UIComponent,
		ResourceModel) {
	"use strict";

	return UIComponent.extend("DamageCalculator.Component", {

		metadata : {
			manifest : "json",
		},
		init : function() {
			// call the init function of the parent
			UIComponent.prototype.init.apply(this, arguments);

			// create the views based on the url/hash
			this.getRouter().initialize();

			var i18nModel = new ResourceModel({
				bundleName : "DamageCalculator.i18n.i18n"
			});
			sap.ui.getCore().setModel(i18nModel, "i18n");
		}
	});
});