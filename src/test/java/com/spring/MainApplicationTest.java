package com.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MainApplicationTest {

  @Test
  public void applicationContextTest () {
    MainApplication.main(new String[]{});
  }
}
