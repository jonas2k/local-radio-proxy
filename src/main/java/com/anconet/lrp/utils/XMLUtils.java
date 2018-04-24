package com.anconet.lrp.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.anconet.lrp.model.ConfigurationItem;
import com.anconet.lrp.model.RadioStation;

public class XMLUtils {

	public static ConfigurationItem readConfigFile(File configFile) {

		SAXBuilder saxBuilder = new SAXBuilder();

		Document doc;

		try {

			doc = saxBuilder.build(configFile);

			Element settings = doc.getRootElement().getChild("settings");
			Element radiostations = doc.getRootElement().getChild("radiostations");

			ConfigurationItem configurationItem = new ConfigurationItem();
			readSettings(settings, configurationItem);

			readRadioStations(radiostations, configurationItem);

			return configurationItem;

		} catch (JDOMException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void readSettings(Element settings, ConfigurationItem configurationItem) {

		configurationItem.setPort(Integer.parseInt(settings.getChildText("port")));
		configurationItem
				.setDefaultUserAgentHeader(new BasicHeader("User-Agent", settings.getChildText("defaultuseragent")));
		configurationItem.setDefaultRefererHeader(new BasicHeader("Referer", settings.getChildText("defaultreferer")));
		configurationItem.setProxyMode(settings.getChildText("proxymode").equals("true") ? true : false);
		configurationItem.setProxyHost(settings.getChildText("proxyhost"));
		configurationItem.setProxyPort(Integer.parseInt(settings.getChildText("proxyport")));
	}

	private static void readRadioStations(Element radiostations, ConfigurationItem configurationItem)
			throws MalformedURLException {

		List<Element> radioStationElements = radiostations.getChildren("radiostation");

		for (Element e : radioStationElements) {

			Header userAgent = e.getChildText("user-agent") != null
					? new BasicHeader("User-Agent", e.getChildText("user-agent"))
					: configurationItem.getDefaultUserAgentHeader();
			Header referer = e.getChildText("referer") != null ? new BasicHeader("Referer", e.getChildText("referer"))
					: configurationItem.getDefaultRefererHeader();

			configurationItem
					.addRadioStation((new RadioStation(e.getChildText("name"), new URL(e.getChildText("address")),
							e.getChildText("icy").equals("yes") ? true : false, userAgent, referer)));
		}
	}
}