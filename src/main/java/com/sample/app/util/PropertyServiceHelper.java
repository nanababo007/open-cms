package com.sample.app.util;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * context-properties.xml 파일의 속성값들을 키값을 알 필요없이 자동완성으로 가져다 쓰는 편의 클래스
 */
public class PropertyServiceHelper {

	/**
	 * 업로드 기본 경로 가져오는 함수
	 * @param propertiesService : 환경설정 주입받은 빈객체
	 * @return : 업로드 기본 경로 문자열
	 */
	public static String getUploadPath(EgovPropertyService propertiesService) {
		return StringUtil.nvl(propertiesService.getString("uploadPath")).trim();
	}

	/**
	 * 기본 목록 페이지 단위 갯수 가져오기
	 * @param propertiesService : 환경설정 주입받은 빈객체
	 * @return : 기본 목록 페이지 단위 갯수
	 */
	public static int getPageUnit(EgovPropertyService propertiesService) {
		return propertiesService.getInt("pageUnit");
	}
	
	/**
	 * 기본 목록 페이지 출력 갯수 가져오기
	 * @param propertiesService : 환경설정 주입받은 빈객체
	 * @return : 기본 목록 페이지 출력 갯수
	 */
	public static int getPageSize(EgovPropertyService propertiesService) {
		return propertiesService.getInt("pageSize");
	}

}
