package com.iekun.ef.jms.service;

import com.iekun.ef.dao.UeInfoMapper;
import com.iekun.ef.jms.vo.Message;

public interface IHandleMessageService {
	void handleMessage(Message message, UeInfoMapper ueInfoMapper);
}
