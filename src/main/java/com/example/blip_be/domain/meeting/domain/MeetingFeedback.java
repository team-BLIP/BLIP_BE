package com.example.blip_be.domain.meeting.domain;

import com.example.blip_be.domain.team.domain.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public MeetingFeedback(Meeting meeting, String feedback) {
        this.meeting = meeting;
        this.feedback = feedback;
    }
}

