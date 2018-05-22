/**
*@Author: sam
*@Date: 2016年12月9日
*@Copyright: 2016  All rights reserved.
*/
package org.cloud.db.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import org.cloud.db.sys.entity.UploadFile;
import org.cloud.db.sys.entity.UploadFileRelate;


/**
 * @author sam
 *
 */
public interface UploadFileService {

	public UploadFile save(UploadFile m);
	
	public void delete(String id);
	
	public Page<UploadFile> getByStatus(Integer status,int page,int pageSize);
	
	public UploadFile findById(String id);
	
	public UploadFileRelate saveRelate(UploadFileRelate m) ;
	
	public void deleteRelate(Long id);
	
	public UploadFileRelate findRelateById(Long id);
	
	public List<UploadFileRelate> findRelateById(Integer ref_type,Long ref_id);
	
}
