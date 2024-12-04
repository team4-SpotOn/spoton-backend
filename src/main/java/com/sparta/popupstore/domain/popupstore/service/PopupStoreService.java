package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
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

    @Transactional
    public PopupStoreUpdateResponseDto updatePopupStore(Long popupId, Company company, PopupStoreUpdateRequestDto requestDto, MultipartFile imageFile) {
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new RuntimeException("PopupStore not found"));

        if (!popupStore.getCompany().equals(company)) {
            throw new RuntimeException("Unauthorized access - not the owner of the popup store");
        }

        if (!isEditable(popupStore)) {
            throw new RuntimeException("Cannot edit a popup store that is in progress");
            // todo : 관리자인 경우 수정 가능하도록 로직 구현해야함
        }

        requestDto.updatePopupStore(popupStore);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = null;
            try {
                imagePath = saveImageFile(imageFile);
            } catch (IOException e) {
                throw new RuntimeException("not found image file");
            }
            popupStore.setImage(imagePath);
        }

        return new PopupStoreUpdateResponseDto(popupStoreRepository.save(popupStore));
    }

    private boolean isEditable(PopupStore popupStore) {
        return popupStore.getStartDate().isAfter(LocalDate.now());
    }
}
