package com.sparta.popupstore.domain.popupstore.util;

import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreOperatingRequestDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreOperatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OperatingUtil {

    private final PopupStoreOperatingRepository popupStoreOperatingRepository;

    public List<PopupStoreOperating> createPopupStoreOperatingList(
            PopupStore popupStore,
            List<PopupStoreOperatingRequestDto> operatingDtoList
    ) {
        return popupStoreOperatingRepository.saveAll(
                operatingDtoList.stream()
                        .map(operationDto -> operationDto.toEntity(popupStore))
                        .toList()
        );
    }

    public List<PopupStoreOperating> getPopupStoreOperatingList(PopupStore popupStore) {
        return popupStoreOperatingRepository.findAllByPopupStore(popupStore);
    }

    public void deletePopupStoreOperatingList(PopupStore popupStore) {
        popupStoreOperatingRepository.deleteAllByPopupStore(popupStore);
    }
}
