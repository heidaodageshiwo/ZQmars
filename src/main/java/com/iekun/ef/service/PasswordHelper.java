package com.iekun.ef.service;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import com.iekun.ef.model.User;



import java.util.UUID;

@Component("passwordHelper")
public class PasswordHelper {
 
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "md5";
    private int hashIterations = 1;

    public void encryptPassword(User user) {

    	//String randomNumberGenerator = UUID.randomUUID().toString().replace("-","");
        //user.setSalt(randomNumberGenerator);
    	user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()==null?"":user.getSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }
    
    public boolean passwordEqual(User user, String inputPassword){
    	String newPassword = new SimpleHash(
                algorithmName,
                inputPassword,
                ByteSource.Util.bytes(user.getSalt()==null?"":user.getSalt()),
                hashIterations).toHex();
    	return user.getPassword().toUpperCase().equals(newPassword.toUpperCase());
    }
}
