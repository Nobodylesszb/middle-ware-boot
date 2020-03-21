package com.muti.data.source.service;

import com.muti.data.source.entity.UserBase;

public interface UserBaseService {

    UserBase selectByPrimaryKey(Integer id) ;

    void insert(UserBase record);
}
