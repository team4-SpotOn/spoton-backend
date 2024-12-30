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
import com.sparta.popupstore.domain.reservation.entity.Reservation;
import com.sparta.popupstore.domain.reservation.repository.ReservationRepository;
import com.sparta.popupstore.domain.user.dto.request.UserValidReservationRequestDto;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class QRService {

    private final UserRepository userRepository;
    private final PopupStoreRepository popupStoreRepository;
    private final ReservationRepository reservationRepository;

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

        Reservation reservation = reservationRepository.findByPopupStoreAndUser(popupStore, user)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_RESERVATION));

        LocalDate reservationDate = reservation.getReservationDate();
        LocalTime reservationTime = reservation.getReservationTime();

        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate();
        LocalTime nowTime = now.toLocalTime();
        if(!reservationDate.isEqual(nowDate)) {
            throw new CustomApiException(ErrorCode.DOESNT_RESERVATION_AT);
        }
        if(nowTime.minusMinutes(30).isAfter(reservationTime)
                || nowTime.plusMinutes(30).isBefore(reservationTime)) {
            throw new CustomApiException(ErrorCode.DOESNT_RESERVATION_AT);
        }
    }
}
