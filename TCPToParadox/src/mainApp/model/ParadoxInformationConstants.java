package mainApp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link ParadoxInformationConstants} this is used for mapping the response
 * bytes with PanelType enum
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
public class ParadoxInformationConstants {
	public static Map<String, PanelType> panelTypesMapping = new HashMap<String, PanelType>();
	static {
		panelTypesMapping.put("0xA122", PanelType.EVO48);
		panelTypesMapping.put("0xA133", PanelType.EVO48);
		panelTypesMapping.put("0xA159", PanelType.EVO48);
		panelTypesMapping.put("0xA123", PanelType.EVO192);
		panelTypesMapping.put("0xA15A", PanelType.EVO192);
		panelTypesMapping.put("0xA16D", PanelType.EVOHD);
		panelTypesMapping.put("0xA41E", PanelType.SP5500);
		panelTypesMapping.put("0xA450", PanelType.SP5500);
		panelTypesMapping.put("0xA41F", PanelType.SP6000);
		panelTypesMapping.put("0xA451", PanelType.SP6000);
		panelTypesMapping.put("0xA420", PanelType.SP7000);
		panelTypesMapping.put("0xA452", PanelType.SP7000);
		panelTypesMapping.put("0xA524", PanelType.MG5000);
		panelTypesMapping.put("0xA526", PanelType.MG5050);
		panelTypesMapping.put("0xAE53", PanelType.SP4000);
		panelTypesMapping.put("0xAE54", PanelType.SP65);
	};

}
