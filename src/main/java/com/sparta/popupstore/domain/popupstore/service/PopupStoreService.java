package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.company.service.CompanyService;
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
    private final CompanyService companyService;


    @Transactional
    public PopupStoreCreateResponseDto createPopupStore(String email, PopupStoreCreateRequestDto requestDto) {
        Company company = companyService.getCompanyByEmail(email);
        if (company == null) {
            throw new RuntimeException("Unauthorized access - company not found");
        }

        PopupStore popupStore = PopupStore.builder()
                .company(company)
                .name(requestDto.getName())
                .contents(requestDto.getContent())
                .image(requestDto.getImageUrl())
                .price(Integer.parseInt(requestDto.getPrice()))
                .address(requestDto.getAddress())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .build();

        PopupStore savedPopupStore = popupStoreRepository.save(popupStore);

        return new PopupStoreCreateResponseDto(savedPopupStore);
    }
}
