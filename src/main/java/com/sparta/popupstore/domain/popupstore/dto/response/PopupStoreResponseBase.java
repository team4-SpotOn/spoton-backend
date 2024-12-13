package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttributes;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class PopupStoreResponseBase {

    protected List<HashMap<DayOfWeek, LocalTime>> processOperatingList(List<PopupStoreOperating> operatingList) {
        return operatingList.stream()
                .map(operating -> {
                    HashMap<DayOfWeek, LocalTime> map = new HashMap<>();
                    map.put(operating.getDayOfWeek(), operating.getStartTime());
                    map.put(operating.getDayOfWeek(), operating.getEndTime());
                    return map;
                })
                .toList();
    }

    protected Map<String, Boolean> processAttributeList(List<PopupStoreAttributes> attributesList) {
        return attributesList.stream()
                .collect(Collectors.toMap(
                        attr -> attr.getAttribute().name(),
                        PopupStoreAttributes::getIsAllow
                ));
    }
}
