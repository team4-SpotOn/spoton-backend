package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreFindOneResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.entity.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;
    private final String UPLOAD_URL = "uploads";


    @Transactional
    public PopupStoreCreateResponseDto createPopupStore(Company company, PopupStoreCreateRequestDto requestDto, MultipartFile imageFile) throws IOException {
        String imagePath = saveImageFile(imageFile);
        return new PopupStoreCreateResponseDto(popupStoreRepository.save(requestDto.toEntity(company, imagePath)));
    }

    private String saveImageFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Path path = Paths.get(UPLOAD_URL, System.currentTimeMillis() + "_" + file.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return path.toString();
    }

    // 관리자 - 팝업 스토어 수정
    @Transactional
    public PopupStoreUpdateResponseDto updatePopupStore(Long popupId, User user, PopupStoreUpdateRequestDto requestDto, MultipartFile imageFile) {
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new RuntimeException("PopupStore not found"));

        if (user.getUserRole() != UserRole.ADMIN) {
            throw new RuntimeException("Not Admin");
        }

        String imagePath = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imagePath = saveImageFile(imageFile);
            } catch (IOException e) {
                throw new RuntimeException("not found image file");
            }
        }

        popupStore.update(requestDto);

        return new PopupStoreUpdateResponseDto(popupStoreRepository.save(popupStore));
    }

    // 회사 - 팝업 스토어 수정
    @Transactional
    public PopupStoreUpdateResponseDto updatePopupStore(Long popupId, Company company, PopupStoreUpdateRequestDto requestDto, MultipartFile imageFile) {
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new RuntimeException("PopupStore not found"));

        if (!popupStore.getCompany().equals(company)) {
            throw new RuntimeException("Unauthorized access - not the owner of the popup store");
        }

        if (!isEditable(popupStore)) {
            throw new RuntimeException("Cannot edit a popup store that is in progress");
        }

        String imagePath = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imagePath = saveImageFile(imageFile);
            } catch (IOException e) {
                throw new RuntimeException("not found image file");
            }
        }

        popupStore.update(requestDto);

        return new PopupStoreUpdateResponseDto(popupStoreRepository.save(popupStore));
    }

    private boolean isEditable(PopupStore popupStore) {
        return popupStore.getStartDate().isAfter(LocalDate.now());
    }

    // 팝업스토어 단건조회
    public PopupStoreFindOneResponseDto getPopupStoreFindOne(Long popupId){
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팝업스토어 입니다."));
        return new PopupStoreFindOneResponseDto(popupStore);
    }

    public void deletePopupStore(Company company, Long popupStoreId) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new IllegalArgumentException("Popup Store not found"));
        if(!popupStore.getCompany().getId().equals(company.getId())) {
            throw new IllegalArgumentException("Popup Store is not in this company");
        }
        if(popupStore.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("이미 시작된 팝업 스토어는 삭제할 수 없습니다.");
        }

        popupStoreRepository.deleteById(popupStoreId);
    }

    public void deletePopupStore(Long popupStoreId) {
        popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new IllegalArgumentException("Popup Store not found"));

        popupStoreRepository.deleteById(popupStoreId);
    }
}
