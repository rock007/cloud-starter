/**
*@Author: sam
*@Date: 2016年12月13日
*@Copyright: 2016  All rights reserved.
*/
package org.cloud.db.sys.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import org.cloud.db.sys.entity.UploadFile;
import org.cloud.db.sys.entity.UploadFileRelate;
import org.cloud.db.sys.repository.UploadFileRelateRepository;
import org.cloud.db.sys.repository.UploadFileRepository;
import org.cloud.db.sys.service.UploadFileService;


/**
 * @author sam
 *
 */
@Component("uploadFileService")
public class UploadFileServiceImp implements UploadFileService{

	@Autowired
	private UploadFileRepository uploadFileRepository;
	
	@Autowired
	private UploadFileRelateRepository uploadFileRelateRepository;
	
	@Override
	public UploadFile save(UploadFile m) {
		
		return uploadFileRepository.save(m);
	}

	@Override
	public void delete(String id) {
		uploadFileRepository.delete(id);
	}

	@Override
	public Page<UploadFile> getByStatus(Integer status, int page, int pageSize) {
		
		return uploadFileRepository.findByStatusOrderByIdDesc(status, new PageRequest(page, pageSize));
	}

	@Override
	public UploadFile findById(String id) {

		return uploadFileRepository.findOne(id);
	}

	@Override
	public UploadFileRelate saveRelate(UploadFileRelate m) {
		
		return uploadFileRelateRepository.save(m);
	}

	@Override
	public void deleteRelate(Long id) {
		
		uploadFileRelateRepository.delete(id);
		
	}
	
	@Override
	public UploadFileRelate findRelateById(Long id) {

		return uploadFileRelateRepository.findOne(id);
	}
	
	@Override
	public List<UploadFileRelate> findRelateById(Integer ref_type,Long ref_id) {

		return uploadFileRelateRepository.findByRefTypeAndRefId(ref_type, ref_id);
	}
	
	
}
