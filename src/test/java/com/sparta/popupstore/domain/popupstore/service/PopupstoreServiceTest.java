package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.kakaoaddress.dto.RoadAddress;
import com.sparta.popupstore.domain.kakaoaddress.service.KakaoAddressService;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreImageRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreBundle;
import com.sparta.popupstore.domain.popupstore.bundle.service.PopupStoreBundleService;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PopupstoreServiceTest {

    @Mock
    private PopupStoreRepository popupStoreRepository;

    @Mock
    private KakaoAddressService kakaoAddressService;

    @Mock
    private PopupStoreBundleService popupStoreBundleService;

    @InjectMocks
    private PopupStoreService popupStoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("팝업스토어 생성")
    void createPopupStore_success() throws Exception {
        // Given
        Company company = Company.builder()
                .id(1L)
                .email("email@test.com")
                .password("password")
                .ceoName("CEO Name")
                .name("Test Company")
                .phone("123-456-7890")
                .website("http://company.com")
                .businessLicense("1234567890")
                .build();

        PopupStoreCreateRequestDto requestDto = new PopupStoreCreateRequestDto();
        setField(requestDto, "name", "Test Store");
        setField(requestDto, "contents", "Test Contents");
        setField(requestDto, "price", 10000);
        setField(requestDto, "reservationLimit", 100);
        setField(requestDto, "address", "Seoul");
        setField(requestDto, "startDate", LocalDate.now());
        setField(requestDto, "endDate", LocalDate.now().plusDays(30));

        PopupStoreImageRequestDto imageDto = new PopupStoreImageRequestDto();
        setField(imageDto, "imageUrl", "http://image.com/thumbnail.jpg");
        setField(imageDto, "sort", 0);
        setField(requestDto, "imageList", List.of(imageDto));

        setField(requestDto, "operatingList", List.of());
        setField(requestDto, "attributeList", List.of());

        Address address = new Address(new RoadAddress("Seoul", "Seoul", 37.5665, 126.9780));
        when(kakaoAddressService.getKakaoAddress(any(String.class))).thenReturn(address);

        PopupStore popupStore = PopupStore.builder()
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

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
