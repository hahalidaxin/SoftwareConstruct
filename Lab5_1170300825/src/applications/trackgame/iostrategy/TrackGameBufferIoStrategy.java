package applications.trackgame.iostrategy;

import applications.trackgame.TrackGame;
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
public class TrackGameBufferIoStrategy implements TrackGameIoStrategy {
  private static Logger logger = Logger.getLogger(TrackGameIoStrategy.class.getName());
  Long outputTime;
  Long inputTime;

  @Override
  public void read(TrackGame tg, String filepath) throws CheckedException {
    Long timeStart = System.currentTimeMillis();
    BufferedReader reader = null;
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

        tg.matchInputLine(line);

        line = reader.readLine();
      }
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
    inputTime = timeEnd - timeStart;
    logger.info("输入时间计时：" + (timeEnd - timeStart));
  }

  @Override
  public void write(TrackGame tg, String filepath) {
    Long timeStart = System.currentTimeMillis();
    BufferedWriter writer = null;
    OutputStreamWriter osw = null;
    try {
      osw = new OutputStreamWriter(
          new FileOutputStream(filepath), StandardCharsets.UTF_8);
      writer = new BufferedWriter(osw);
      writer.write(tg.getGameTypesInfo() + "\n");
      writer.write(tg.getNumofTracksInfo() + "\n");
      for (String s : tg.getAllAtheltesInfo()) {
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
    outputTime = timeEnd - timeStart;
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
