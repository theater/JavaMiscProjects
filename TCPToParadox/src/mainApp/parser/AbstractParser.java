package mainApp.parser;

import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainApp.model.Version;

public abstract class AbstractParser implements IParadoxParser {

	private static Logger logger = LoggerFactory.getLogger(AbstractParser.class);

	@Override
	public Version parseApplicationVersion(byte[] panelInfoBytes) {
		return new Version(panelInfoBytes[9], panelInfoBytes[10], panelInfoBytes[11]);
	}

	@Override
	public Version parseHardwareVersion(byte[] panelInfoBytes) {
		return new Version(panelInfoBytes[16], panelInfoBytes[17]);
	}

	@Override
	public Version parseBootloaderVersion(byte[] panelInfoBytes) {
		Date date = new GregorianCalendar( panelInfoBytes[24], panelInfoBytes[22], panelInfoBytes[21]).getTime();;
		return new Version(panelInfoBytes[18], panelInfoBytes[19], panelInfoBytes[20], date);
	}
}
