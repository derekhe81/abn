package com.derekhe.abnamro;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportGenerator {

	private static final Logger logger = LogManager.getLogger(ReportGenerator.class);

	private static final int TRANSACTION_WIDTH = 176;

	private static final String INTEGER_RE = "^\\d+$";

	/**
	 * Generate a transaction report for every product.
	 * 
	 * @param input
	 *            the path for the input.txt.
	 * @param output
	 *            the path for the output.csv.
	 */
	public void generate(URI input, String output) {

		TransactionReader reader = new TransactionReader();
		List<String> transactionList = reader.read(input);
		FileWriter writer = null;

		try {
			writer = new FileWriter(output);
			// create Headers
			CSVUtils.writeLine(writer,
					Arrays.asList("Client_Information", "Product_Information", "Total_Transaction_Amount"));

			for (String str : transactionList) {
				if (str.length() != TRANSACTION_WIDTH) {
					logger.warn(
							"Transaction width is not correct, won't generate report for this transaction : " + str);
					continue;
				}

				List<String> results = new ArrayList<String>();
				List<Information> infoList = (new ReportConfigParser()).parse();

				for (Information info : infoList) {
					// calculate transaction value when mode is calculation.
					if ("calculation".equals(info.getMode())) {
						Long resultValue = calculate(str, info);
						results.add(resultValue.toString());
					} else {
						// concat item value to represent information e.g: client info.
						StringBuilder infoStr = new StringBuilder();
						for (Item item : info.getItems()) {
							infoStr.append(str.substring(item.getBeginIndex() - 1, item.getEndIndex()));
						}
						results.add(infoStr.toString());
					}

				}

				// write one transaction to csv file.
				CSVUtils.writeLine(writer, results);
			}
		} catch (IOException | XMLStreamException e) {
			logger.error("Exception occured when generating report!", e);
			throw new RuntimeException("Exception occured when generating report!", e);
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				logger.error("Exception occured when closing file!", e);
				throw new RuntimeException("Exception occured when closing file!", e);
			}

		}

	}

	/**
	 * The value of information is calculated based on the config in
	 * report_config.xml.
	 * 
	 * @param transaction
	 * @param info
	 * @return
	 */
	private static Long calculate(String transaction, Information info) {
		Long finalValue = 0l;
		for (int i = 0; i < info.getItems().size(); i = i + 2) {

			Item signItem = info.getItems().get(i);
			Item valueItem = info.getItems().get(i + 1);

			String signStr = transaction.substring(signItem.getBeginIndex() - 1, signItem.getEndIndex());
			logger.debug("sign is " + signStr);

			Integer sign = 0;
			if (signStr != null && !signStr.trim().isEmpty()) {
				sign = Integer.parseInt(transaction.substring(signItem.getBeginIndex() - 1, signItem.getEndIndex()));
			}

			String value = transaction.substring(valueItem.getBeginIndex() - 1, valueItem.getEndIndex());
			if (!value.matches(INTEGER_RE)) {
				logger.warn("Cannot do calculation as this value [" + value + "] is not an integer");
				finalValue = 0l;
				break;
			}

			Long itemValue = 0l;
			// value is positive if sign is greater than 1
			if (sign >= 1) {
				itemValue = Long.parseLong("-" + value);
			} else {
				itemValue = Long.parseLong(value);
			}

			logger.debug("itemValue is " + itemValue);

			switch (valueItem.getOperator()) {
			case "+": {
				finalValue = finalValue + itemValue;
				break;
			}
			case "-": {
				finalValue = finalValue - itemValue;
				break;
			}
			case "*": {
				finalValue = finalValue * itemValue;
				break;
			}
			case "/": {
				finalValue = finalValue / itemValue;
				break;
			}
			}
		}
		return finalValue;
	}

	public static void main(String[] args) throws URISyntaxException {
		URI uri = ClassLoader.getSystemResource("Input.txt").toURI();
		ReportGenerator reportGenerator = new ReportGenerator();
		reportGenerator.generate(uri, "c:\\report\\output.csv");
	}
}
