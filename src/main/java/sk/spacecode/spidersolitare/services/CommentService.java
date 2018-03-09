package sk.spacecode.spidersolitare.services;

import sk.spacecode.spidersolitare.entities.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws CommentException;
    List<Comment> getComments(String game) throws CommentException;
}