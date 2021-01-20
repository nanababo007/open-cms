package com.sample.app.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.property.EgovPropertyService;

public class FileUtil {
	
	/**
	 * 확장자를 포함한 파일명에서, 확장자를 뺀 파일명만 반환
	 * @param fileOrgName : 파일 원본 이름
	 * @return : 확장자를 제외한 파일명 (abc.txt -> abc)
	 */
	public static String getBaseFileName(String fileOrgName) {
		String result = "";
		
		if(fileOrgName!=null) {
			int pos = fileOrgName .lastIndexOf(".");
			if(pos!=-1) {
				result = fileOrgName.substring(0, pos);
			}
		}
		
		return result;
	}
	
	/**
	 * 파일 원본이름에서 확장자만 반환
	 * @param fileOrgName : 파일 원본명
	 * @return : 파일명을 제외한 확장자만 반환 (abc.txt -> txt)
	 */
	public static String getFileExt(String fileOrgName) {
		String result = "";
		
		if(fileOrgName!=null) {
			int pos = fileOrgName .lastIndexOf(".");
			if(pos!=-1) {
				result = fileOrgName.substring(pos + 1);
			}
		}
		
		return result;
	}
	
	public static String getFileExt(MultipartFile mfile) {
		if(isMultipartFileNotNull(mfile)) {
			return getFileExt(mfile.getOriginalFilename());
		} else {
			return "";
		}
	}

	/**
	 * 파일 원본이름에서 .확장자 반환
	 * @param fileOrgName : 파일 원본명
	 * @return : 파일명을 제외한 확장자만 반환 (abc.txt -> .txt)
	 */
	public static String getFileDotExt(String fileOrgName) {
		String result = "";
		
		if(fileOrgName!=null) {
			int pos = fileOrgName .lastIndexOf(".");
			if(pos!=-1) {
				result = fileOrgName.substring(pos);
			}
		}
		
		return result;
	}

	/**
	 * 파일 원본이름에서 .확장자 반환
	 * @param mfile : 파일 업로드 객체
	 * @return : 파일명을 제외한 확장자만 반환 (abc.txt -> .txt)
	 */
	public static String getFileDotExt(MultipartFile mfile) {
		if(isMultipartFileNotNull(mfile)) {
			return getFileDotExt(mfile.getOriginalFilename());
		} else {
			return "";
		}
	}

	/**
	 * MultipartFile 객체가 업로드 파일의 유무 확인
	 * @param mfile : 업로드 파일 객체
	 * @return : 업로드 파일이 있으면 false, 없으면 true
	 */
	public static boolean isMultipartFileNull(MultipartFile mfile) {
		boolean result = true;
		
		if(mfile!=null && mfile.getOriginalFilename()!=null && !mfile.getOriginalFilename().equals("")) {
			result = false;
		}
		
		return result;
	}
	
	public static boolean isMultipartFileNotNull(MultipartFile mfile) {
		return !isMultipartFileNull(mfile);
	}
	
	public static String combinePath(String path1, String path2) {
		return combinePath(path1, path2, File.separator);
	}
	
	public static String combinePath(String path1, String path2, String pathSeperator) {
		String result = "";
		
		path1 = StringUtil.nvl(path1).trim();
		path2 = StringUtil.nvl(path2).trim();
		pathSeperator = StringUtil.nvl(pathSeperator).trim();
		
		result = path1;
		
		if(StringUtil.isNotEmpty(path1) && StringUtil.isNotEmpty(path2) && StringUtil.isNotEmpty(pathSeperator)) {
			if(!path1.endsWith(pathSeperator) && !path2.startsWith(pathSeperator)) {
				result = path1 + pathSeperator + path2;
			} else if(path1.endsWith(pathSeperator) && path2.startsWith(pathSeperator)) {
				result = path1 + path2.substring(pathSeperator.length());
			} else {
				result = path1 + path2;
			}
		}
		
		return result;
	}
	
	public static void makeFolder(String path) {
		path = StringUtil.nvl(path).trim();
		
		if(StringUtil.isNotEmpty(path)) {
			File folder = new File(path);
			if(!folder.exists() ) {
				folder.mkdirs();
			}
		}
	}
	
