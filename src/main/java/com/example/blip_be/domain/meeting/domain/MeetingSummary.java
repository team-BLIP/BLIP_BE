package com.example.blip_be.domain.meeting.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column(columnDefinition = "LONGTEXT")
    private String summary;

    @Builder
    public MeetingSummary(Meeting meeting, String summary) {
        this.meeting = meeting;
        this.summary = summary;
    }
}