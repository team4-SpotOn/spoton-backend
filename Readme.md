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

[팀노션](https://teamsparta.notion.site/4-1502dc3ef514816d8d33fefa88b95f04)

-------
## 사용기술
![docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![aws](https://img.shields.io/badge/Amazon_AWS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white)
![spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![jwt](https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink)
![swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![git action](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)

-------
### 와이어프레임

<img src="https://cdn.discordapp.com/attachments/1321530954057252905/1323560447332585513/2024-12-31_4.56.36.png?ex=6776ef79&is=67759df9&hm=7b51a85388dea65ada28adaefbb70f6c607f58960edebb1ef229ad58630f24e9&" width="700" height="800"/>

-------
### erd

<img src="https://cdn.discordapp.com/attachments/1321530954057252905/1324174611411238963/img.png?ex=67773135&is=6775dfb5&hm=37734ee22bf55ef3eb94f4f9d312aac3e7e7ae6bcde7607d603f7dfbc9b12caa&" width="1000" height="500"/>

-------
### 아키텍쳐
<img width="555" alt="스크린샷 2025-01-07 오전 9 08 58" src="https://github.com/user-attachments/assets/62926ba2-584f-466b-b764-3f8d3f2d2e64" />

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
## 개선점 밎 보완할 점

#### 개선점
프로젝트 초,중반까지는 예정된 일정에 맞게 잘 진행되었으나,
프로젝트 후반부엔 CI/CD 및 프로젝트 자료 준비로 인하여 일정에 차질이 생겼다.
프로젝트 시작하기전부터 일정을 완벽하게 정해서 거기에 맞춰서 진행하고 싶었으나,
예상치 못한 이슈로 인하여 그 점을 지키지 못한게 아쉽다. 
CI도입 후엔 정작 개발 진행을 추가 개발 시간이 없어서 CI가 제대로 활용되지 못한 점도 아쉽다.

#### 보완할 점
프로젝트 시작시에 실제 서비스가 목표였던만큼 기회가 된다면 실제 서비스를 해보는 것이 좋을거 같다.
테스트 코드를 통해 코드 로직을 확인해야 한다.
실제 데이터를 크롤링 하여 현재 로직이 어떤 약점을 보이는지, 어떤 강점을 보이는지 확인하도록 한다.
전화번호 인증 api를 구현하여 전화번호로 인한 중복 가입 방지라는 목적을 제대로 이루도록 한다.
결제 기능 api를 구현하여 사용자가 포인트를 현금으로 충전할 수 있도록 한다.

------------
## 회고
| 팀원  | 내용                                                           |         
|-----|--------------------------------------------------------------|
| 문정원 | 프로젝트 일정과 기획을 상세하게 분류하고 차근차근 진행한다는 점에서 좋았습니다. 후반부엔 예상치 못한 이슈로 일정에 차칠이 생겨 아쉬웠습니다.이번 일을 계기로 작은 프로젝트 일지라도 이슈를 예상해서 좀 더 꼼꼼한 일정 관리의 중요성을 느꼈습니다.                               |                       
| 권익현 | 테스트 코드 작성에 시간을 들이지 못해 성능 향상을 시도해보지 못한 것이 아쉽다. 프로젝트라는 것이 계획대로 진행되지 않는다는 것을 뼈아프게 확인할 수 있는 프로젝트였다. | 
| 장용환 | 막히는 부분이 있을 때 빨리 이야기해서 문제를 해결한 부분은 좋았으나, 개발 범위를 잘 못 정한 것 같아서 아쉽습니다. 이번 프로젝트를 진행하면서 개발 스코프를 잘 정해야겠다는 생각이 들었고, 팀원 분들과 잘 맞아서 즐겁게 프로젝트를 진행 할 수 있었던 것 같습니다.                       | 
| 이하승 | 로직이나 고민사항이 있으면 같이 고민해주신 덕분에 해결해 나갈 수 있었던 것 같습니다. 하지만 부하 테스트 및 성능 개선에 별다른 시간을 들이지 못한게 아쉽습니다.이번 프로젝트를 진행하면서 해보고 싶었던 부분들을 직접 해볼 수 있어서 좋았습니다|
| 김도현 | Phase별로 나누어 진행하여 프로젝트의 전체적인 진행상황을 파악할 수 있어 좋았습니다. 간단한 기능들을 맡았음에도 제대로 구현하지 못하여 아쉬웠습니다.                                 |

------------
## 트러블 슈팅

- ### S3 이미지 저장 이슈   
    - 실제로 db에 저장된 이미지 정보와 s3에 저장되어 있는 이미지들이 불일치하는 문제가 발생했다.
    사용자가 없을 새벽 시간에 스케줄러를 통해 현재 사용되는 이미지와 S3에 저장되어있는 이미지를 비교하여 사용하지 않는 이미지들을 삭제하는 작업 실행 한다.

- ### 동시성 제어 이슈
   - 선착순 10개 쿠폰 발급 시, "선착순 쿠폰 개수 +1"과 "쿠폰 발급 1개"가 1:1로 매칭되지 않았다.
   비관적 락을 사용하여 정상적으로 동시성 제어에 성공했다.
   향후 플랫폼의 트래픽 증가에 대비하여 Redis 기반 분산락의 적용도 고려해보면 좋을 듯 하다.

- ### 카카오 API 이슈   
   - 카카오 지도 API 이용 중에 허용IP와 추가 API설정에서 문제가 발생했다.
   허용IP는 IP를 등록해서 해결 / 지도 API설정은 2024년 12월 1일 정책변경으로 인한 API 설정 기능을 활성화 했다.
   외부 API를 연동하는 과정에서 확인해야 할 부분이 많다고 느꼈다. 정책변경 등의 상황에 대한 대비가 필요하다.

- ### 팝업스토어 조회수 어뷰징   
   - 임의의 사용자가 새로고침과 같은 기능으로 조회 api를 반복 요청하여 팝업스토어 조회수를 비정상적으로 증가 시킬 수 있는 문제가 발생했다.
   쿠키에 해당 팝업 스토어를 조회 했는지를 저장한 후 쿠키가 존재할 경우, 조회수가 증가하지 않도록 로직을 변경했다.
   사용자가 쿠키를 삭제한 후 요청할 경우 어뷰징 문제를 막을 수 없다는 단점이 있어 보완할 점을 생각해보아야 할 것 같다.

- ### Promethues 비정상적인 종료 이슈   
   - ec2 배포 시 Prometheus가 계속 종료되는 문제가 발생했다.
   queryLog들을 저장하는 디렉토리 경로를 /prometheus/query_log.log 로 지정해뒀지만 해당 경로에 대한 쓰기 권한이 없었습니다.
   docker-compose prometheus 컨테이너를 실행시킬 때 root 권한으로 실행되도록 하였습니다.
   현재 root 권한으로 실행되게 하였지만 이 부분은 보안상 위험할 수 있다고 하기에 ec2에서 디렉토리의 권한을 조정하는 방법을 고려하고 있다.
