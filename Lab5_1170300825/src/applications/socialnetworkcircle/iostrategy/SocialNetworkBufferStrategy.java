package applications.socialnetworkcircle.iostrategy;

import applications.socialnetworkcircle.SocialNetworkCircle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import org.apache.log4j.Logger;
import otherdirectory.exception.CheckedException;

/**
 * .
 *
 * @author hahalidaxin
 */
public class SocialNetworkBufferStrategy implements SocialNetworkIoStrategy {
  private static Logger logger = Logger.getLogger(SocialNetworkIoStrategy.class.getName());
  Long outputTime;
  Long inputTime;

  @Override
  public void read(SocialNetworkCircle snc, String filepath) throws CheckedException {
    BufferedReader reader = null;
    Long timeStart = System.currentTimeMillis();
    try {
      InputStreamReader isr =
          new InputStreamReader(
              new FileInputStream(filepath), StandardCharsets.UTF_8);
      reader = new BufferedReader(isr);
      String line = reader.readLine();
      while (line != null) {
        if (line.length() == 0) {
          line = reader.readLine();
          continue;
        }
        line = line.trim();

        snc.matchInputLine(line);

        line = reader.readLine();
      }
      snc.processInputData();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (CheckedException e) {
      logger.error(e);
      throw e;
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    Long timeEnd = System.currentTimeMillis();
    inputTime = timeEnd-timeStart;
    logger.info("输入文件计时：" + (timeEnd - timeStart));
  }

  @Override
  public void write(SocialNetworkCircle snc, String filepath) {
    BufferedWriter writer = null;
    OutputStreamWriter osw = null;
    Long timeStart = System.currentTimeMillis();
    try {
      osw = new OutputStreamWriter(
          new FileOutputStream(filepath), StandardCharsets.UTF_8);
      writer = new BufferedWriter(osw);
      writer.write(snc.getCentralUserInfo() + "\n");
      for (String s : snc.getSocialTieInfo()) {
        writer.write(s + "\n");
      }
      for (String s : snc.getFriendInfo()) {
        writer.write(s + "\n");
      }
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
        if (osw != null) {
          osw.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    Long timeEnd = System.currentTimeMillis();
    outputTime = timeEnd-timeStart;
    logger.info("输出文件计时：" + (timeEnd - timeStart));
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
