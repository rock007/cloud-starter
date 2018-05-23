package org.cloud.rest.api.config.security;

import java.util.ArrayList;
import java.util.List;

import org.cloud.db.sys.entity.Role;
import org.cloud.db.sys.entity.SysUser;

import org.cloud.db.sys.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

@Component("accountAuthService")
public class AccountAuthService implements UserDetailsService{

    private static final Logger loggger = LoggerFactory.getLogger(AccountAuthService.class);
    
    @Bean
    public PasswordEncoder passwordEncoder() {
	       return new StandardPasswordEncoder();
	 }

    @Autowired
    private UserRepository userRepository;
    
	@Override
	public UserDetails loadUserByUsername(String name)
			throws UsernameNotFoundException {
		
		SysUser selUser= userRepository.findByUsername(name);
		
		if(selUser==null){
			
			loggger.warn("用户不存在！");
			throw new UsernameNotFoundException("用户没有找到！");
		}

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
  		for ( Role role : selUser.getRoles()){
  			SimpleGrantedAuthority auth = new SimpleGrantedAuthority( "ROLE_"+role.getName().trim());
  			authList.add(auth);
  		}

        return new User(selUser.getUsername(),  
        		selUser.getPassword()!=null?selUser.getPassword():passwordEncoder().encode("what-is-that"),
        				authList);

	}
	
}