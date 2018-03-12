package sk.spacecode.spidersolitare.services;

import sk.spacecode.spidersolitare.entities.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    CREATE TABLE comment (
        player VARCHAR(64) NOT NULL,
        game VARCHAR(64) NOT NULL,
        comment VARCHAR(64) NOT NULL,
        commentedon TIMESTAMP NOT NULL
    );
     */

public class CommentServiceJDBC implements CommentService {

    private static final String URL = "jdbc:mysql://localhost:3306/gamestudio";
    private static final String USER = "root";
    private static final String PASSWORD = "benqfp7ig";

    private static final String INSERT_COMMENT =
            "INSERT INTO comment (player, game, comment, commentedon) VALUES (?, ?, ?, ?)";

    private static final String SELECT_COMMENTS =
            "SELECT * FROM comment WHERE game = 'spider-solitaire' ORDER BY commentedon DESC";

    @Override
    public void addComment(Comment comment) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_COMMENT)) {
                ps.setString(1, comment.getPlayer());
                ps.setString(2, comment.getGame());
                ps.setString(3, comment.getComment());
                ps.setDate(4, new Date(comment.getCommentedOn().getTime()));

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CommentException("Error saving comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMMENTS)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Comment comment;
                        comment = new Comment(
                                resultSet.getString(1),
                                resultSet.getString(3),
                                resultSet.getTimestamp(4)
                        );
                        comments.add(comment);
                    }
                }
            }
        } catch (SQLException e) {
            throw new CommentException("Error loading comments", e);
        }
        return comments;
    }
}
