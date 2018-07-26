package org.cloud.db.ad.service;

import java.util.List;

import org.cloud.db.ad.entity.AdPostion;
import org.springframework.data.domain.Page;

/**
 * @author sam
 *
 */
public interface AdPostionService {

	public AdPostion save(AdPostion m);
	
	public void delete(Long id);
	
	public List<AdPostion> getByPostion(String postion);
	
	public AdPostion findById(Long id);
	
	public Page<AdPostion> search(String postionlike, int page, int pageSize);
}
