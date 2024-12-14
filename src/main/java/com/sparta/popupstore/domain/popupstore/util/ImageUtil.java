package com.sparta.popupstore.domain.popupstore.util;

import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreImageRequestDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreImage;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ImageUtil {

    private final PopupStoreImageRepository popupStoreImageRepository;

    public List<PopupStoreImage> createPopupStoreImageList(
            PopupStore popupStore,
            List<PopupStoreImageRequestDto> imageDtoList
    ) {
        return popupStoreImageRepository.saveAll(
                imageDtoList.stream()
                        .map(imageDto -> imageDto.toEntity(popupStore))
                        .toList()
        );
    }

    public List<PopupStoreImage> getPopupStoreImageList(PopupStore popupStore) {
        return popupStoreImageRepository.findAllByPopupStore(popupStore);
    }

    public void deletePopupStoreImageList(PopupStore popupStore) {
        popupStoreImageRepository.deleteAllByPopupStore(popupStore);
    }
}
