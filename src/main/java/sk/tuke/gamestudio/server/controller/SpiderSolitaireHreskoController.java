package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.webui.WebUI;
import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.server.service.CommentService;
import sk.tuke.gamestudio.server.service.RatingService;
import sk.tuke.gamestudio.server.service.ScoreService;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SpiderSolitaireHreskoController {

    @Autowired
    private WebUI webUI;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommentService commentService;

    //http://localhost:8080/spider-solitaire-hresko

    @RequestMapping("/spider-solitaire-hresko")
    public String spiderSolitaire(@RequestParam(value = "command", required = false) String command,
                                  @RequestParam(value = "sourceRow", required = false) String sourceRow,
                                  @RequestParam(value = "sourceRowIndex", required = false) String sourceRowIndex,
                                  @RequestParam(value = "destinationRow", required = false) String destinationRow,
                                  Model model) {

        webUI.processCommand(command, sourceRow, sourceRowIndex, destinationRow);

        model.addAttribute("score", webUI.getScore());
        model.addAttribute("steps", webUI.getSteps());
        model.addAttribute("renderStock", webUI.renderStock());
        model.addAttribute("renderFoundation", webUI.renderFoundations());
        model.addAttribute("renderError", webUI.renderError());
        model.addAttribute("renderDeck", webUI.renderDeck());
        model.addAttribute("scores", scoreService.getBestScores("spider-solitaire"));
        model.addAttribute("ratings", ratingService.getAverageRating("spider-solitaire"));
        model.addAttribute("comments", commentService.getComments("spider-solitaire"));

        return "spider-solitaire-hresko";
    }

    @RequestMapping("spider-solitaire-hresko-login")
    public String spiderSolitaireLogin() {
        return "spider-solitaire-hresko-login";
    }

    @RequestMapping("spider-solitaire-hresko-registration")
    public String spiderSolitaireRegistration() {
        return "spider-solitaire-hresko-registration";
    }

    @PostMapping(value = "spider-solitaire-hresko-score")
    public ResponseEntity createScore(@RequestBody Score score) {

        score = new Score(score.getPlayer(), webUI.getScore(), new Date());

        scoreService.addScore(score);

        return new ResponseEntity(score, HttpStatus.OK);
    }

    @PostMapping(value = "spider-solitaire-hresko-comment")
    public ResponseEntity createComment(@RequestBody Comment comment) {

        comment = new Comment(comment.getPlayer(), comment.getComment(), new Date());

        commentService.addComment(comment);

        return new ResponseEntity(comment, HttpStatus.OK);
    }


    @PostMapping(value = "spider-solitaire-hresko-rating")
    public ResponseEntity createComment(@RequestBody Rating rating) {

        rating = new Rating(rating.getPlayer(), rating.getRating(), new Date());

        ratingService.setRating(rating);

        return new ResponseEntity(rating, HttpStatus.OK);
    }
}
