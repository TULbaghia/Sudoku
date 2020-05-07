package pl.prokom.dao.file.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import pl.prokom.dao.api.model.Dao;
import pl.prokom.dao.file.exception.DaoClassException;
import pl.prokom.dao.file.exception.DaoFileException;
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
    public SudokuBoard read() throws DaoFileException, DaoClassException {
        SudokuBoard deserialized = null;
        Path filePath = Paths.get(fileName);
        try (ObjectInputStream iStream = new ObjectInputStream(Files.newInputStream(filePath))) {
            deserialized = (SudokuBoard) iStream.readObject();
        } catch (IOException e) {
            throw new DaoFileException("Illegal file access.", e);
        }
          catch (ClassNotFoundException e) {
            throw new DaoClassException("Illegal class obtained.", e);
        }
        return deserialized;
    }

    /**
     * Store chosen instance data to a file - serializing process.
     *
     * @param object is an instance of SudokuBoard, which is currenty being serialized
     */
    @Override
    public void write(SudokuBoard object) throws DaoFileException {
        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream oStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            oStream.writeObject(object);
        } catch (IOException e) {
            throw new DaoFileException("Illegal file access.", e);
        }
    }

    @Override
    public void close() {}
}
