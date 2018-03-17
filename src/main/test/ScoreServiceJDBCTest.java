import org.junit.Before;
import org.junit.Test;
import sk.tuke.gamestudio.spidersolitaire.services.ScoreServiceJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ScoreServiceJDBCTest extends ScoreServiceTest {

    private static final String DELETE = "DELETE FROM score";

    private static final String URL = "jdbc:mysql://localhost:3306/gamestudio";
    private static final String USER = "root";
    private static final String PASS = "benqfp7ig";

    public ScoreServiceJDBCTest() {
        super.scoreService = new ScoreServiceJDBC();
    }

    @Before
    public void setUp() throws Exception {
        Connection c = DriverManager.getConnection(URL, USER, PASS);
        Statement s = c.createStatement();
        s.execute(DELETE);
    }

    @Test
    public void testDbInit() throws Exception {
        super.testDbInit();
    }

    @Test
    public void testAddScore() throws Exception {
        super.testAddScore();
    }

    @Test
    public void testGestBestScores() throws Exception {
        super.testGestBestScores();
    }
}