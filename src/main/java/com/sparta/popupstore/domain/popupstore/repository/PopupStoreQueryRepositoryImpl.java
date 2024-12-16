package com.sparta.popupstore.domain.popupstore.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import static com.sparta.popupstore.domain.popupstore.entity.QPopupStore.popupStore;
@RequiredArgsConstructor
public class PopupStoreQueryRepositoryImpl implements PopupStoreQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PopupStore> findByStartDateAndEndDate(LocalDate now, LocalDate endDate) {
        return queryFactory
                .selectFrom(popupStore)
                .where(
                        popupStore.startDate.loe(endDate),
                        popupStore.endDate.goe(now)
                )
                .fetch();
    }
}
