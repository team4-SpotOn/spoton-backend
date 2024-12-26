package com.sparta.popupstore.domain.popupstore.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.QPopupStore;
import com.sparta.popupstore.domain.popupstore.enums.PopupStoreStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PopupStoreQueryDslImpl implements PopupStoreQueryDsl {
    private final JPAQueryFactory query;
    private final QPopupStore popupStore = QPopupStore.popupStore;

    @Override
    public Page<PopupStore> findByStatus(Pageable pageable, PopupStoreStatus popupStoreStatus) {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = fromDate.plusDays(1);

        return this.getPopupStores(pageable, fromDate, toDate, popupStoreStatus);
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

    @Override
    public List<PopupStore> findByMonth(Long page, Long size) {
        YearMonth MontDate = YearMonth.now();
        LocalDate startDate = MontDate.atDay(1);
        LocalDate endDate = MontDate.atEndOfMonth();

        return query.selectFrom(popupStore)
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
                .where(
                        findByStatus(fromDate, toDate, popupStoreStatus)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.selectFrom(popupStore)
                .where(
                        findByStatus(fromDate, toDate, popupStoreStatus)
                )
                .stream()
                .count();
        return new PageImpl<>(startingSoon, pageable, total);
    }

    private BooleanExpression findByStatus(LocalDate fromDate, LocalDate toDate, PopupStoreStatus popupStoreStatus) {
        return switch(popupStoreStatus) {
            case ALL -> null;
            case SCHEDULE -> popupStore.startDate.between(toDate, toDate.plusWeeks(1));
            case OPEN -> popupStore.startDate.before(toDate).and(popupStore.endDate.after(fromDate.minusDays(1)));
            case CLOSE -> popupStore.endDate.before(fromDate);
        };
    }
}
