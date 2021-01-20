package com.sample.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sample.app.dao.SampleFileDAO;
import com.sample.app.service.SampleFileService;
import com.sample.app.vo.SampleFileVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("sampleFileService")
public class SampleFileServiceImpl extends EgovAbstractServiceImpl implements SampleFileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleFileServiceImpl.class);

	@Resource(name = "sampleFileDAO")
	private SampleFileDAO sampleFileDAO;

	@Override
	public int insertSampleFile(SampleFileVO vo) throws Exception {
		LOGGER.debug("SampleFileServiceImpl.insertSampleFile ::: " + vo.toString());
		
		sampleFileDAO.insertSampleFile(vo);
		return vo.getFileSeq();
	}

	@Override
	public void updateSampleFile(SampleFileVO vo) throws Exception {
		sampleFileDAO.updateSampleFile(vo);
	}

	@Override
	public void deleteSampleFile(SampleFileVO vo) throws Exception {
		sampleFileDAO.deleteSampleFile(vo);
	}

	@Override
	public SampleFileVO selectSampleFile(SampleFileVO vo) throws Exception {
		SampleFileVO resultVO = sampleFileDAO.selectSampleFile(vo);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}

	@Override
	public List<SampleFileVO> selectSampleFileList(SampleFileVO vo) throws Exception {
		return sampleFileDAO.selectSampleFileList(vo);
	}

	@Override
	public int selectSampleFileListTotCnt(SampleFileVO vo) {
		return sampleFileDAO.selectSampleFileListTotCnt(vo);
	}

}