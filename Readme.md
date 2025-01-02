# <center> 프로젝트명 </center>

## <center> spotOn </center>


<div style="text-align: center;"><img src="https://cdn.discordapp.com/attachments/1321530954057252905/1324173460456472630/img_2.png?ex=67773022&is=6775dea2&hm=acd89d1cb43ea7b42c34247e3da3a90f30e67d6f24190231723c360024cc753a&" width="250" height="250"/></div> 


-------
## 프로젝트 진행 기간

__2024.12.2-2025.1.7__

-------
## 프로젝트 설명
 __spoton 프로젝트는 팝업스토어 조회 및 예약이 가능한 플랫폼입니다. 사용자들은 다양한 팝업스토어를 손쉽게 탐색하고, 원하는 장소와 시간에 맞춰 예약할 수 있습니다.   
팝업스토어 운영자는 플랫폼을 통해 손쉽게 공간을 관리하고, 고객과의 예약을 효율적으로 관리할 수 있습니다.__

-------
## 팀원소개

| 팀원  | 역할                                                           |         
|-----|--------------------------------------------------------------|
| 문정원 | 위도경도 구하기, 카카오맵지도 호출, 동시성제어, CI                               |                         
| 권익현 | 소셜로그인, QR, 회원가입 및 로그인, 회원정보 수정, 회원탈퇴, 전체적 코드리팩토링, 예약시 인원수 추가 | 
| 장용환 | 팝업스토어 운영시간, 팝업스토어 예약, 팝업스토어CRUD, 조회수기능                       | 
| 이하승 | 실시간 로깅 및 모니터링, AWS s3, 이미지 업로드, 이벤트CRUD, 쿠폰생성, 스케줄러, CD      |
| 김도현 | 포인트 관리, 리뷰CRUD, 날짜별 팝업스토어 조회                                 |

-------
### 와이어프레임

<img src="https://cdn.discordapp.com/attachments/1321530954057252905/1323560447332585513/2024-12-31_4.56.36.png?ex=6776ef79&is=67759df9&hm=7b51a85388dea65ada28adaefbb70f6c607f58960edebb1ef229ad58630f24e9&" width="700" height="800"/>

-------
### erd

<img src="https://cdn.discordapp.com/attachments/1321530954057252905/1324174611411238963/img.png?ex=67773135&is=6775dfb5&hm=37734ee22bf55ef3eb94f4f9d312aac3e7e7ae6bcde7607d603f7dfbc9b12caa&" width="1000" height="500"/>

-------
## Api 정리

### QR Code API
| Method | Endpoint                                    | Description                        |
|--------|--------------------------------------------|------------------------------------|
| GET    | /users/mypage/qrCode                       | 유저 QR 코드 출력                 |
| GET    | /popupstores/{popupStoreId}/qrCode         | QR 코드로 유저 확인               |

---

### Promotion Events API
| Method | Endpoint                                    | Description                        |
|--------|--------------------------------------------|------------------------------------|
| GET    | /promotionEvents                           | 프로모션 이벤트 다건 조회          |
| GET    | /promotionEvents/{promotionEventId}        | 프로모션 이벤트 단건 조회          |

---

### Social Login API
| Method | Endpoint                                    | Description                        |
|--------|--------------------------------------------|------------------------------------|
| POST   | /oauth2/phone-number                       | 사용자 전화번호 연동              |
| GET    | /oauth2/signin/{platform}                  | 소셜 로그인 phase 1               |
| GET    | /oauth2/callback/{platform}                | 소셜 로그인 phase 2               |

---

### Admin API
| Method | Endpoint                                    | Description                       |
|--------|--------------------------------------------|-----------------------------------|
| POST   | /admins/signup                             | 관리자 회원 가입                 |
| POST   | /admins/signin                             | 관리자 로그인                    |
| POST   | /admins/promotionEvents                    | 프로모션 이벤트 추가             |
| PATCH  | /admins/promotionEvents/{promotionEventId} | 프로모션 이벤트 수정             |
| PATCH  | /admins/popupstores/{popupStoreId}         | 관리자 - 팝업스토어 수정         |
| DELETE | /admins/promotionEvents/{promotionEventId} | 프로모션 이벤트 삭제             |
| DELETE | /admins/popupstores/{popupStoreId}         | 관리자 - 팝업스토어 삭제         |


