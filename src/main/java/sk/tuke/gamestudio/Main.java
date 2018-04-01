package sk.tuke.gamestudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.deck.ConsoleUI;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.deck.Deck;
import sk.tuke.gamestudio.server.service.*;

@Configuration
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public Deck deck() {
        return new Deck();
    }

    @Bean
    public ConsoleUI consoleUI() {
        return new ConsoleUI();
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
}
