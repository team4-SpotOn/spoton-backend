package com.sparta.popupstore;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public abstract class TestContainerSupport {

    private static final String MYSQL_IMAGE = "mysql:8.0";  // MySQL 버전 설정
    private static JdbcDatabaseContainer<?> mysqlContainer;

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        // Spring에서 사용할 DB 관련 설정을 TestContainer로부터 동적으로 가져오기
        if (mysqlContainer == null) {
            mysqlContainer = new MySQLContainer<>(DockerImageName.parse(MYSQL_IMAGE))
                .withEnv("MYSQL_ROOT_PASSWORD", System.getenv("MYSQL_ROOT_PASSWORD"))
                .withDatabaseName(System.getenv("DB_NAME"))
                .withUsername(System.getenv("DB_USER"))
                .withPassword(System.getenv("DB_PASSWORD"))
                .withExposedPorts(3306)  // MySQL 포트 노출
                .withReuse(true);  // 컨테이너 재사용 설정

            mysqlContainer.start();  // MySQL 컨테이너 시작
        }

        registry.add("spring.datasource.driver-class-name", mysqlContainer::getDriverClassName);
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);  // DB 연결 URL
        registry.add("spring.datasource.username", mysqlContainer::getUsername);  // DB 사용자명
        registry.add("spring.datasource.password", mysqlContainer::getPassword);  // DB 비밀번호
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");  // Hibernate DDL 자동 처리 설정
    }

    // TestContainer 종료 설정 (필요시)
    // @AfterAll
    // public static void tearDown() {
    //     if (mysqlContainer != null) {
    //         mysqlContainer.stop();
    //     }
    // }
}
