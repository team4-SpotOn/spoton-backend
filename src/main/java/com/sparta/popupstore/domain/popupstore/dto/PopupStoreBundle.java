package com.sparta.popupstore.domain.popupstore.dto;

import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreImage;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PopupStoreBundle {
    private final List<PopupStoreImage> imageList;
    private final List<PopupStoreOperating> operatingList;
    private final List<PopupStoreAttribute> attributeList;
}
