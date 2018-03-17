package sk.tuke.gamestudio.spidersolitaire.services;

import sk.tuke.gamestudio.spidersolitaire.entities.Comment;

import java.util.List;

/**
 * Simple interface with methods to add and get list of comments
 */

public interface CommentService {
    void addComment(Comment comment) throws CommentException;

    List<Comment> getComments(String game) throws CommentException;
}