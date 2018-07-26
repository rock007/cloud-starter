package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.Address;
import org.springframework.data.repository.CrudRepository;


public interface AddressRepository extends CrudRepository<Address, Long> {

    List<Address> findByCreateUser(String user);
}