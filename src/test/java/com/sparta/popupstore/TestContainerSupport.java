package com.sparta.popupstore;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public abstract class TestContainerSupport  {

    private static final String MYSQL_IMAGE = "mysql:8.0";  // MySQL 버전 설정
    private static final int MYSQL_PORT = 3306;  // MySQL 컨테이너 내부 포트 설정
    private static JdbcDatabaseContainer<?> MYSQL;

    static {
        // GitHub Secrets 사용하여 접속 정보 암호화
        String dbHost = System.getenv("DB_HOST");
        String dbPort = System.getenv("DB_PORT");
        String dbName = System.getenv("DB_NAME");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        // MySQL 컨테이너 설정
        MYSQL = new MySQLContainer<>(DockerImageName.parse(MYSQL_IMAGE))
            .withExposedPorts(MYSQL_PORT)  // MySQL 포트 노출
            .withDatabaseName(dbName)  // DB 이름 (GitHub Secrets에서 가져옴)
            .withUsername(dbUser)  // MySQL 사용자명 (GitHub Secrets에서 가져옴)
            .withPassword(dbPassword)  // 비밀번호 (GitHub Secrets에서 가져옴)
            .withNetworkMode("app-network")  // 네트워크 모드
            .withReuse(true);  // 컨테이너 재사용 설정
        MYSQL.start();  // MySQL 컨테이너 시작
    }

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        // Spring에서 사용할 DB 관련 설정을 TestContainer로부터 동적으로 가져오기
        registry.add("spring.datasource.driver-class-name", MYSQL::getDriverClassName);
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);  // DB 연결 URL
        registry.add("spring.datasource.username", MYSQL::getUsername);  // DB 사용자명
        registry.add("spring.datasource.password", MYSQL::getPassword);  // DB 비밀번호
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");  // Hibernate DDL 자동 처리 설정
    }

    @BeforeEach
    public void setUp() {
        // 필요한 초기화 작업을 여기에 추가할 수 있습니다.
    }

    @AfterAll
    public static void tearDown() {
        if (MYSQL != null) {
            MYSQL.stop();  // 테스트 후 MySQL 컨테이너 종료
        }
    }
}