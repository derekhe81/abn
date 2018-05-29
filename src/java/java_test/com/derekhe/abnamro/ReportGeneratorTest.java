package com.derekhe.abnamro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.derekhe.abnamro.ReportGenerator;

public class ReportGeneratorTest {

	@Test
	public void testGenerate() {

		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			URI outputURI = ClassLoader.getSystemResource("com/derekhe/abnamro").toURI();

			URI inputUri = ClassLoader.getSystemResource("testinput.txt").toURI();
			String mainPath = Paths.get(outputURI).toString();
			Path output = Paths.get(mainPath, "testoutput.csv");

			reportGenerator.generate(inputUri, output.toString());

			String line = "";
			String cvsSplitBy = ",";

			try (BufferedReader br = new BufferedReader(new FileReader(output.toString()))) {
				int i = 0;

				while ((line = br.readLine()) != null) {
					if (i == 1) {
						// use comma as separator
						String[] transaction = line.split(cvsSplitBy);
						System.out.println("Info [clientInfo= " + transaction[0] + " , productInfo=" + transaction[1]
								+ ", amount=" + transaction[2] + "]");
						// check client info
						assertEquals("4567890123456789", transaction[0]);

						// check product info
						assertEquals("89016723456789012345", transaction[1]);

						// check transaction amount
						assertEquals(4567890123l - 3456789012l, Long.parseLong(transaction[2]));
					}

					i++;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (URISyntaxException e) {
			fail("Exception occured!");
		}
	}

	@Test
	public void testMalformedTransaction() {

		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			URI outputURI = ClassLoader.getSystemResource("com/derekhe/abnamro").toURI();

			URI inputUri = ClassLoader.getSystemResource("malformedInput.txt").toURI();
			String mainPath = Paths.get(outputURI).toString();
			Path output = Paths.get(mainPath, "malformendOutput.csv");

			reportGenerator.generate(inputUri, output.toString());
			try (BufferedReader br = new BufferedReader(new FileReader(output.toString()))) {

				int i = 0;
				String line = "";
				String cvsSplitBy = ",";

				while ((line = br.readLine()) != null) {
					if (i == 2) {
						// use comma as separator
						String[] transaction = line.split(cvsSplitBy);
						/**
						 * Don't calculate transaction amount, as QUANTITY LONG is not valid integer
						 */
						assertEquals(0l, Long.parseLong(transaction[2]));
					}
					if (i == 3) {
						// use comma as separator
						String[] transaction = line.split(cvsSplitBy);
						/**
						 * Don't calculate transaction amount, as QUANTITY short is not valid integer
						 */
						assertEquals(0l, Long.parseLong(transaction[2]));
					}

					i++;
				}

				/**
				 * should only generate three records as there are three valid transactions in
				 * malformedInput.txt . The reason there are two lines here as we should
				 * consider header as well.
				 */
				assertEquals(i, 4l);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (URISyntaxException e) {
			fail("Exception occured!");
		}
	}

}
