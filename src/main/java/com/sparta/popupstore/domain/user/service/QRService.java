package com.sparta.popupstore.domain.user.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.reservation.repository.ReservationRepository;
import com.sparta.popupstore.domain.user.dto.request.UserValidReservationRequestDto;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class QRService {

    private final UserRepository userRepository;
    private final PopupStoreRepository popupStoreRepository;
    private final ReservationRepository reservationRepository;

    public QRService(UserRepository userRepository, PopupStoreRepository popupStoreRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.popupStoreRepository = popupStoreRepository;
        this.reservationRepository = reservationRepository;
    }

    public byte[] getUseQrCode(User user) {
        int width = 400;
        int height = 400;
        String userQrCode = user.getQrCode();

        try {
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(userQrCode, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream qrCode = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", qrCode);
            return qrCode.toByteArray();
        } catch(WriterException we) {
            throw new CustomApiException(ErrorCode.QR_ENCODE_ERROR);
        } catch(IOException e) {
            throw new CustomApiException(ErrorCode.WRITE_STREAM_ERROR);
        }
    }

    public void validReservation(Long popupStoreId, UserValidReservationRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));
        User user = userRepository.findByQrCode(requestDto.getQrCode())
                .orElseThrow(() -> new CustomApiException(ErrorCode.QR_NOT_FOUND_ERROR));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationAt = reservationRepository.findByPopupStoreAndUser(popupStore, user)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_RESERVATION))
                .getReservationAt();
        if(reservationAt.isAfter(now.plusMinutes(30))
                || reservationAt.isBefore(now.minusMinutes(30))
        ) {
            throw new CustomApiException(ErrorCode.DOESNT_RESERVATION_AT);
        }
    }
}
