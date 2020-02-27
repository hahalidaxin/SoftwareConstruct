package otherDirectory.logparser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 日志文件解析类
 */

public class LogParser {
//    static String LOGPATH = "out/debug.log";
    List<LogItem> logItemList = new ArrayList<>();

    public LogParser() {
        loadLogFile();
    }

    public void loadLogFile() {
        BufferedReader reader = null;
        File outFile = new File("out/");
        List<String> debugfiles = new ArrayList<>();
        if(outFile.exists()) {
            File[] arrayFile = outFile.listFiles();
            if(arrayFile==null) return ;
            for(File tfile:arrayFile) {
                if(tfile.isFile()) {
                    if(tfile.getName().startsWith("debug.log")) {
                        debugfiles.add(tfile.getPath());
                    }
                }
            }
        }
        try {
            for(String debugFile:debugfiles) {
//            reader = new BufferedReader(new FileReader(new File(LOGPATH)));
                InputStreamReader isr = new InputStreamReader(new FileInputStream(debugFile), StandardCharsets.UTF_8);
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
                    isr.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        logItemList.sort((x,y)->{return x.getTime().compareTo(y.getTime());});
    }

    int logCount;
    public String getFilterLogs(Predicate<LogItem> predicate) {
        List<LogItem> logs = logItemList.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        logs.forEach(sb::append);
        logCount=logs.size()*6;
        return sb.toString();
    }

    public int getLogCount() {
        return this.logCount;
    }

    public List<String> getAllClassNames() {
        Set<String> classnames = new HashSet<>();
        logItemList.forEach(it->classnames.add(it.className));
        return new ArrayList<>(classnames);
    }
    public List<String> getAllMethodNames() {
        Set<String> methodnames = new HashSet<>();
        logItemList.forEach(it->methodnames.add(it.methodName));
        return new ArrayList<>(methodnames);
    }
    public List<String> getAllETypes() {
        Set<String> etypes = new HashSet<>();
        logItemList.forEach(it->etypes.add(it.logType));
        return new ArrayList<>(etypes);
    }

}
