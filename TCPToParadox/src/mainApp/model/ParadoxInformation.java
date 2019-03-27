package mainApp.model;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainApp.communication.EvoCommunicator;
import mainApp.parser.IParadoxParser;
import mainApp.util.ParadoxUtil;

/**
 * The {@link ParadoxInformation} Class that provides the basic panel
 * information (serial number, panel type, application, hardware and bootloader
 * versions. It's the object representation of 37 bytes 0x72 serial response.
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
public class ParadoxInformation {

	private static Logger logger = LoggerFactory.getLogger(EvoCommunicator.class);

	private PanelType panelType;
	private String serialNumber;
	private Version applicationVersion;
	private Version hardwareVersion;
	private Version bootloaderVersion;

	public ParadoxInformation(byte[] panelInfoBytes, IParadoxParser parser) {
		panelType = parsePanelType(panelInfoBytes);

		applicationVersion = parser.parseApplicationVersion(panelInfoBytes);
		hardwareVersion = parser.parseHardwareVersion(panelInfoBytes);
		bootloaderVersion = parser.parseBootloaderVersion(panelInfoBytes);

		byte[] serialNumberBytes = Arrays.copyOfRange(panelInfoBytes, 12, 16);
		serialNumber = ParadoxUtil.byteArrayAsString(serialNumberBytes);
	}

	public PanelType getPanelType() {
		return panelType;
	}

	public Version getApplicationVersion() {
		return applicationVersion;
	}

	public Version getHardwareVersion() {
		return hardwareVersion;
	}

	public Version getBootLoaderVersion() {
		return bootloaderVersion;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	private PanelType parsePanelType(byte[] infoPacket) {
		if (infoPacket == null || infoPacket.length != 37) {
			return PanelType.UNKNOWN;
		}
		byte[] panelTypeBytes = Arrays.copyOfRange(infoPacket, 6, 8);
		String key = "0x" + ParadoxUtil.byteArrayAsString(panelTypeBytes);

		return ParadoxInformationConstants.panelTypesMapping.getOrDefault(key, PanelType.UNKNOWN);
	}

	@Override
	public String toString() {
		return "ParadoxInformation [panelType=" + panelType + ", serialNumber=" + serialNumber + ", applicationVersion="
				+ applicationVersion + ", hardwareVersion=" + hardwareVersion + ", bootloaderVersion="
				+ bootloaderVersion + "]";
	}

}
