import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessManager {
    public String getOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return "windows";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            return "unix";
        } else {
            return "unknown";
        }
    }

    public void listProcesses() {
        String os = getOS();
        if ("unix".equals(os)) {
            try {
                Process process = new ProcessBuilder("ps", "-e").start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                boolean skipFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (skipFirstLine) {
                        skipFirstLine = false;
                        continue;
                    }

                    System.out.println(line);
                    String[] tokens = line.trim().split("\\s+");
                    if (tokens.length > 3) {
                        try {
                            int pid = Integer.parseInt(tokens[0]);
                            String processName = tokens[tokens.length - 1];

                            java.io.File file = new java.io.File("/proc/" + pid + "/");
                            if (file.exists()) {
                                measureCPUUsage(pid, processName);
                            } else {
                                System.out.println("Processo " + pid + " não está mais ativo.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Erro ao converter PID para número inteiro: " + e.getMessage());
                        }
                    }
                }
                int exitCode = process.waitFor();
                System.out.println("Comando executado com código de saída: " + exitCode);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Este método só pode ser executado em sistemas Unix/Linux/Mac.");
        }
    }

    public void measureCPUUsage(int pid, String processName) {
        CPUUsage cpuUsage = new CPUUsage();
        double cpuUsageResult = cpuUsage.measureCPUUsage(pid);
        if (cpuUsageResult != -1.0) {
            System.out.println("Uso de CPU do processo " + pid + " (" + processName + "): " + cpuUsageResult + "%");
        } else {
            System.out.println("Erro ao medir o uso de CPU para o processo " + pid);
        }
    }
}
