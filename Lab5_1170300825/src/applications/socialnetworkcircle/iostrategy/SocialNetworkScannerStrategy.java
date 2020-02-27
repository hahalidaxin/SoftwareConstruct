package applications.socialnetworkcircle.iostrategy;

import applications.socialnetworkcircle.SocialNetworkCircle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import org.apache.log4j.Logger;
import otherdirectory.exception.CheckedException;

/**
 * .
 *
 * @author hahalidaxin
 */
public class SocialNetworkScannerStrategy implements SocialNetworkIoStrategy {
  private static Logger logger = Logger.getLogger(SocialNetworkIoStrategy.class.getName());
  Long outputTime;
  Long inputTime;

  @Override
  public void read(SocialNetworkCircle snc, String filepath) throws CheckedException {
    Long timeStart = System.currentTimeMillis();
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File(filepath));
      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        if (line.length() == 0) {
          continue;
        }
        line = line.trim();
        snc.matchInputLine(line);
      }
      snc.processInputData();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (CheckedException e) {
      logger.error(e);
      throw e;
    } finally {
      if (scanner != null) {
        scanner.close();
      }
    }
    Long timeEnd = System.currentTimeMillis();
    inputTime = timeEnd-timeStart;
    logger.info("输入时间计时：" + (timeEnd - timeStart));
  }

  @Override
  public void write(SocialNetworkCircle snc, String filepath) {
    Long timeStart = System.currentTimeMillis();
    OutputStream outputStream = null;
    PrintWriter writer = null;
    try {
      outputStream = new FileOutputStream(filepath);
      writer = new PrintWriter(outputStream);
      writer.println(snc.getCentralUserInfo());
      snc.getSocialTieInfo().forEach(writer::println);
      snc.getFriendInfo().forEach(writer::println);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (outputStream != null) {
          outputStream.close();
        }
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    Long timeEnd = System.currentTimeMillis();
    outputTime = timeEnd-timeStart;
    logger.info("输出时间计时：" + (timeEnd - timeStart));
  }

  @Override
  public Long getOutputTime() {
    return outputTime;
  }

  @Override
  public Long getInputTime() {
    return inputTime;
  }
}
