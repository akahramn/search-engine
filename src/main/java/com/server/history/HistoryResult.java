package com.server.history;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryResult {
    Integer recordId;
    @NonNull
    String keyword;
    String link;
    @NonNull
    Integer frequency;
}
