package com.derekhe.abnamro;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * A CSV utility class which is used to write values to a CSV file.
 * 
 * @author derekhe
 *
 */
class CSVUtils {

	private static final char DEFAULT_SEPARATOR = ',';

	/**
	 * Write one line to a csv file. Values to be saved in csv file which separated
	 * by ',';
	 * 
	 * @param w
	 *            an instance of {@link Writer}
	 * @param values
	 *            values will be written to csv file
	 * @throws IOException
	 */
	public static void writeLine(Writer w, List<String> values) throws IOException {
		writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	}

	/**
	 * Format csv value see https://tools.ietf.org/html/rfc4180
	 */
	private static String followCVSformat(String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

	/**
	 * Write one line to a csv file. Values to be saved in csv file which separated
	 * by ',' if separator not specified.
	 * 
	 * @param w
	 *            an instance of {@link Writer}
	 * @param values
	 *            values will be written to csv file
	 * @param separator
	 *            used to separated values in csv file, by default it's ',' if not
	 *            specified
	 * @param customQuote
	 * @throws IOException
	 */
	public static void writeLine(Writer w, List<String> values, char separator, char customQuote) throws IOException {

		boolean first = true;

		if (separator == ' ') {
			separator = DEFAULT_SEPARATOR;
		}

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separator);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}
}