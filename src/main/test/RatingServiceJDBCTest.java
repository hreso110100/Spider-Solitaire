import org.junit.Before;
import org.junit.Test;
import sk.tuke.gamestudio.server.service.RatingServiceJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RatingServiceJDBCTest extends RatingServiceTest {
    private static final String DELETE = "DELETE FROM score";

    private static final String URL = "jdbc:mysql://localhost:3306/gamestudio";
    private static final String USER = "root";
    private static final String PASS = "benqfp7ig";

    public RatingServiceJDBCTest() {
        super.ratingService = new RatingServiceJDBC();
    }

    @Before
    public void setUp() throws Exception {
        Connection c = DriverManager.getConnection(URL, USER, PASS);
        Statement s = c.createStatement();
        s.execute(DELETE);
    }

    @Test
    public void testAddRating() throws Exception {
        super.testAddRating();
    }

    @Test
    public void testGetRating() throws Exception {
        super.testGetRating();
    }


    @Test
    public void testGetAverageRating() throws Exception {
        super.testGetAverageRating();
    }

}
