package com.sparta.popupstore.domain.popupstore.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreGetAllResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreSearchResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.QPopupStore;
import com.sparta.popupstore.domain.popupstore.enums.PopupStoreStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PopupStoreQueryDslImpl implements PopupStoreQueryDsl {
    private final JPAQueryFactory query;
    private final QPopupStore popupStore = QPopupStore.popupStore;

    @Override
    public PopupStoreSearchResponseDto findByStatus(Pageable pageable, PopupStoreStatus popupStoreStatus) {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = fromDate;

        Page<PopupStore> popupStoreList = this.getPopupStores(pageable, fromDate, toDate, popupStoreStatus);

        return PopupStoreSearchResponseDto.builder()
                .popupStores(popupStoreList.map(PopupStoreGetAllResponseDto::new))
                .build();
    }

    @Override
    public List<PopupStore> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate, Long page, Long size) {
        return query
                .selectFrom(popupStore)
                .where(
                        popupStore.startDate.loe(endDate),
                        popupStore.endDate.goe(startDate)
                )
                .offset((page-1)*size)
                .limit(size)
                .fetch();
    }

    private Page<PopupStore> getPopupStores(Pageable pageable, LocalDate fromDate, LocalDate toDate, PopupStoreStatus popupStoreStatus) {
        List<PopupStore> startingSoon = query.select(popupStore)
                .from(popupStore)
                .leftJoin(popupStore.company)
                .fetchJoin()
                .where(
                        startingSoon(toDate, popupStoreStatus),
                        openPopupStore(fromDate, toDate, popupStoreStatus),
                        closePopupStore(fromDate, popupStoreStatus)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.selectFrom(popupStore)
                .where(
                        startingSoon(toDate, popupStoreStatus),
                        openPopupStore(fromDate, toDate, popupStoreStatus),
                        closePopupStore(fromDate, popupStoreStatus)
                )
                .stream()
                .count();
        return new PageImpl<>(startingSoon, pageable, total);
    }

    private BooleanExpression startingSoon(LocalDate toDate, PopupStoreStatus popupStoreStatus){
        if(popupStoreStatus == PopupStoreStatus.SCHEDULE){
            return popupStore.startDate.between(toDate.plusDays(1), toDate.plusWeeks(1));
        }
        return null;
    }

    private BooleanExpression openPopupStore(LocalDate fromDate, LocalDate toDate,PopupStoreStatus popupStoreStatus){
        if(popupStoreStatus == PopupStoreStatus.OPEN){
            return popupStore.startDate.before(toDate).and(popupStore.endDate.after(fromDate.minusDays(1)));
        }
        return null;
    }

    private BooleanExpression closePopupStore(LocalDate fromDate, PopupStoreStatus popupStoreStatus){
        if(popupStoreStatus == PopupStoreStatus.CLOSE){
            return popupStore.endDate.before(fromDate);
        }
        return null;
    }
}
