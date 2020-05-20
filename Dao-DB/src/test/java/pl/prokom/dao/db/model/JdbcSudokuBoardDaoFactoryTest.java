package pl.prokom.dao.db.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoFactoryTest {

    @Test
    public void DaoFactoryTestCase() {
        JdbcSudokuBoardDaoFactory factory = new JdbcSudokuBoardDaoFactory();
        assertNotNull(factory.getDbDao("TEST"));
    }

}