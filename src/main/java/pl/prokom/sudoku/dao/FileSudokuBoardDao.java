package pl.prokom.sudoku.dao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import pl.prokom.sudoku.board.SudokuBoard;

/**
 * Class created to serialize/deserialize instances of SudokuBoard class.
 */
public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    /**
     * Stores a path to serialization process output/input file.
     */
    private String fileName;

    /**
     * Add name of the input/output serialization process file.
     *
     * @param fileName value that has to be assigned to this.fileName.
     */
    FileSudokuBoardDao(final String fileName) {
        try {
            this.fileName = fileName;
        } catch (Exception e) {
            System.out.println("Invalid filename.");
            e.printStackTrace();
        }
    }

    /**
     * Restoring all serialized data to an instance of SudokuBoard class.
     *
     * @return reference to {@code SudokuBoard}
     */
    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException {
        SudokuBoard deserialized = null;
        System.out.println(Paths.get(fileName));
        Path filePath = Paths.get(fileName);
        try (ObjectInputStream iStream = new ObjectInputStream(Files.newInputStream(filePath))) {
            deserialized = (SudokuBoard) iStream.readObject();
        } catch (IOException e) {
            System.out.println("Invalid file processing.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Illegal class processed.");
            e.printStackTrace();
        }
        return deserialized;
    }

    /**
     * Store chosen instance data to a file - serializing process.
     *
     * @param object is an instance of SudokuBoard, which is currenty being serialized
     */
    @Override
    public void write(SudokuBoard object) throws IOException {
        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream oStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            oStream.writeObject(object);
        } catch (IOException e) {
            System.out.println("Invalid file processing.");
            e.printStackTrace();
        }
    }

}
