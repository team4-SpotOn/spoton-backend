package com.sparta.popupstore.domain.event.repository;

import com.sparta.popupstore.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
