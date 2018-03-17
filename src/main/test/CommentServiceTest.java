import org.junit.Test;
import sk.tuke.gamestudio.spidersolitaire.entities.Comment;
import sk.tuke.gamestudio.spidersolitaire.services.CommentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommentServiceTest {

    CommentService commentService;

    private final String GAME_NAME = "spider-solitaire";
    private final String PLAYER_NAME = "TESTER_COMMENT";

    @Test
    public void testDbInit() throws Exception {
        assertEquals(0, commentService.getComments(GAME_NAME).size());
    }

    @Test
    public void testAddComment() throws Exception {
        Comment comment = new Comment(PLAYER_NAME, "This is just a test.", new Date());
        commentService.addComment(comment);
        assertEquals(1, commentService.getComments(GAME_NAME).size());
    }

    @Test
    public void testGetComments() throws Exception {
        Comment comment1 = new Comment(PLAYER_NAME, "Comment no.1", new Date());
        Comment comment2 = new Comment(PLAYER_NAME, "Comment no.2", new Date());
        List<Comment> expectedResult = new ArrayList<>();

        expectedResult.add(comment1);
        expectedResult.add(comment2);
        commentService.addComment(comment1);
        commentService.addComment(comment2);

        List<Comment> comments = commentService.getComments(GAME_NAME);

        assertEquals(expectedResult.size(), comments.size());
    }
}
