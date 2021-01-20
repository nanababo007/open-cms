/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sample.app.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sample.app.service.SampleFileService;
import com.sample.app.util.StringUtil;
import com.sample.app.vo.SampleFileVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping(value = "/sample/file")
public class SampleFileController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleFileController.class);
	
	@Resource(name = "sampleFileService")
	private SampleFileService sampleFileService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@RequestMapping(value = "/list.do")
	public String selectSampleFileList(@ModelAttribute("searchVO") SampleFileVO searchVO, ModelMap model) throws Exception {
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
		model.addAttribute("resultList", sampleList);

		int totCnt = sampleFileService.selectSampleFileListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sample/file/list";
	}
	
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String selectSampleFile(SampleFileVO sampleFileVO, @ModelAttribute("searchVO") SampleFileVO searchVO, ModelMap model) throws Exception {
		model.addAttribute("sampleFileVO", sampleFileService.selectSampleFile(sampleFileVO));
		return "sample/file/view";
	}
	
	@RequestMapping(value = "/insert.do", method = RequestMethod.GET)
	public String insertSampleFileView(@ModelAttribute("searchVO") SampleFileVO searchVO, Model model) throws Exception {
		model.addAttribute("sampleFileVO", new SampleFileVO());
		return "sample/file/insert";
	}

	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public String insertSampleFile(@ModelAttribute("searchVO") SampleFileVO searchVO, SampleFileVO sampleFileVO, Model model)
			throws Exception {

		System.out.println("insert controller is called ");

		if(StringUtil.isEmpty(sampleFileVO.getOrgFileName())) {
			model.addAttribute("sampleFileVO", sampleFileVO);
			return "sample/egovSampleRegister";
		}

		sampleFileService.insertSampleFile(sampleFileVO);

		return "forward:/sample/file/list.do";
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String updateSampleFileView(@ModelAttribute("searchVO") SampleFileVO searchVO, Model model) throws Exception {
		SampleFileVO sampleFileVO = sampleFileService.selectSampleFile(searchVO);
		
		model.addAttribute("sampleFileVO", sampleFileVO);
		
		return "sample/file/insert";
	}

	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public String updateSampleFile(@ModelAttribute("searchVO") SampleFileVO searchVO, SampleFileVO sampleFileVO, Model model)
			throws Exception {

		if(StringUtil.isEmpty(sampleFileVO.getOrgFileName())) {
			model.addAttribute("sampleFileVO", sampleFileVO);
			return "sample/egovSampleRegister";
		}

		sampleFileService.updateSampleFile(sampleFileVO);
		
		return "forward:/sample/file/list.do";
	}

	@RequestMapping("/delete.do")
	public String deleteSampleFile(SampleFileVO sampleFileVO, @ModelAttribute("searchVO") SampleFileVO searchVO) throws Exception {
		sampleFileService.deleteSampleFile(sampleFileVO);
		return "forward:/sample/file/list.do";
	}

}
