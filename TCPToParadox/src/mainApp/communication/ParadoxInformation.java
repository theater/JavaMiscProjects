package mainApp.communication;

import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainApp.ParadoxUtil;
import mainApp.messages.PanelType;

public class ParadoxInformation {

	private static Logger logger = LoggerFactory.getLogger(EvoCommunicator.class);

	PanelType panelType;

	ParadoxInformation(byte[] infoPacket) {
		panelType = calculatePanelType(infoPacket);
		logger.debug("Pannel type: {}", panelType);
	}

	private PanelType calculatePanelType(byte[] infoPacket) {
		if (infoPacket == null || infoPacket.length != 37) {
			return PanelType.UNKNOWN;
		}
		byte[] panelTypeBytes = Arrays.copyOfRange(infoPacket, 6, 8);
		String key = ParadoxUtil.byteArrayAsString(panelTypeBytes);
		logger.debug("To String: {}", key);

		return ParadoxInformationConstants.panelTypes.getOrDefault(key, PanelType.UNKNOWN);
	}
}
