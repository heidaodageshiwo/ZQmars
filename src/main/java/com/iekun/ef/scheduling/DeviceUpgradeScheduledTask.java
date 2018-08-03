package com.iekun.ef.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iekun.ef.service.DeviceLicenseService;
import com.iekun.ef.service.DeviceVersionService;


@Component
public class DeviceUpgradeScheduledTask {

	@Autowired
	DeviceVersionService dvVersinoService;
	
	@Autowired
	DeviceLicenseService dvLicenseService;
	
	  @Scheduled(fixedRate = 900000)
	  public void dvUpgradeTaskProcess(){ 
		  
		  dvVersinoService.devVersionNotifySend();
		  
		  dvLicenseService.licenseNotifySend();
	}
	  
}
