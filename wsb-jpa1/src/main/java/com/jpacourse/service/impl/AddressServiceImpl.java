package com.jpacourse.service.impl;

import com.jpacourse.dto.AddressTO;
import com.jpacourse.mapper.AddressMapper;
import com.jpacourse.persistance.dao.AddressDao;
import com.jpacourse.persistance.dao.Dao;
import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AddressServiceImpl extends AbstractService<AddressTO, AddressEntity, Long> implements AddressService {
    private final AddressDao addressDao;

    @Autowired
    public AddressServiceImpl(AddressDao pAddressDao) {
        addressDao = pAddressDao;
    }

    @Override
    protected Dao<AddressEntity, Long> getDao() {
        return addressDao;
    }

    @Override
    protected AddressTO mapToTO(AddressEntity entity) {
        return AddressMapper.mapToTO(entity);
    }

}