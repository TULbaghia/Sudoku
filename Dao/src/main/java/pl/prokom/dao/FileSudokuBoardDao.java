package pl.prokom.dao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import pl.prokom.model.board.SudokuBoard;

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
            this.fileName = fileName;
    }

    /**
     * Restoring all serialized data to an instance of SudokuBoard class.
     *
     * @return reference to {@code SudokuBoard}
     */
    @Override
    public SudokuBoard read() {
        SudokuBoard deserialized = null;
        Path filePath = Paths.get(fileName);
        try (ObjectInputStream iStream = new ObjectInputStream(Files.newInputStream(filePath))) {
            deserialized = (SudokuBoard) iStream.readObject();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Illegal class processed.");
        }
        return deserialized;
    }

    /**
     * Store chosen instance data to a file - serializing process.
     *
     * @param object is an instance of SudokuBoard, which is currenty being serialized
     */
    @Override
    public void write(SudokuBoard object) {
        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream oStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            oStream.writeObject(object);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
