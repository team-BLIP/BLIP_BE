package com.example.blip_be.domain.meeting.domain;

import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.domain.user.domain.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String topic;

    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingFeedback> feedbacks = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "meeting_user",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> participants = new ArrayList<>();

    @OneToOne(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private MeetingSummary meetingSummary;

    private boolean isStarted = false;

    private String roomUrl;

    @Builder
    public Meeting(String topic, LocalDateTime startTime, Team team, String fileUrl, String roomUrl) {
        this.topic = topic;
        this.startTime = startTime != null ? startTime : LocalDateTime.now();
        this.isStarted = true;
        this.team = team;
        this.fileUrl = fileUrl;
        this.roomUrl = roomUrl;
    }
    
    @AssertTrue(message = "종료 시간은 시작 시간보다 뒤여야 합니다.")
    private boolean isEndTimeAfterStartTime() {
        return startTime == null || endTime == null || endTime.isAfter(startTime);
    }

    public void endMeeting(String fileUrl, LocalDateTime endTime) {
        this.fileUrl = fileUrl;
        this.endTime = endTime;
    }

    public void applyAnalysisResult(String summary, String feedback) {
        this.meetingSummary = new MeetingSummary(this, summary);
        this.feedbacks.add(new MeetingFeedback(this, feedback));
    }
}