package com.sparta.popupstore.domain.point.controller;

import com.sparta.popupstore.domain.point.service.PointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 관련 API")
public class PointController {
  private final PointService pointService;
}
