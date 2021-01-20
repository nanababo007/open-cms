package com.sample.app.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

	@GetMapping("/list")
    public ResponseEntity<Map<String,Object>> getList() {
		Map<String,Object> data = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        
    	Map<String,Object> row1 = new HashMap<String,Object>();
    	row1.put("row1_col1", "row1_value1");
    	row1.put("row1_col2", "row1_value2");

    	Map<String,Object> row2 = new HashMap<String,Object>();
    	row2.put("row2_col1", "row2_value1");
    	row2.put("row2_col2", "row2_value2");
    	
    	list.add(row1);
    	list.add(row2);
    	
    	data.put("list", list);
    	
    	return ResponseEntity.status(HttpStatus.OK).body(data);
    }
	
}
