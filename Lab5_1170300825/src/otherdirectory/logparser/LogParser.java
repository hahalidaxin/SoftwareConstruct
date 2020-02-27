package otherdirectory.logparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * .
 * 日志文件解析类
 */

public class LogParser {
  List<LogItem> logItemList = new ArrayList<>();
  int logCount;

  public LogParser() {
    loadLogFile();
  }

  /**
   * .
   * 加载文件
   */
  public void loadLogFile() {
    BufferedReader reader = null;
    File outFile = new File("out/");
    List<String> debugfiles = new ArrayList<>();
    if (outFile.exists()) {
      File[] arrayFile = outFile.listFiles();
      if (arrayFile == null) {
        return;
      }
      for (File tfile : arrayFile) {
        if (tfile.isFile()) {
          if (tfile.getName().startsWith("debug.log")) {
            debugfiles.add(tfile.getPath());
          }
        }
      }
    }
    try {
      for (String debugFile : debugfiles) {
        InputStreamReader isr = new InputStreamReader(
            new FileInputStream(debugFile), StandardCharsets.UTF_8);
        reader = new BufferedReader(isr);
        String line = reader.readLine();
        while (line != null) {
          if (line.length() == 0) {
            line = reader.readLine();
            continue;
          }
          logItemList.add(new LogItem(line.trim()));
          line = reader.readLine();
        }
        try {
          reader.close();
        } catch (IOException e) {
          System.out.println(e);
        }
        try {
          isr.close();
        } catch (IOException e) {
          System.out.println(e);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    logItemList.sort((x, y) -> {
      return x.getTime().compareTo(y.getTime());
    });
  }

  /**
   * .
   * 根据Predicate-LogItem 选择满足条件的日志
   * @param predicate 选择条件
   * @return 所有满足条件的日志的输出字串
   */
  public String getFilterLogs(Predicate<LogItem> predicate) {
    List<LogItem> logs = logItemList.stream()
        .filter(predicate)
        .collect(Collectors.toList());
    StringBuilder sb = new StringBuilder();
    logs.forEach(sb::append);
    logCount = logs.size() * 6;
    return sb.toString();
  }

  public int getLogCount() {
    return this.logCount;
  }

  /**
   * .
   * 获得所有的可能类名
   * @return 所有可能的类名
   */
  public List<String> getAllClassNames() {
    Set<String> classnames = new HashSet<>();
    logItemList.forEach(it -> classnames.add(it.className));
    return new ArrayList<>(classnames);
  }

  /**
   * .
   * 获得所有可能的方法名
   * @return 所有可能的方法名
   */
  public List<String> getAllMethodNames() {
    Set<String> methodnames = new HashSet<>();
    logItemList.forEach(it -> methodnames.add(it.methodName));
    return new ArrayList<>(methodnames);
  }

  /**
   * .
   * 获得所有可能的错误类型
   * @return 所有可能的错误类型
   */
  public List<String> getAllETypes() {
    Set<String> etypes = new HashSet<>();
    logItemList.forEach(it -> etypes.add(it.logType));
    return new ArrayList<>(etypes);
  }

}
