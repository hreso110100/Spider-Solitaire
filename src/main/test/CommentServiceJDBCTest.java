import org.junit.Before;
import org.junit.Test;
import sk.tuke.gamestudio.spidersolitaire.services.CommentServiceJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CommentServiceJDBCTest extends CommentServiceTest {

    private static final String DELETE = "DELETE FROM comment";

    private static final String URL = "jdbc:mysql://localhost:3306/gamestudio";
    private static final String USER = "root";
    private static final String PASS = "benqfp7ig";

    public CommentServiceJDBCTest() {
        super.commentService = new CommentServiceJDBC();
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
    public void testAddComment() throws Exception {
        super.testAddComment();
    }

    @Test
    public void testGetComments() throws Exception {
        super.testGetComments();
    }
}
