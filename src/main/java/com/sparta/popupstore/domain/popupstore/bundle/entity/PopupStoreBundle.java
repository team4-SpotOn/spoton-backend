package com.sparta.popupstore.domain.popupstore.bundle.entity;

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
