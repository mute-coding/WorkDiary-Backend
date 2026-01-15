package com.rainey.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rainey.project.service.UnifiedService;

@SpringBootTest
class WorkdirayApplicationTests {

	@Test
	void contextLoads() {
	}
	  @Autowired
	    private UnifiedService unifiedService;

	    @Test
	    void testPrint() {
	        unifiedService.findAllWorkDiaries();
	    }

}
