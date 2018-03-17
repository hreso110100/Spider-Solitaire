import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ScoreServiceJDBCTest.class,
        RatingServiceJDBCTest.class,
        CommentServiceJDBCTest.class
})

public class SpiderSolitaireTestSuite {
}
