
package com.derekhe.abnamro;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 * This class reads transaction file with fixed width and convert it to a list
 * of string.
 * </p>
 * 
 * @author derekhe
 *
 */
class TransactionReader {

	private static final Logger logger = LogManager.getLogger(TransactionReader.class);

	/**
	 * Return a list of string that every element in the list represent one
	 * transaction.
	 * 
	 * @param fileName
	 *            the transaction file path.
	 */
	public List<String> read(URI fileName) {

		List<String> result = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
			// br returns as stream and convert it into a List
			result = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			logger.error("Exception occured when reading transaction file!", e);
			throw new RuntimeException("Exception occured when reading transaction file!", e);
		}

		return result;
	}

}
