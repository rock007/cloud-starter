package org.cloud.db.sms.service;

import org.cloud.db.sms.entity.OrgGroup;
import org.cloud.db.sms.entity.OrgUser;
import java.util.List;

import org.springframework.data.domain.Page;

public interface OrgService {

    public OrgGroup saveGroup(OrgGroup m);

    public void deleteGroup(long id);

    public OrgGroup getGroupById(long id);
    
    public List<OrgGroup> getGroupsByParentId(long parentId);

    public void saveUser(OrgUser m);

    public void deleteUser(long id);

    public OrgUser getUserById(long userId);
    
    public List<OrgUser> getUsersByOrgId(long orgId);
    
    public Page<OrgUser> searchUsersBy(final OrgUser m,List<Long> orgids,int page,int pageSize);

}
