package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.spidersolitaire.hresko.webui.WebUI;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SpiderSolitaireHreskoController {

    private WebUI webUI = new WebUI();

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
        model.addAttribute("renderDeck", webUI.renderDeck());

        return "spider-solitaire-hresko";
    }
}
