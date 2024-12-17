package com.sparta.popupstore.domain.popupstore.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreGetAllResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreSearchResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.QPopupStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.sparta.popupstore.domain.popupstore.entity.QPopupStore.popupStore;

@Repository
@RequiredArgsConstructor
public class PopupStoreQueryDslImpl implements PopupStoreQueryDsl {
    private final JPAQueryFactory query;
    private final QPopupStore popupStore = QPopupStore.popupStore;

    @Override
    public PopupStoreSearchResponseDto findByDate(Pageable pageable) {
        Page<PopupStore> startingSoon = this.getStartingSoon(pageable);
        Page<PopupStore> endingSoon = this.getEndingSoon(pageable);

        return PopupStoreSearchResponseDto.builder()
                .startingSoonPopupStores(startingSoon.map(PopupStoreGetAllResponseDto::new))
                .endingSoonPopupStores(endingSoon.map(PopupStoreGetAllResponseDto::new))
                .build();
    }

    private Page<PopupStore> getStartingSoon(Pageable pageable) {
        List<PopupStore> startingSoon = query.select(popupStore)
                .from(popupStore)
                .leftJoin(popupStore.company)
                .fetchJoin()
                .where(
                        startingSoon()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.selectFrom(popupStore)
                .where(startingSoon())
                .stream()
                .count();
        return new PageImpl<>(startingSoon, pageable, total);
    }

    private Page<PopupStore> getEndingSoon(Pageable pageable) {
        List<PopupStore> endingSoon = query.select(popupStore)
                .from(popupStore)
                .leftJoin(popupStore.company)
                .fetchJoin()
                .where(
                        endingSoon()
                                .and(endPopupStore())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.selectFrom(popupStore)
                .where(
                        endingSoon()
                                .and(endPopupStore())
                )
                .stream()
                .count();
        return new PageImpl<>(endingSoon, pageable, total);
    }

    @Override
    public List<PopupStore> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        return query
                .selectFrom(popupStore)
                .where(
                        popupStore.startDate.loe(endDate),
                        popupStore.endDate.goe(startDate)
                )
                .fetch();
    }
    private BooleanExpression startingSoon(){
        return popupStore.startDate.between(LocalDate.now(), LocalDate.now().plusDays(3));
    }

    private BooleanExpression endingSoon(){
        return popupStore.endDate.before(LocalDate.now().plusDays(3));
    }

    private BooleanExpression endPopupStore(){
        return popupStore.endDate.after(LocalDate.now());
    }
}
