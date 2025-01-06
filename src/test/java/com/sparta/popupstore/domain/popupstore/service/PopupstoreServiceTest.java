package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.kakaoaddress.dto.RoadAddress;
import com.sparta.popupstore.domain.kakaoaddress.service.KakaoAddressService;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreAttributeRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreImageRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreOperatingRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreBundle;
import com.sparta.popupstore.domain.popupstore.bundle.enums.PopupStoreAttributeEnum;
import com.sparta.popupstore.domain.popupstore.bundle.service.PopupStoreBundleService;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PopupstoreServiceTest {

    @Mock
    private PopupStoreRepository popupStoreRepository;

    @Mock
    private KakaoAddressService kakaoAddressService;

    @Mock
    private PopupStoreBundleService popupStoreBundleService;

    @InjectMocks
    private PopupStoreService popupStoreService;

    @Test
    @DisplayName("팝업스토어 생성 - 성공")
    void test1() {
        // Given
        Company company = createTestCompany();
        PopupStoreCreateRequestDto requestDto = getCreateRequestDto();

        Address address = new Address(new RoadAddress("Seoul", "Seoul", 37.5665, 126.9780));
        when(kakaoAddressService.getKakaoAddress(any(String.class))).thenReturn(address);

        PopupStore popupStore = createTestPopupStore(company, address);
        when(popupStoreRepository.save(any(PopupStore.class))).thenReturn(popupStore);

        PopupStoreBundle bundle = new PopupStoreBundle(
                List.of(),
                List.of(),
                List.of()
        );

        when(popupStoreBundleService.createPopupStoreBundle(any(), any(), any(), any())).thenReturn(bundle);

        // When
        PopupStoreCreateResponseDto response = popupStoreService.createPopupStore(company, requestDto);

        // Then
        verify(kakaoAddressService, times(1)).getKakaoAddress(any(String.class));
        verify(popupStoreRepository, times(1)).save(any(PopupStore.class));
        verify(popupStoreBundleService, times(1)).createPopupStoreBundle(any(), any(), any(), any());

        assertNotNull(response);
    }

    @Test
    @DisplayName("팝업스토어 생성 - 주소 조회 실패")
    void test2() {
        // Given
        Company company = createTestCompany();
        PopupStoreCreateRequestDto requestDto = getCreateRequestDto();

        when(kakaoAddressService.getKakaoAddress(any(String.class)))
                .thenThrow(new CustomApiException(ErrorCode.KAKAO_ADDRESS_API_ERROR));

        // When & Then
        assertThrows(CustomApiException.class, () -> popupStoreService.createPopupStore(company, requestDto));
    }

    @Test
    @DisplayName("팝업스토어 수정 - 성공")
    void updatePopupStore_success() {
        // Given
        Long popupId = 1L;
        Company company = createTestCompany();
        PopupStore popupStore = createTestPopupStore(company, new Address(new RoadAddress("Seoul", "Seoul", 37.5665, 126.9780)));
        PopupStoreUpdateRequestDto requestDto = getUpdateRequestDto();

        when(popupStoreRepository.findById(popupId)).thenReturn(Optional.of(popupStore));
        when(kakaoAddressService.getKakaoAddress(any(String.class))).thenReturn(new Address(new RoadAddress("Incheon", "Incheon", 37.4562, 126.7052)));
        PopupStoreBundle bundle = new PopupStoreBundle(
                List.of(),
                List.of(),
                List.of()
        );
        when(popupStoreBundleService.updatePopupStoreBundle(any(), any(), any(), any())).thenReturn(bundle);

        // When
        PopupStoreUpdateResponseDto response = popupStoreService.updatePopupStore(popupId, company, requestDto);

        // Then
        verify(popupStoreRepository, times(1)).findById(popupId);
        verify(kakaoAddressService, times(1)).getKakaoAddress(any(String.class));
        verify(popupStoreBundleService, times(1)).updatePopupStoreBundle(any(), any(), any(), any());

        assertNotNull(response);

        assertEquals(requestDto.getName(), popupStore.getName());
        assertEquals(requestDto.getContents(), popupStore.getContents());
        assertEquals(requestDto.getPrice(), popupStore.getPrice());
        assertEquals(requestDto.getReservationLimit(), popupStore.getReservationLimit());
        assertEquals(requestDto.getAddress(), popupStore.getAddress().getAddress());
        assertEquals(requestDto.getStartDate(), popupStore.getStartDate());
        assertEquals(requestDto.getEndDate(), popupStore.getEndDate());
    }

    @Test
    @DisplayName("팝업스토어 수정 - 존재하지 않는 팝업스토어")
    void updatePopupStore_notFound() {
        // Given
        Long popupId = 1L;
        Company company = createTestCompany();
        PopupStoreUpdateRequestDto requestDto = getUpdateRequestDto();

        when(popupStoreRepository.findById(popupId)).thenReturn(Optional.empty());

        // When & Then
        CustomApiException exception = assertThrows(
                CustomApiException.class, () -> popupStoreService.updatePopupStore(popupId, company, requestDto)
        );

        assertEquals(ErrorCode.POPUP_STORE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("팝업스토어 수정 - 다른 회사 소유")
    void updatePopupStore_notOwner() {
        // Given
        Long popupId = 1L;
        Company company = createTestCompany();
        Company otherCompany = Company.builder()
                .id(2L)
                .name("Other Company")
                .build();
        PopupStore popupStore = createTestPopupStore(otherCompany, new Address(new RoadAddress("Seoul", "Seoul", 37.5665, 126.9780)));
        PopupStoreUpdateRequestDto requestDto = getUpdateRequestDto();

        when(popupStoreRepository.findById(popupId)).thenReturn(Optional.of(popupStore));

        // When & Then
        CustomApiException exception = assertThrows(
                CustomApiException.class, () -> popupStoreService.updatePopupStore(popupId, company, requestDto)
        );

        assertEquals(ErrorCode.POPUP_STORE_NOT_BY_THIS_COMPANY, exception.getErrorCode());
    }

    private Company createTestCompany() {
        return Company.builder()
                .id(1L)
                .email("email@test.com")
                .password("password")
                .ceoName("CEO Name")
                .name("Test Company")
                .phone("123-456-7890")
                .website("http://company.com")
                .businessLicense("1234567890")
                .build();
    }

    private PopupStoreCreateRequestDto getCreateRequestDto() {
        PopupStoreImageRequestDto imageDto = new PopupStoreImageRequestDto("http://image.com/thumbnail.jpg", 0);

        return PopupStoreCreateRequestDto.builder()
                .name("Test Store")
                .contents("Test Contents")
                .price(10000)
                .reservationLimit(100)
                .address("Seoul")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .imageList(List.of(imageDto))
                .operatingList(List.of())
                .attributeList(List.of())
                .build();
    }

    private PopupStore createTestPopupStore(Company company, Address address) {
        return PopupStore.builder()
                .id(1L)
                .company(company)
                .name("Test Store")
                .contents("Test Contents")
                .price(10000)
                .reservationLimit(100)
                .view(0)
                .thumbnail("http://image.com/thumbnail.jpg")
                .address(address)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .build();
    }

    private PopupStoreUpdateRequestDto getUpdateRequestDto() {
        PopupStoreImageRequestDto imageDto = new PopupStoreImageRequestDto("http://image.com/updated_thumbnail.jpg", 0);

        PopupStoreOperatingRequestDto operatingDto = new PopupStoreOperatingRequestDto(
                DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0));

        PopupStoreAttributeRequestDto attributeDto = new PopupStoreAttributeRequestDto(
                PopupStoreAttributeEnum.RESERVATION, true);

        return PopupStoreUpdateRequestDto.builder()
                .name("Updated Test Store")
                .contents("Updated Test Contents")
                .price(15000)
                .reservationLimit(120)
                .address("Incheon")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(31))
                .imageList(List.of(imageDto))
                .operatingList(List.of(operatingDto))
                .attributeList(List.of(attributeDto))
                .build();
    }
}
