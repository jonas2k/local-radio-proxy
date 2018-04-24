package com.anconet.lrp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.anconet.lrp.controller.LRPController;
import com.anconet.lrp.model.ConfigurationItem;
import com.anconet.lrp.utils.XMLUtils;

public class App {

	private static List<String> arguments = new ArrayList<String>();

	public static void main(String[] args) {

		arguments = Arrays.asList(args);

		if (!arguments.isEmpty()) {

			init(new File(arguments.get(0)));

		} else if (configFileExists()) {

			init(new File("config.xml"));

		} else {
			System.err.println("No config file supplied.");
		}
	}

	private static void init(File configFile) {
		ConfigurationItem settings = XMLUtils.readConfigFile(configFile);

		LRPController lrpController = new LRPController(settings.getPort(), settings.getRadioStations(), settings);

		while (true) {
			lrpController.control();
		}
	}

	private static boolean configFileExists() {
		return new File("config.xml").exists();
	}
}