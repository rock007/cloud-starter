package org.cloud.db.ad.repository;

import java.util.List;

import org.cloud.db.ad.entity.AdPostion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author sam
 *
 */
public interface AdPostionRepository extends CrudRepository<AdPostion, Long>,JpaSpecificationExecutor<AdPostion>{
	
	List<AdPostion> findByPostion(String postion);

	Page<AdPostion>  findAll(Pageable pageable);
	
	Page<AdPostion> findByPostionLike(String postion, Pageable pageable);
}
