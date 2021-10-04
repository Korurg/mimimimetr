package com.korurg.mimimimetr.web.session;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.LinkedList;
import java.util.List;

@Data
public class VoteStatsSession {

    public static final String SESSION_ATTRIBUTE_NAME = "VOTE_STATS";

    //Пары котиков не могут повторяться, но не совсем понятно могут ли повторяться сами котики.
    //Допустим что нет, и тогда буду хранить не pair, а просто id котиков, которых уже видел пользователь.
    private List<Long> catsId;

    private Pair<Long,Long> nextCats;

    private boolean isVoted;

    public VoteStatsSession(){
        catsId = new LinkedList<>();
    }
}
