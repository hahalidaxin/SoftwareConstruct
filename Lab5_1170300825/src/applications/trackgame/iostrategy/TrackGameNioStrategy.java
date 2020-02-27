package applications.trackgame.iostrategy;

import applications.trackgame.TrackGame;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.apache.log4j.Logger;
import otherdirectory.exception.CheckedException;

/**
 * .
 *
 * @author hahalidaxin
 */
public class TrackGameNioStrategy implements TrackGameIoStrategy {
  private static Logger logger = Logger.getLogger(TrackGameNioStrategy.class.getName());
  Long outputTime;
  Long inputTime;

  /**
   * .
   * 按行写入文件
   *
   * @param outCh  写通道
   * @param line   行数据
   * @param encode 编码
   */
  public static void writeByLine(FileChannel outCh, byte[] line, String encode) {
    try {
      ByteBuffer wbuf = ByteBuffer.allocate(line.length);
      wbuf.clear();
      wbuf.put(line);
      wbuf.flip();
      while (wbuf.hasRemaining()) {
        outCh.write(wbuf);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getLineString(byte[] line, String encode) {
    try {
      ByteBuffer wbuf = ByteBuffer.allocate(line.length);
      wbuf.clear();
      wbuf.put(line);
      wbuf.flip();
      return new String(line, 0, line.length, encode);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void read(TrackGame tg, String filepath) throws CheckedException {
    Long timeStart = System.currentTimeMillis();
    String encode = "UTF-8";
    FileChannel inCh = null;
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(filepath);
      inCh = fis.getChannel();
      ByteBuffer rbuf = ByteBuffer.allocate(1024);

      byte[] temp = new byte[0];//用来缓存上次读取剩下的部分
      int LF = "\n".getBytes()[0];//换行符
      while (inCh.read(rbuf) != -1) {
        int position = rbuf.position();
        byte[] rbyte = new byte[position];
        rbuf.flip();
        rbuf.get(rbyte);
        //判断是否有换行符
        int startnum = 0;
        for (int i = 0; i < rbyte.length; i++) {
          if (rbyte[i] == LF) {//若存在换行符
            byte[] line = new byte[temp.length + i - startnum + 1];
            System.arraycopy(temp, 0, line, 0, temp.length);
            System.arraycopy(rbyte, startnum, line, temp.length, i - startnum + 1);
            startnum = i + 1;
            temp = new byte[0];
            String lineString = getLineString(line, encode);
            if (lineString != null && !lineString.equals("\n")) {
              tg.matchInputLine(lineString);
            }
          }
        }
        if (startnum < rbyte.length) {//说明rbyte最后还剩不完整的一行
          byte[] temp2 = new byte[temp.length + rbyte.length - startnum];
          System.arraycopy(temp, 0, temp2, 0, temp.length);
          System.arraycopy(rbyte, startnum, temp2, temp.length, rbyte.length - startnum);
          temp = temp2;
        }
        rbuf.clear();
      }
      //兼容最后一行没有换行的情况
      if (temp.length > 0) {
        String lineString = getLineString(temp, encode);

        if (lineString != null && !lineString.equals("\n")) {
          tg.matchInputLine(lineString);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (CheckedException e) {
      logger.error(e);
      throw e;
    } finally {
      try {
        if (inCh != null) {
          inCh.close();
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
    String encode = "UTF-8";
    FileOutputStream outraf = null;
    FileChannel outCh = null;
    try {
      outraf = new FileOutputStream(filepath, false);
      outCh = outraf.getChannel();

      writeByLine(outCh, (tg.getGameTypesInfo() + "\n").getBytes(), encode);
      writeByLine(outCh, (tg.getNumofTracksInfo() + "\n").getBytes(), encode);
      for (String s : tg.getAllAtheltesInfo()) {
        writeByLine(outCh, (s + "\n").getBytes(), encode);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (outraf != null) {
          outraf.close();
        }
        if (outCh != null) {
          outCh.close();
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
