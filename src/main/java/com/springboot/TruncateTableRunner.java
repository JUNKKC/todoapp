package com.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TruncateTableRunner implements CommandLineRunner {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public void run(String... args) throws Exception {
    // TRUNCATE TABLE 쿼리 실행
    String sql = "TRUNCATE TABLE todos";
    jdbcTemplate.execute(sql);

    // 실행한 결과를 확인하기 위한 출력
    log.info("테이블 'todos'가 초기화되었습니다.");
  }
}