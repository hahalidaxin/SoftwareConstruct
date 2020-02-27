package applications.trackgame.iostrategy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * .
 *
 * @author hahalidaxin
 */
public class iotest {
  /**
   * 按行读取文件
   *
   * @param inCh   读通道
   * @param rbuf   读缓冲区
   * @param outCh  写通道
   * @param wbuf   写缓冲区
   * @param encode 编码
   */
  public static void readByLine(FileChannel inCh, ByteBuffer rbuf, FileChannel outCh, ByteBuffer wbuf, String encode) {
    try {
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
            writeByLine(outCh, wbuf, line, encode);
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
        writeByLine(outCh, wbuf, temp, encode);

      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        inCh.close();
        outCh.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 按行写入文件
   *
   * @param outCh  写通道
   * @param wbuf   写缓冲区
   * @param line   行数据
   * @param encode 编码
   */
  public static void writeByLine(FileChannel outCh, ByteBuffer wbuf, byte[] line, String encode) {
    try {
      wbuf = ByteBuffer.allocate(line.length);
      wbuf.clear();
      wbuf.put(line);
      wbuf.flip();
      System.out.print(new String(line, 0, line.length, encode));
      while (wbuf.hasRemaining()) {
        outCh.write(wbuf);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    try {
      RandomAccessFile inraf = new RandomAccessFile("src/configFile/TrackGame.txt", "r");
      RandomAccessFile outraf = new RandomAccessFile("out/output/test.txt", "rw");
      FileChannel inch = inraf.getChannel();
      FileChannel outch = outraf.getChannel();
      ByteBuffer rbuf = ByteBuffer.allocate(1024);
      ByteBuffer wbuf = ByteBuffer.allocate(1024);
      readByLine(inch, rbuf, outch, wbuf, "GBK");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
