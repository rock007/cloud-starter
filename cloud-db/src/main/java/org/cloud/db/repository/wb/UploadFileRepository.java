package org.cloud.db.repository.wb;

import org.cloud.db.entity.wb.UploadFile;
import org.springframework.data.repository.CrudRepository;

public interface UploadFileRepository extends CrudRepository<UploadFile, Long> {
	
}