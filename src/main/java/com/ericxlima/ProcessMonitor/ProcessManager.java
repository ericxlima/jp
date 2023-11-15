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
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
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

}
