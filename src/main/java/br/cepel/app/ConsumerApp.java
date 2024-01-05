package br.cepel.app;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import br.cepel.config.Constants;
import br.cepel.consumer.ConsumerCreator;
import br.cepel.helper.Helper;

public class ConsumerApp {
  public static void main(String[] args) {
    runConsumer();
  }

  private static int count = 0;
  //private static long start = System.currentTimeMillis();
  private static long now = System.currentTimeMillis();
  private static long end = System.currentTimeMillis();
  private static Long lastTimestamp = 0L;
  private static Long minDiffTimeStamps = Long.MAX_VALUE;
  private static Long maxDiffTimeStamps = Long.MIN_VALUE;
  private static Long minProcTimeStamps = Long.MAX_VALUE;
  private static Long maxProcTimeStamps = Long.MIN_VALUE;
  private static Long diffProcTimeStamps = -1L;

  private static void runConsumer() {
    long start = System.currentTimeMillis();

    Consumer<Long, String> consumer = ConsumerCreator.createConsumer();

    int noMessageFound = 0;

    Helper.writeToConsumerFileLn("-= CONSUMER STARTED =-");
    Helper.writeToConsumerFileLn("Now:\t\t" + Helper.getFormattedDateTime(now) + "\n");

    count = 0;
    while (true) {
      now = System.currentTimeMillis();
      // time in milliseconds consumer will wait if no record is found at broker
      ConsumerRecords<Long, String> consumerRecords = consumer.poll(Constants.CONSUMER_POOL_TIMEOUT);

      if (consumerRecords.count() == 0) {
        noMessageFound++;
        if (noMessageFound > Constants.MAX_NO_MESSAGE_FOUND_COUNT)
          // If no message found count is reached to threshold exit loop
          break;
        else
          continue;
      }

      // print each record.
      consumerRecords.forEach(record -> {
        Helper.writeToConsumerFileLn("\nRecord number:\t" + ++count);

        long timestamp = record.timestamp();
        Helper.writeToConsumerFileLn("Record timestamp:\t" + Helper.getFormattedDateTime(timestamp));
        if (lastTimestamp != 0L) {
          long diffTimeStamps = timestamp - lastTimestamp;
          if (diffTimeStamps < minDiffTimeStamps) {
            minDiffTimeStamps = diffTimeStamps;
          } else if (diffTimeStamps > maxDiffTimeStamps) {
            maxDiffTimeStamps = diffTimeStamps;
          }

          Helper
              .writeToConsumerFileLn("Last timestamp:\t" + Helper.getFormattedDateTime(lastTimestamp));
          Helper.writeToConsumerFileLn("Diff timestamps:\t" + diffTimeStamps + " ms");
        }

        Helper.writeToConsumerFileLn("Record value:\t" + record.value());

        lastTimestamp = timestamp;
        end = System.currentTimeMillis();
        diffProcTimeStamps = end - now;
        if (diffProcTimeStamps < minProcTimeStamps) {
          minProcTimeStamps = diffProcTimeStamps;
        } else if (diffProcTimeStamps > maxProcTimeStamps) {
          maxProcTimeStamps = diffProcTimeStamps;
        }
      });

      // commits the offset of record to broker.
      consumer.commitAsync();
    }

    consumer.close();

    Helper.writeToConsumerFileLn("\nStatistics:");
    Helper.writeToConsumerFileLn("\tTerminated at:\t" + Helper.getFormattedDateTime(end));
    Helper.writeToConsumerFileLn("\tTotal time:\t" + (end - start) + " ms");
    Helper.writeToConsumerFileLn("\tMin diff rec timestamps:  " + minDiffTimeStamps + " ms");
    Helper.writeToConsumerFileLn("\tMax diff rec timestamps:  " + maxDiffTimeStamps + " ms");
    Helper.writeToConsumerFileLn("\tMin diff proc timestamps: " + minProcTimeStamps + " ms");
    Helper.writeToConsumerFileLn("\tMax diff proc timestamps: " + maxProcTimeStamps + " ms");
    Helper.closeConsumerFile();
  }
}
