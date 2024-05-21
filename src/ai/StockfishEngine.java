package ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class StockfishEngine {

    private Process stockfishProcess;
    private BufferedReader reader;
    private PrintWriter writer;
    private ExecutorService executor;

    public StockfishEngine(String stockfishPath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(stockfishPath);
        stockfishProcess = processBuilder.start();
        reader = new BufferedReader(new InputStreamReader(stockfishProcess.getInputStream()));
        writer = new PrintWriter(stockfishProcess.getOutputStream(), true);
        executor = Executors.newSingleThreadExecutor();
        initializeEngine();
    }

    private void initializeEngine() throws IOException {
        sendCommand("uci");
        waitFor("uciok");
        sendCommand("isready");
        waitFor("readyok");
        sendCommand("ucinewgame");  // Initialize a new game
    }

    public void sendCommand(String command) {
        System.out.println("Sending command to Stockfish: " + command);
        writer.println(command);
        writer.flush();
    }

    public String getBestMove(String moveList) throws IOException {
        sendCommand("position startpos moves " + moveList);
        sendCommand("go movetime 1000");
        return waitForBestMove();
    }

    private String readLine() throws IOException {
        String line = reader.readLine();
        System.out.println("Read line: " + line);
        return line;
    }

    private void waitFor(String keyword) throws IOException {
        String line;
        while ((line = readLine()) != null) {
            if (line.contains(keyword)) {
                break;
            }
        }
    }

    private String waitForBestMove() throws IOException {
        String line;
        String bestMove = null;
        while ((line = readLine()) != null) {
            System.out.println("Stockfish Output: " + line);
            if (line.startsWith("bestmove")) {
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    bestMove = parts[1];
                }
                break;
            }
        }
        if (bestMove == null) {
            System.out.println("No best move found by Stockfish.");
        }
        return bestMove;
    }

    public void close() throws IOException {
        sendCommand("quit");
        reader.close();
        writer.close();
        executor.shutdown();
    }
}

