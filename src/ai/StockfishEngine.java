package ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * This class represents an interface to the Stockfish chess engine. It handles
 * communication with the Stockfish process to get chess moves.
 */

public class StockfishEngine {

	private Process stockfishProcess;
	private BufferedReader reader;
	private PrintWriter writer;
	private ExecutorService executor;

	/**
	 * Constructs a new StockfishEngine.
	 *
	 * @param stockfishPath The path to the Stockfish executable.
	 * @throws IOException if an I/O error occurs.
	 */
	public StockfishEngine(String stockfishPath) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder(stockfishPath);
		stockfishProcess = processBuilder.start();
		reader = new BufferedReader(new InputStreamReader(stockfishProcess.getInputStream()));
		writer = new PrintWriter(stockfishProcess.getOutputStream(), true);
		executor = Executors.newSingleThreadExecutor();
		initializeEngine();
	}

	/**
	 * Initializes the Stockfish engine by sending necessary commands.
	 *
	 * @throws IOException if an I/O error occurs.
	 */
	private void initializeEngine() throws IOException {
		sendCommand("uci"); // Send UCI (Universal Chess Interface) command
		waitFor("uciok"); // Wait for the engine to acknowledge UCI
		sendCommand("isready"); // Check if the engine is ready
		waitFor("readyok"); // Wait for the engine to be ready
		sendCommand("ucinewgame"); // Initialize a new game
	}

	/**
	 * Sends a command to the Stockfish engine using syntax expected by the API.
	 *
	 * @param command The command to send.
	 */
	public void sendCommand(String command) {
		System.out.println("Sending command to Stockfish: " + command);
		writer.println(command);
		writer.flush();
	}

	/**
	 * Gets the best move from the Stockfish engine for a given move list.
	 *
	 * @param moveList The list of moves in UCI format.
	 * @return The best move in UCI format.
	 * @throws IOException if an I/O error occurs.
	 */
	public String getBestMove(String moveList) throws IOException {
		sendCommand("position startpos moves " + moveList); // Set up the position with the given move list
		sendCommand("go movetime 750"); // Search for the best move with a time limit [ADJUST THIS FOR DIFFICULTY]
		return waitForBestMove(); // Wait for and return the best move
	}

	/**
	 * Reads a line of output from the Stockfish engine.
	 *
	 * @return The line of output.
	 * @throws IOException if an I/O error occurs.
	 */
	private String readLine() throws IOException {
		String line = reader.readLine();
		System.out.println("Read line: " + line);
		return line;
	}

	/**
	 * Waits for a specific keyword in the output from the Stockfish engine.
	 *
	 * @param keyword The keyword to wait for.
	 * @throws IOException if an I/O error occurs.
	 */
	private void waitFor(String keyword) throws IOException {
		String line;
		while ((line = readLine()) != null) {
			if (line.contains(keyword)) {
				break; // Exit loop when the keyword is found
			}
		}
	}

	/**
	 * Waits for the best move from the Stockfish engine.
	 *
	 * @return The best move in UCI format.
	 * @throws IOException if an I/O error occurs.
	 */
	private String waitForBestMove() throws IOException {
		String line;
		String bestMove = null;
		while ((line = readLine()) != null) {
			System.out.println("Stockfish Output: " + line);
			if (line.startsWith("bestmove")) {
				String[] parts = line.split(" ");
				if (parts.length > 1) {
					bestMove = parts[1]; // Extract the best move
				}
				break; // Exit loop after finding the best move
			}
		}
		if (bestMove == null) {
			System.out.println("No best move found by Stockfish.");
		}
		return bestMove;
	}

	/**
	 * Closes the Stockfish engine and releases resources.
	 *
	 * @throws IOException if an I/O error occurs.
	 */
	public void close() throws IOException {
		sendCommand("quit"); // Send quit command to Stockfish
		reader.close();
		writer.close();
		executor.shutdown(); // Shut down the executor service
	}
}
