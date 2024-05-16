import java.io.*;

public class Main {
    static int[] shiftSquares = {
            56, 57, 58, 59, 60, 61, 62, 63,
            48, 49, 50, 51, 52, 53, 54, 55,
            40, 41, 42, 43, 44, 45, 46, 47,
            32, 33, 34, 35, 36, 37, 38, 39,
            24, 25, 26, 27, 28, 29, 30, 31,
            16, 17, 18, 19, 20, 21, 22, 23,
            8, 9, 10, 11, 12, 13, 14, 15,
            0, 1, 2, 3, 4, 5, 6, 7};

    public static void main(String[] args) {
        fenToTensor(1000, 1, "output.txt", "tensos.txt");
    }

    static void fenToTensor(int statusUpdateIncrement, int amountLines, String inputFileName, String outputFileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(inputFileName));
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFileName));
            String lineIn = in.readLine();
            String[] split;
            int counter = 0;

            while (lineIn != null) {
                counter++;
                if (counter%statusUpdateIncrement == 0) {
                    System.out.println(counter + " Lines Processed!");
                }
                if (amountLines != -1) {
                    if (counter > amountLines) break;
                }

                split = lineIn.split(", ");
                out.write(boardToTensor(new Board(split[0])) + ", " + split[1]);
                out.newLine();
                lineIn = in.readLine();
            }
            out.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    static String boardToTensor(Board board) {
        String inputs = "";
        int fBase = shiftSquares[BitMethods.getLS1B(board.fKing)] * 64 * 5 * 2;
        int eBase = BitMethods.getLS1B(board.eKing) * 64 * 5 * 2 + 64 * 64 * 5 * 2;
        int currentPieceSquare;
        long currentBits;

        currentBits = board.fQueen;
        while (currentBits != 0) {
            currentPieceSquare = BitMethods.getLS1B(currentBits);
            currentBits &= ~(1L << currentPieceSquare);
            inputs += ", " + (fBase + shiftSquares[currentPieceSquare] * 10 + 2); //2 is the code for queen
            inputs += ", " + (eBase + currentPieceSquare * 10 + 2 + 5);
        }

        currentBits = board.eQueen;
        while (currentBits != 0) {
            currentPieceSquare = BitMethods.getLS1B(currentBits);
            currentBits &= ~(1L << currentPieceSquare);
            inputs += ", " + (fBase + shiftSquares[currentPieceSquare] * 10 + 2 + 5);
            inputs += ", " + (eBase + currentPieceSquare * 10 + 2);
            //2 is the code for queen and the second 2 is since it is an enemy queen
        }

        currentBits = board.fRook;
        while (currentBits != 0) {
            currentPieceSquare = BitMethods.getLS1B(currentBits);
            currentBits &= ~(1L << currentPieceSquare);
            inputs += ", " + (fBase + shiftSquares[currentPieceSquare] * 10 + 3);
            inputs += ", " + (eBase + currentPieceSquare * 10 + 3 + 5);
        }

        currentBits = board.eRook;
        while (currentBits != 0) {
            currentPieceSquare = BitMethods.getLS1B(currentBits);
            currentBits &= ~(1L << currentPieceSquare);
            inputs += ", " + (fBase + shiftSquares[currentPieceSquare] * 10 + 3 + 5);
            inputs += ", " + (eBase + currentPieceSquare * 10 + 3);
        }

        currentBits = board.fBishop;
        while (currentBits != 0) {
            currentPieceSquare = BitMethods.getLS1B(currentBits);
            currentBits &= ~(1L << currentPieceSquare);
            inputs += ", " + (fBase + shiftSquares[currentPieceSquare] * 10 + 4);
            inputs += ", " + (eBase + currentPieceSquare * 10 + 4 + 5);
        }

        currentBits = board.eBishop;
        while (currentBits != 0) {
            currentPieceSquare = BitMethods.getLS1B(currentBits);
            currentBits &= ~(1L << currentPieceSquare);
            inputs += ", " + (fBase + shiftSquares[currentPieceSquare] * 10 + 4 + 5);
            inputs += ", " + (eBase + currentPieceSquare * 10 + 4);
        }

        currentBits = board.fKnight;
        while (currentBits != 0) {
            currentPieceSquare = BitMethods.getLS1B(currentBits);
            currentBits &= ~(1L << currentPieceSquare);
            inputs += ", " + (fBase + shiftSquares[currentPieceSquare] * 10 + 5);
            inputs += ", " + (eBase + currentPieceSquare * 10 + 5 + 5);
        }

        currentBits = board.eKnight;
        while (currentBits != 0) {
            currentPieceSquare = BitMethods.getLS1B(currentBits);
            currentBits &= ~(1L << currentPieceSquare);
            inputs += ", " + (fBase + shiftSquares[currentPieceSquare] * 10 + 5 + 5);
            inputs += ", " + (eBase + currentPieceSquare * 10 + 5);
        }

        currentBits = board.fPawn;
        while (currentBits != 0) {
            currentPieceSquare = BitMethods.getLS1B(currentBits);
            currentBits &= ~(1L << currentPieceSquare);
            inputs += ", " + (fBase + shiftSquares[currentPieceSquare] * 10 + 6);
            inputs += ", " + (eBase + currentPieceSquare * 10 + 6 + 5);
        }

        currentBits = board.ePawn;
        while (currentBits != 0) {
            currentPieceSquare = BitMethods.getLS1B(currentBits);
            currentBits &= ~(1L << currentPieceSquare);
            inputs += ", " + (fBase + shiftSquares[currentPieceSquare] * 10 + 6 + 5);
            inputs += ", " + (eBase + currentPieceSquare * 10 + 6);
        }

        return inputs.substring(2);
    }
}