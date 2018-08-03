package com.iekun.ef.jms.vo.send;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iekun.ef.jms.service.HandleRcvUpgradeComplete;
import com.iekun.ef.util.ConvertTools;

public class SendUpgradeNotify {

		String  type;
		String  protocol;
		String  padding;
		String  filePath;
		String  signature;
		String  time;
		String  ftpIp;
		String  ftpPort;
		String  username;
		String  password;
		String  version;
	
		private static Logger logger = LoggerFactory.getLogger(HandleRcvUpgradeComplete.class);
		
		public String toString() {
			StringBuffer sb=new StringBuffer();
			
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.type)), 2));
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.protocol)), 2));
			sb.append("0000");
			
			try {
				byte[] filePathByte = this.filePath.getBytes("UTF-8");
				String filePathUtf8 = ConvertTools.bytesToHexString(filePathByte);
				sb.append(ConvertTools.fillZeroToEnd(filePathUtf8, 256));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				
				byte[] signatureByte = this.signature.getBytes("UTF-8");
				logger.info("signature "+ this.signature  + "signatureByte " + signatureByte.toString());
				String signatureUtf8 = ConvertTools.bytesToHexString(signatureByte);
				sb.append(ConvertTools.fillZeroToEnd(signatureUtf8, 256));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//sb.append(ConvertTools.fillZeroToEnd(this.signature, 256));
			
			try {
				byte[] timeByte = this.time.getBytes("UTF-8");
				String timeUtf8 = ConvertTools.bytesToHexString(timeByte);
				sb.append(ConvertTools.fillZeroToEnd(timeUtf8, 64));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//sb.append(ConvertTools.fillZeroToEnd(this.time, 64));
			
			try {
				//String tempIp = "12.21.21";
				byte[] ftpIpByte =this.ftpIp.getBytes("UTF-8");
				String ftpIpUtf8 = ConvertTools.bytesToHexString(ftpIpByte);
				sb.append(ConvertTools.fillZeroToEnd(ftpIpUtf8, 32));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//sb.append(ConvertTools.fillZeroToEnd(this.ftpIp, 32));
			
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.ftpPort)), 8));
			
			try {
				byte[] userNameByte = this.username.getBytes("UTF-8");
				String usernameUtf8 = ConvertTools.bytesToHexString(userNameByte);
				sb.append(ConvertTools.fillZeroToEnd(usernameUtf8, 64));
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			try {
				byte[] passWordByte = this.password.getBytes("UTF-8");
				String passwordUtf8 = ConvertTools.bytesToHexString(passWordByte);
				sb.append(ConvertTools.fillZeroToEnd(passwordUtf8, 64));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//sb.append(ConvertTools.fillZeroToEnd(this.password, 64));
			
			
			try {
				byte[] versionByte = this.version.getBytes("UTF-8");
				String versionUtf8 = ConvertTools.bytesToHexString(versionByte);
				sb.append(ConvertTools.fillZeroToEnd(versionUtf8, 256));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//sb.append(ConvertTools.fillZeroToEnd(this.version, 256));
			
			return sb.toString();
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getProtocol() {
			return protocol;
		}
		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}
		public String getPadding() {
			return padding;
		}
		public void setPadding(String padding) {
			this.padding = padding;
		}
		
		public String getSignature() {
			return signature;
		}
		public void setSignature(String signature) {
			this.signature = signature;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public String getFtpIp() {
			return ftpIp;
		}

		public void setFtpIp(String ftpIp) {
			this.ftpIp = ftpIp;
		}

		public String getFtpPort() {
			return ftpPort;
		}

		public void setFtpPort(String ftpPort) {
			this.ftpPort = ftpPort;
		}
		
}
