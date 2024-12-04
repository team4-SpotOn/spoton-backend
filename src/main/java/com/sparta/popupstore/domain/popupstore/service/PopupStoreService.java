package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;


    @Transactional
    public PopupStoreCreateResponseDto createPopupStore(Company company, PopupStoreCreateRequestDto requestDto) {
        if (company == null) {
            throw new RuntimeException("Unauthorized access - company not found");
        }
        return new PopupStoreCreateResponseDto(popupStoreRepository.save(requestDto.toEntity(company)));
    }
}
