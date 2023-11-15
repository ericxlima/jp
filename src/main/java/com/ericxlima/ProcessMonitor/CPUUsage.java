import java.io.BufferedReader;
import java.io.FileReader;

public class CPUUsage {
    public double measureCPUUsage(int pid) {
        try {
            FileReader fileReader = new FileReader("/proc/" + pid + "/stat");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            String[] tokens = line.split("\\s+");

            long utime = Long.parseLong(tokens[13]);
            long stime = Long.parseLong(tokens[14]);

            long total_time = utime + stime;
            long seconds = System.currentTimeMillis() / 1000L;

            double cpu_usage = 100.0 * ((double) total_time / seconds);

            bufferedReader.close();
            fileReader.close();

            return cpu_usage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1.0;
    }
}