---

### User API
| Method | Endpoint                                    | Description |
|--------|--------------------------------------------|-------------|
| POST   | /users/signup                              | 유저 회원 가입    |
| POST   | /users/signin                              | 유저 로그인      |
| GET    | /users/mypage                              | 유저 마이페이지    |
| GET    | /users/coupons                             | 유저 내 쿠폰 보기  |
| PATCH  | /users                                     | 유저 정보 수정    |
| DELETE | /users                                     | 유저 회원 탈퇴    |



---

### Reservation API
| Method | Endpoint                                    | Description                        |
|--------|--------------------------------------------|------------------------------------|
| POST   | /reservations/popupstores/{popupStoreId}   | 팝업스토어 예약                  |
| DELETE | /reservations/{reservationId}              | 팝업스토어 예약 취소              |

---

### S3 Bucket API
| Method | Endpoint                                    | Description                        |
|--------|--------------------------------------------|------------------------------------|
| GET    | /{directory}/images/preassigned            | 리뷰 & 프로모션 이벤트 이미지 URL  |
| GET    | /popup-stores/images/preassigned           | 팝업스토어 이미지 URL             |
| DELETE | /images                                    | S3에 저장된 이미지 삭제           |

---

### Review API
| Method | Endpoint                                    | Description                        |
|--------|--------------------------------------------|------------------------------------|
| POST   | /reviews/popupstores/{popupStoreId}        | 리뷰 생성                         |
| GET    | /reviews/popupstores/{popupStoreId}        | 리뷰 조회                         |
| PATCH  | /reviews/{reviewId}                        | 리뷰 수정                         |
| DELETE | /reviews/{reviewId}                        | 리뷰 삭제                         |





---

### Kakao Address API
| Method | Endpoint                                    | Description                        |
|--------|--------------------------------------------|------------------------------------|
| GET    | /kakaoapi                                  | 카카오 주소 API                   |

---

### Points API
| Method | Endpoint                                    | Description                        |
|--------|--------------------------------------------|------------------------------------|
| POST   | /users/point/charge                        | 포인트 충전                       |
| GET    | /users/point/used                          | 포인트 사용내역 조회              |
| GET    | /users/point/charge                        | 포인트 충전내역 조회              |


---

### Coupon API
| Method | Endpoint                                    | Description                        |
|--------|--------------------------------------------|------------------------------------|
| POST   | /coupons/{promotionEventId}/coupons        | 프로모션 이벤트 쿠폰 발급         |

---

### Popup Store API
| Method | Endpoint                                    | Description    |
|--------|--------------------------------------------|----------------|
| POST   | /popupstores                               | 팝업스토어 생성       |
| GET    | /popupstores/search/{popupStoreStatus}    | 특정 상태 팝업스토어 조회 |
| GET    | /popupstores/period                       | 팝업스토어 날짜별 조회   |
| GET    | /popupstores/month                        | 이번달 팝업스토어 조회   |
| GET    | /popupstores                               | 팝업스토어 전체 조회    |
| GET    | /popupstores/{popupStoreId}               | 팝업스토어 단건 조회    |
| PATCH  | /popupstores/{popupStoreId}               | 팝업스토어 수정       |
| DELETE | /popupstores/{popupStoreId}               | 팝업스토어 삭제       |





---

### Company API
| Method | Endpoint                                    | Description                        |
|--------|--------------------------------------------|------------------------------------|
| POST   | /companies/signup                          | 회사 회원 가입                    |
| POST   | /companies/signin                          | 회사 로그인                       |
| GET    | /companies/popupstores                    | 회사 자사 팝업스토어 조회         |
| GET    | /companies/mypage                         | 회사 마이페이지                   |
| PATCH  | /companies                                 | 회사 정보 수정                    |
| DELETE | /companies                                 | 회사 회원 탈퇴                    |



-------
## 기술
- 도커   
- aws s3   
- 스프링부트   
- Jwt   
- 스웨거   
- 깃액션