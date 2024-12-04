package dev.timebetov.todo.models;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Task {

    private int id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    private boolean status;

    @NonNull
    private Timestamp dueTime;
}
