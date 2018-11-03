package com.xk.bbs.service.impl;

import com.xk.bbs.bean.Notify;
import com.xk.bbs.dao.BaseDaoImpl;
import com.xk.bbs.service.NotifyService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class NotifyServiceImpl extends BaseDaoImpl<Notify> implements NotifyService {
}
