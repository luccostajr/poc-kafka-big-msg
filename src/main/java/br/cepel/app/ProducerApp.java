package br.cepel.app;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import br.cepel.config.Constants;
import br.cepel.helper.Helper;
import br.cepel.pojo.CustomObject;
import br.cepel.producer.ProducerCreator;

public class ProducerApp {
  public static void main(String[] args) {
    runProducer();
  }

  static void runProducer() {
    long start = System.currentTimeMillis();

    Producer<Long, String> producer = ProducerCreator.createProducer();

    long minDiffTimeStamps = Long.MAX_VALUE;
    long maxDiffTimeStamps = Long.MIN_VALUE;

    Helper.writeToProducerFileLn("-= PRODUCER STARTED =-");
    Helper.writeToProducerFileLn("Now:\t\t" + Helper.getFormattedDateTime(start) + "\n");

    for (int index = 0; index < Constants.MESSAGE_COUNT; index++) {
      long now = System.currentTimeMillis();
      System.out.println("Creating CustomObject " + (index + 1) + " of " + Constants.MESSAGE_COUNT);
      CustomObject customObject = new CustomObject(Constants.TOPIC_NAME);
      String str = customObject.toString();
      Helper.writeToProducerFileLn(
          "CustomObject " + (index + 1) + " of " + Constants.MESSAGE_COUNT + ":\n" + str);
      ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(Constants.TOPIC_NAME,
          str);
      try {
        RecordMetadata metadata = producer.send(record).get();
        long end = System.currentTimeMillis();
        long diffTimeStamps = end - now;
        if (diffTimeStamps < minDiffTimeStamps) {
          minDiffTimeStamps = diffTimeStamps;
        } else if (diffTimeStamps > maxDiffTimeStamps) {
          maxDiffTimeStamps = diffTimeStamps;
        }
        Helper
            .writeToProducerFileLn("Record " + (index + 1) + " sent to partition " + metadata.partition()
                + " with offset " + metadata.offset() + " in " + diffTimeStamps + " ms\n");
      } catch (Throwable t) {
        Helper.writeToProducerFileLn("Error in sending record");
        Helper.writeToProducerFileLn(t.getMessage());
      }
    }
    long end = System.currentTimeMillis();
    Helper.writeToProducerFileLn("\nStatistics:");
    Helper.writeToProducerFileLn("\tTerminated at:\t" + Helper.getFormattedDateTime(end));
    Helper.writeToProducerFileLn("\tTotal time:\t" + (end - start) + " ms");
    Helper.writeToProducerFileLn("\tMin diff rec timestamps: " + minDiffTimeStamps + " ms");
    Helper.writeToProducerFileLn("\tMax diff rec timestamps: " + maxDiffTimeStamps + " ms");

    producer.close();
    Helper.closeProducerFile();
  }
}
