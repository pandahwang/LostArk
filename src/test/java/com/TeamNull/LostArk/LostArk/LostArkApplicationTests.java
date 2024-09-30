package com.TeamNull.LostArk.LostArk;

import com.TeamNull.LostArk.LostArk.controller.OuterDataController;
import com.TeamNull.LostArk.LostArk.dto.OuterDataDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LostArkApplicationTests {

	private OuterDataController outerDataController;

	@Autowired
	public LostArkApplicationTests(OuterDataController outerDataController) {
		super();
		this.outerDataController = outerDataController;
	}

	@Test
	void testAlluser() {
		List<OuterDataDto> outerDataDtoList = this.outerDataController.alluser();
		assertEquals(26, outerDataDtoList.size());
		assertEquals("도화가", outerDataDtoList.get(0).getName());
		System.out.println(outerDataDtoList.get(0).getName());
		System.out.println(outerDataDtoList.get(0).getValue());
		System.out.println(outerDataDtoList.get(0).getIcon());
	}

	@Test
	void testCreateAlluser() throws IOException, ParseException {
		this.outerDataController.createAlluser();
	}

}
