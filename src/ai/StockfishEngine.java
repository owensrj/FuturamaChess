package ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class StockfishEngine {

    private Process stockfishProcess;
    private BufferedReader reader;
    private PrintWriter writer;

    public StockfishEngine(String stockfishPath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(stockfishPath);
        stockfishProcess = processBuilder.start();
        reader = new BufferedReader(new InputStreamReader(stockfishProcess.getInputStream()));
        writer = new PrintWriter(stockfishProcess.getOutputStream(), true);
    }

    public String sendCommand(String command) throws IOException {
        writer.println(command);
        writer.flush();
        return reader.readLine();
    }

    public String getBestMove(String fen) throws IOException {
        sendCommand("position fen " + fen);
        sendCommand("go movetime 2000");  // Adjust the time as needed
        String response;
        while ((response = reader.readLine()) != null) {
            if (response.startsWith("bestmove")) {
                return response.split(" ")[1];
            }
        }
        return null;
    }

    public void close() throws IOException {
        sendCommand("quit");
        reader.close();
        writer.close();
    }
}
