package br.cepel.helper;

import java.io.File;
import java.io.FileWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Helper {
	private static final Logger logger = LogManager.getLogger(Helper.class);

	private static final String FILE_PRODUCER_TXT = "big-message-producer.txt";
	private static final String FILE_CONSUMER_TXT = "big-message-consumer.txt";

	// create file object
	private static File fileProducer = new File(FILE_PRODUCER_TXT);
	private static FileWriter writerProducer = null;

	private static File fileConsumer = new File(FILE_CONSUMER_TXT);
	private static FileWriter writerConsumer = null;

	// write a string to the producer file
	public synchronized static void writeToProducerFile(String str) {
		try {
			if (writerProducer == null) {
				fileProducer.createNewFile();
				writerProducer = new FileWriter(fileProducer);
			}
			writerProducer.write(str);
			writerProducer.flush();
		} catch (Exception e) {
			logger.warn("*** Unable to write [" + str + "] to file: " + e.getMessage());
		}
	}

	// write a string to the prosucer file and add a new line
	public synchronized static void writeToProducerFileLn(String str) {
		try {
			if (writerProducer == null) {
				fileProducer.createNewFile();
				writerProducer = new FileWriter(fileProducer);
			}
			writerProducer.write(str + "\n");
			writerProducer.flush();
		} catch (Exception e) {
			logger.warn("*** Unable to write [" + str + "] to file: " + e.getMessage());
		}
	}

	// close the producer file
	public synchronized static void closeProducerFile() {
		try {
			writerProducer.close();
			writerProducer = null;
		} catch (Exception e) {
			logger.warn("*** Unable to close file: " + e.getMessage());
		}
	}

	// write a string to the consumer file
	public synchronized static void writeToConsumerFile(String str) {
		try {
			if (writerConsumer == null) {
				fileConsumer.createNewFile();
				writerConsumer = new FileWriter(fileConsumer);
			}
			writerConsumer.write(str);
			writerConsumer.flush();
		} catch (Exception e) {
			logger.warn("*** Unable to write [" + str + "] to file: " + e.getMessage());
		}
	}

	// write a string to the prosucer file and add a new line
	public synchronized static void writeToConsumerFileLn(String str) {
		try {
			if (writerConsumer == null) {
				fileConsumer.createNewFile();
				writerConsumer = new FileWriter(fileConsumer);
			}
			writerConsumer.write(str + "\n");
			writerConsumer.flush();
		} catch (Exception e) {
			logger.warn("*** Unable to write [" + str + "] to file: " + e.getMessage());
		}
	}

	// close the consumer file
	public synchronized static void closeConsumerFile() {
		try {
			writerConsumer.close();
			writerConsumer = null;
		} catch (Exception e) {
			logger.warn("*** Unable to close file: " + e.getMessage());
		}
	}

	public synchronized static String getFormattedDateTime(long now) {
		return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", now);
	}
}
