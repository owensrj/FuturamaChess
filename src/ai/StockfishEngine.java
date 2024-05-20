package ai;

import java.io.*;

import javax.swing.JOptionPane;

public class StockfishEngine {
    private Process stockfishProcess;
    private BufferedReader reader;
    private BufferedWriter writer;

    public StockfishEngine(String pathToStockfish) {
        try {
            stockfishProcess = new ProcessBuilder(pathToStockfish).start();
            reader = new BufferedReader(new InputStreamReader(stockfishProcess.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(stockfishProcess.getOutputStream()));
        } catch (IOException e) {
            showError("Error starting Stockfish engine: " + e.getMessage());
        }
    }

    public String getBestMove(String fen, int difficulty) {
        try {
            sendCommand("uci");
            sendCommand("setoption name Skill Level value " + difficulty);
            sendCommand("position fen " + fen);
            sendCommand("go movetime 2000"); // Adjust movetime as needed
            
            String bestMove = null;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("bestmove")) {
                    bestMove = line.split(" ")[1];
                    break;
                }
            }
            return bestMove;
        } catch (IOException e) {
            showError("Error communicating with Stockfish: " + e.getMessage());
        }
        return null;
    }

    private void sendCommand(String command) throws IOException {
        writer.write(command + "\n");
        writer.flush();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void close() {
        try {
            sendCommand("quit");
            stockfishProcess.destroy();
            reader.close();
            writer.close();
        } catch (IOException e) {
            showError("Error closing Stockfish engine: " + e.getMessage());
        }
    }
}
