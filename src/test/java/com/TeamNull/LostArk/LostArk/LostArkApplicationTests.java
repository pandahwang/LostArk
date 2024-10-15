package com.TeamNull.LostArk.LostArk;

import com.TeamNull.LostArk.LostArk.controller.CommentController;
import com.TeamNull.LostArk.LostArk.controller.DataController;
import com.TeamNull.LostArk.LostArk.controller.OuterDataController;
import com.TeamNull.LostArk.LostArk.controller.ResultController;
import com.TeamNull.LostArk.LostArk.dto.DataDto;
import com.TeamNull.LostArk.LostArk.dto.OuterDataDto;
import com.TeamNull.LostArk.LostArk.dto.ResultDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LostArkApplicationTests {

	private OuterDataController outerDataController;
	private DataController dataController;
	private ResultController resultController;
	private CommentController commentController;

	@Autowired
	public LostArkApplicationTests(OuterDataController outerDataController, DataController dataController, ResultController resultController, CommentController commentController) {
		super();
		this.outerDataController = outerDataController;
		this.dataController = dataController;
		this.resultController = resultController;
		this.commentController = commentController;
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

//	@Test
//	void testData() {
//		List<DataDto> dataDtoList = this.dataController.read();
//		assertEquals(26, dataDtoList.size());
//		System.out.println(dataDtoList.get(0).getName());
//		System.out.println(dataDtoList.get(0).getValue());
//		System.out.println(dataDtoList.get(0).getIcon());
//	}

//	@Test
//	void testTop5() {
//		this.resultController.top5(UUID.fromString("f9ceff67-e36b-43d9-89a8-263a334a6313"));
//	}

//	@Test
//	void testResult() {
//		ResponseEntity<List<ResultDto>> result = this.resultController.result(UUID.fromString("ffb057b9-0b82-4789-8440-ded61c248cd2"));
//		System.out.println(result.getBody().get(0).getName());
//	}

}
