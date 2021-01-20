package com.sample.app.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.service.SampleFileService;
import com.sample.app.util.StringUtil;
import com.sample.app.vo.SampleFileVO;

import egovframework.example.sample.service.SampleVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@RestController
@RequestMapping("/api/sample/file")
public class SampleFileApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleFileApiController.class);
	
	@Resource(name = "sampleFileService")
	private SampleFileService sampleFileService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@GetMapping("/list.do")
	public ResponseEntity<Map<String,Object>> selectSampleFileList(@ModelAttribute("searchVO") SampleFileVO searchVO) throws Exception {
		Map<String,Object> data = new HashMap<String,Object>();
		
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> sampleList = sampleFileService.selectSampleFileList(searchVO);
		data.put("resultList", sampleList);
		
		int totCnt = sampleFileService.selectSampleFileListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		data.put("paginationInfo", paginationInfo);

		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@GetMapping("/view.do")
	public ResponseEntity<Map<String,Object>> selectSampleFile(SampleFileVO sampleFileVO, @ModelAttribute("searchVO") SampleFileVO searchVO) throws Exception {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("sampleFileVO", sampleFileService.selectSampleFile(sampleFileVO));
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@GetMapping("/insert.do")
	public ResponseEntity<Map<String,Object>> insertSampleFileView(@ModelAttribute("searchVO") SampleFileVO searchVO) throws Exception {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("sampleVO", new SampleVO());
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@PostMapping("/insert.do")
	public ResponseEntity<Map<String,Object>> insertSampleFile(@ModelAttribute("searchVO") SampleFileVO searchVO, SampleFileVO sampleFileVO)
			throws Exception {
		
		Map<String,Object> data = new HashMap<String,Object>();
		
		if(StringUtil.isEmpty(sampleFileVO.getOrgFileName())) {
			data.put("error_message", "need_param");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
		}
		
		sampleFileService.insertSampleFile(sampleFileVO);
		
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@GetMapping("/update.do")
	public ResponseEntity<Map<String,Object>> updateSampleFileView(@ModelAttribute("searchVO") SampleFileVO searchVO) throws Exception {
		Map<String,Object> data = new HashMap<String,Object>();

		if(searchVO.getFileSeq()==0) {
			data.put("error_message", "need_param");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
		}
		
		SampleFileVO sampleFileVO = sampleFileService.selectSampleFile(searchVO);
		
		data.put("sampleFileVO", sampleFileVO);
		
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@PostMapping("/update.do")
	public ResponseEntity<Map<String,Object>> updateSampleFile(@ModelAttribute("searchVO") SampleFileVO searchVO, SampleFileVO sampleFileVO)
			throws Exception {
		
		Map<String,Object> data = new HashMap<String,Object>();

		if(StringUtil.isEmpty(searchVO.getOrgFileName())) {
			data.put("error_message", "need_param");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
		}
		
		sampleFileService.updateSampleFile(sampleFileVO);
		
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@PostMapping("/delete.do")
	public ResponseEntity<Map<String,Object>> deleteSampleFile(SampleFileVO sampleFileVO, @ModelAttribute("searchVO") SampleFileVO searchVO) throws Exception {
		Map<String,Object> data = new HashMap<String,Object>();
		
		sampleFileService.deleteSampleFile(sampleFileVO);
		
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

}