	public static boolean isFolderExists(String path) {
		boolean result = false;
		
		if(StringUtil.isNotEmpty(path)) {
			File folder = new File(path);
			if(folder.isDirectory() && folder.exists()) {
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * 파일저장 결과를 반환하는 클래스
	 * FileUtil.FileSaveInfo saveInfo = FileUtil.saveMultipartFile(...);
	 */
	public static class FileSaveInfo {
		private boolean isSuccess = false;
		private File file = null;
		private String fullPath = null;
		private String subPath = null;

		public boolean isSuccess() {
			return isSuccess;
		}

		public void setSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public String getFullPath() {
			return fullPath;
		}

		public void setFullPath(String fullPath) {
			this.fullPath = fullPath;
		}

		public String getSubPath() {
			return subPath;
		}

		public void setSubPath(String subPath) {
			this.subPath = subPath;
		}

		@Override
		public String toString() {
			return "FileSaveInfo [isSuccess=" + isSuccess + ", file=" + file + ", fullPath=" + fullPath + ", subPath="
					+ subPath + "]";
		}
		
	}
	
	/**
	 * 업로드 파일 저장처리
	 * @param prop : 환경설정 빈 주입받은 객체 (propertiesService)
	 * @param mfile : 업로드된 파일 객체
	 * @param subFolder : 저장할 하위폴더 이름
	 * @param filePrefix : 저장할 파일명 앞에 붙일 이름
	 * @return : FileUtil.FileSaveInfo 파일저장 결과정보 객체
	 * 
	 * 	@Resource(name = "propertiesService")
	 *	protected EgovPropertyService propertiesService;
	 */
	public static FileUtil.FileSaveInfo saveMultipartFile(EgovPropertyService propertiesService, MultipartFile mfile, String subFolder, String filePrefix) throws IOException {
		FileUtil.FileSaveInfo result = new FileUtil.FileSaveInfo();
		
		if(propertiesService!=null && !isMultipartFileNull(mfile) && StringUtil.isNotEmpty(subFolder)) {
			filePrefix = filePrefix==null ? "" : filePrefix.trim();
			
			String uploadFolder = PropertyServiceHelper.getUploadPath(propertiesService);
			if(isFolderExists(uploadFolder)) {
				String uploadSubFolder =  combinePath(uploadFolder,subFolder);
				makeFolder(uploadSubFolder);
				
				//파일저장 폴더 생성 (현재 년/월/일)
				String uploadSaveFolder = uploadSubFolder;
				String dateYear = DateUtil.getFormatDate("yyyy");
				String dateMon = DateUtil.getFormatDate("MM");
				String dateDay = DateUtil.getFormatDate("dd");
				
				uploadSaveFolder = combinePath(uploadSaveFolder,dateYear);
				makeFolder(uploadSaveFolder);
				
				uploadSaveFolder = combinePath(uploadSaveFolder,dateMon);
				makeFolder(uploadSaveFolder);
				
				uploadSaveFolder = combinePath(uploadSaveFolder,dateDay);
				makeFolder(uploadSaveFolder);
				
				//파일 저장처리
				String saveFileName = filePrefix + DateUtil.getFormatDate("HHmmss") + NumUtil.rand(1, 1000) + getFileDotExt(mfile);
				String uploadSaveFile =  combinePath(uploadSaveFolder,saveFileName);
				File uploadSaveFileObj = new File(uploadSaveFile);
				
				mfile.transferTo(uploadSaveFileObj);
				
				//파일 하위 저장경로
				String uploadSubPath = subFolder;
				uploadSubPath = combinePath(uploadSubPath, dateYear);
				uploadSubPath = combinePath(uploadSubPath, dateMon);
				uploadSubPath = combinePath(uploadSubPath, dateDay);
				uploadSubPath = combinePath(uploadSubPath, saveFileName);
				
				//결과값 셋팅
				result.setSuccess(true);
				result.setFullPath(uploadSaveFile);
				result.setFile(uploadSaveFileObj);
				result.setSubPath(uploadSubPath);
			}
		}
		
		return result;
	}
	
}
