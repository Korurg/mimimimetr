package com.korurg.mimimimetr.web.controller;

import com.korurg.mimimimetr.data.entity.CatEntity;
import com.korurg.mimimimetr.exception.NotEnoughCatException;
import com.korurg.mimimimetr.service.api.CatService;
import com.korurg.mimimimetr.web.HtmlPageTemplates;
import com.korurg.mimimimetr.web.dto.CatDto;
import com.korurg.mimimimetr.web.mapper.CatMapper;
import com.korurg.mimimimetr.web.session.VoteStatsSession;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class CatController {

    private final CatService catService;
    private final CatMapper catMapper;

    @GetMapping()
    public String redirectToMainPage() {
        return HtmlPageTemplates.INDEX.redirect();
    }

    @GetMapping("/index")
    public String getMainPage(HttpSession session) {
        return HtmlPageTemplates.INDEX.getName();
    }

    @GetMapping("/top")
    public String getTop10Page(Model model) {
        List<CatDto> topCats = catService
                .getTopCats(10)
                .stream()
                .map(catMapper::toCatDto)
                .collect(Collectors.toList());

        model.addAttribute("cats", topCats);

        return HtmlPageTemplates.TOP.getName();
    }

    @GetMapping("/upload")
    public String getUploadPage() {
        return HtmlPageTemplates.UPLOAD.getName();
    }

    @PostMapping("/upload")
    public String uploadCat(@RequestParam("name") @NotBlank String name,
                            @RequestParam("imageUrl") @NotBlank String imageUrl) {

        CatEntity cat = CatEntity.builder()
                .name(name)
                .imageUrl(imageUrl)
                .score(0)
                .build();

        catService.createCat(cat);

        return HtmlPageTemplates.UPLOAD.getName();
    }

    @GetMapping("/vote")
    public String vote(Model model, HttpSession session) {
        VoteStatsSession voteStats = (VoteStatsSession) session.getAttribute(VoteStatsSession.SESSION_ATTRIBUTE_NAME);

        try {
            if (voteStats == null) {
                voteStats = new VoteStatsSession();
                Pair<CatEntity, CatEntity> newCats = null;

                newCats = catService.getNewPairCats(voteStats.getCatsId());
                voteStats.setNextCats(Pair.of(newCats.getFirst().getId(), newCats.getSecond().getId()));

            }

            if (voteStats.isVoted()) {
                Pair<CatEntity, CatEntity> newCats = catService.getNewPairCats(voteStats.getCatsId());
                voteStats.setNextCats(Pair.of(newCats.getFirst().getId(), newCats.getSecond().getId()));
                voteStats.setVoted(false);
            }

            CatEntity firstCat = catService.getById(voteStats.getNextCats().getFirst());
            CatEntity secondCat = catService.getById(voteStats.getNextCats().getSecond());

            CatDto firstCatForShow = catMapper.toCatDto(firstCat);
            CatDto secondCatForShow = catMapper.toCatDto(secondCat);

            model.addAttribute("firstCat", firstCatForShow);
            model.addAttribute("secondCat", secondCatForShow);

            session.setAttribute(VoteStatsSession.SESSION_ATTRIBUTE_NAME, voteStats);

        } catch (NotEnoughCatException e) {
            model.addAttribute("error", "На сегодня котики закончились");
        }

        return HtmlPageTemplates.VOTE.getName();
    }

    @PostMapping("/vote")
    public String vote(@RequestParam("catId") long votedCatId, HttpSession session) {
        VoteStatsSession voteStats = (VoteStatsSession) session.getAttribute(VoteStatsSession.SESSION_ATTRIBUTE_NAME);

        catService.incrementRating(votedCatId);

        Pair<Long, Long> prevCats = voteStats.getNextCats();

        voteStats.getCatsId().add(prevCats.getFirst());
        voteStats.getCatsId().add(prevCats.getSecond());

        voteStats.setVoted(true);

        session.setAttribute(VoteStatsSession.SESSION_ATTRIBUTE_NAME, voteStats);

        return HtmlPageTemplates.VOTE.redirect();
    }
}
