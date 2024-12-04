package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class PopupStoreUpdateRequestDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @Min(value = 0, message = "가격은 0이상이어야만 합니다.")
    private String price;
    private String content;
    private String imageUrl;
    private String address;

    public void updatePopupStore(PopupStore popupStore) {
        if (this.name != null) popupStore.setName(this.name);
        if (this.startDate != null) popupStore.setStartDate(this.startDate);
        if (this.endDate != null) popupStore.setEndDate(this.endDate);
        if (this.price != null) popupStore.setPrice(Integer.parseInt(this.price));
        if (this.content != null) popupStore.setContents(this.content);
        if (this.address != null) popupStore.setAddress(this.address);
    }
}
