package com.sparta.popupstore.domain.event.entity;

import com.sparta.popupstore.domain.common.entity.BaseEntity;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "events")
public class Event extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "popupstore_id")
    private PopupStore popupStore;

    private String title;
    private String description;
    private int discountPercentage;
    private int winnerCount;
    private int applicantCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    public Event(Long id, PopupStore popupStore, String title, String description, int discountPercentage, int winnerCount, int applicantCount, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.popupStore = popupStore;
        this.title = title;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.winnerCount = winnerCount;
        this.applicantCount = applicantCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
