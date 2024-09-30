package com.TeamNull.LostArk.LostArk;

import com.TeamNull.LostArk.LostArk.controller.DataController;
import com.TeamNull.LostArk.LostArk.controller.OuterDataController;
import com.TeamNull.LostArk.LostArk.dto.DataDto;
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
	private DataController dataController;

	@Autowired
	public LostArkApplicationTests(OuterDataController outerDataController, DataController dataController) {
		super();
		this.outerDataController = outerDataController;
		this.dataController = dataController;
	}

//	@Test
//	void testAlluser() {
//		List<OuterDataDto> outerDataDtoList = this.outerDataController.alluser();
//		assertEquals(26, outerDataDtoList.size());
//		assertEquals("도화가", outerDataDtoList.get(0).getName());
//		System.out.println(outerDataDtoList.get(0).getName());
//		System.out.println(outerDataDtoList.get(0).getValue());
//		System.out.println(outerDataDtoList.get(0).getIcon());
//	}
//
//	@Test
//	void testCreateAlluser() throws IOException, ParseException {
//		this.outerDataController.createAlluser();
//	}

	@Test
	void testData() {
		List<DataDto> dataDtoList = this.dataController.read();
		assertEquals(26, dataDtoList.size());
		System.out.println(dataDtoList.get(0).getName());
		System.out.println(dataDtoList.get(0).getValue());
		System.out.println(dataDtoList.get(0).getIcon());
	}

}
