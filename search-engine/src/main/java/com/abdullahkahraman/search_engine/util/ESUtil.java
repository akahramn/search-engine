package com.abdullahkahraman.search_engine.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class ESUtil {

    public static Supplier<Query> buildQueryForKeyword(String keyword) {
        return () -> Query.of(q -> q.match(buildMatchQueryForKeyword(keyword)));
    }

    public static MatchQuery buildMatchQueryForKeyword(String keyword) {
        return new MatchQuery.Builder()
                .field("title")
                .query(keyword)
                .build();
    }
}
