package com.iekun.ef.service;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.util.Security;
import com.iekun.ef.util.SystemInfo;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by feilong.cai on 2016/12/15.
 */

@Service
public class LicenseService {

    private static Logger logger = LoggerFactory.getLogger(LicenseService.class);

    private static final int HEADER_LEN = 64;
    private static final int FOOTER_LEN = 77;

    private String productId;
    private String productEdition;
    private String productVersion;
    private String productKey;
    private String jreInfo;
    private String jvmInfo;

    private String licProductId;
    private String licProductEdition;
    private String licProductKey;
    private String licProductVersion;
    private Date licIssueDate;
    private Date licExpiryDate;
    private String licCompany;

    @Value(" ${storage.basePath}")
    private String basePathStr;

    @Value(" ${storage.licensePath}")
    private String licensePathStr;

    @PostConstruct
    public void init(){

        logger.info("License Service is construct!");
        this.productId = SystemInfo.PRODUCT_ID;
        this.productEdition = SystemInfo.PRODUCT_EDITION;
        this.productVersion = SystemInfo.getVersion();
        this.productKey = calcProductKey();

        this.jreInfo =  System.getProperty("java.version") + " " + System.getProperty("os.arch") ;
        this.jvmInfo = System.getProperty("java.vm.name");

        logger.info("Product ID:" + this.productId);
        logger.info("Product Edition:" + this.productEdition);
        logger.info("Product Version:" + this.productVersion);
        logger.info("Product Key:" + this.productKey);

        String filePath = basePathStr.trim() + licensePathStr.trim() + "/system/license.lic";
        if( !parseLicenseFile(filePath)) {
            logger.info("read license file error!");
        }

    }

    public boolean reloadLicenseFile(){
        String filePath = basePathStr.trim() + licensePathStr.trim() + "/system/license.lic";
        return parseLicenseFile(filePath);
    }


    public Integer verifyLicense() {

        if( (null == licProductKey) || ( null == licExpiryDate )  ) {
            return 1;
        }

        if( ! productKey.equals( licProductKey ) ) {
            return 2;
        }

        Date nowDate = new Date();
        if( this.licExpiryDate.getTime() < nowDate.getTime() ) {
            return 3;
        }

        return 0;
    }


    public String getProductId() {
        return productId;
    }

    public String getProductEdition() {
        return productEdition;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public String getProductKey() {
        return productKey;
    }

    public String getJreInfo() {
        return jreInfo;
    }

    public String getJvmInfo() {
        return jvmInfo;
    }

    public String getLicProductId() {
        return licProductId;
    }

    public String getLicProductEdition() {
        return licProductEdition;
    }

    public String getLicProductKey() {
        return licProductKey;
    }

    public String getLicProductVersion() {
        return licProductVersion;
    }

    public Date getLicIssueDate() {
        return licIssueDate;
    }

    public String getLicIssueDataText(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sDateFormat.format( this.licIssueDate );
    }

    public Date getLicExpiryDate() {
        return licExpiryDate;
    }

    public String getLicExpiryDateText() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sDateFormat.format( this.licExpiryDate );
    }

    public String getLicCompany() {
        return licCompany;
    }

    private String calcProductKey() {
        String key = null;
        try {

            key = "efence:" + getMacInfo() + this.productId + this.productEdition + SystemInfo.VERSION_MAJOR;

            Security security = new Security();
            key = security.encodeByMD5(key).toUpperCase();

            String result = "";
            for (int i=0; i < key.length(); i++) {
                if (i%6==0 && i > 0) {
                    result += '-';
                }
                result += key.charAt(i);
            }

            key = result;

        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return key;
    }

    private String getMacInfo() {
        Sigar sigar = null;
        try {
            sigar = new Sigar();
            String[] ifaces = sigar.getNetInterfaceList();
            String hwaddr = null;
            long mac1=0 ,mac2=0, mac3=0, mac4=0, mac5=0, mac6=0;
            for (int i = 0; i < ifaces.length; i++) {
                NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
                if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
                        || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                        || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
                    continue;
                }

                String currHwAddr = cfg.getHwaddr();
                String[] currHwAdddrByte =   currHwAddr.split(":");
                mac1 = mac1 ^ Long.parseLong(currHwAdddrByte[0], 16);
                mac2 = mac2 ^ Long.parseLong(currHwAdddrByte[1], 16);
                mac3 = mac3 ^ Long.parseLong(currHwAdddrByte[2], 16);
                mac4 = mac4 ^ Long.parseLong(currHwAdddrByte[3], 16);
                mac5 = mac5 ^ Long.parseLong(currHwAdddrByte[4], 16);
                mac6 = mac6 ^ Long.parseLong(currHwAdddrByte[5], 16);

            }
            hwaddr = String.format("%1$02X,%2$02X,%3$02X,%4$02X,%5$02X,%6$02X",
                    mac1, mac2, mac3, mac4, mac5, mac6);

            return hwaddr != null ? hwaddr : null;
        } catch (Exception e) {
            return null;
        } finally {
            if (sigar != null)
                sigar.close();
        }
    }

    private boolean parseLicenseFile( String filePath ) {

        String randomHeader="";
        String randomFooter="";
        String outLicense = "";

        boolean bResult = true;

        String decodeOutLicense="";
        File file = new File( filePath );
        FileInputStream inFile = null;

        try {

            //System.out.println(System.getProperty("file.encoding"));

            if( !file.exists()) {
                logger.info("Failed to license.lic file not exist!");
                return false;
            }

            StringBuffer content = new StringBuffer();
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            inFile = new FileInputStream( file );
            while ((bytesRead = inFile.read(buffer)) != -1) {
                content.append(new String(buffer, 0, bytesRead));
            }
            String readLicense = content.toString();

            Security security = new Security();
            decodeOutLicense = security.decodeByASE ( readLicense );
            decodeOutLicense = decodeOutLicense.substring( this.HEADER_LEN, decodeOutLicense.length()- this.FOOTER_LEN );
            String licenseText = new String( decodeOutLicense.getBytes("UTF-8") );
            parseLicenseTxt(licenseText);

            inFile.close();

        } catch (Exception e1) {
            e1.printStackTrace();
            bResult = false;
        } finally {
            if (inFile != null) {
                try {
                    inFile.close();
                } catch ( java.io.IOException e ) {
                    bResult = false;
                }
            }

        }

        return  bResult;
    }

    private void parseLicenseTxt( String licenseTxt )  throws  Exception {

        JSONObject jSONObject =  JSONObject.parseObject( licenseTxt );

        this.licProductId = jSONObject.getString("productId");
        this.licProductEdition = jSONObject.getString("productEdition");
        this.licProductVersion = jSONObject.getString("productVersion");
        this.licProductKey = jSONObject.getString("productKey");

        JSONObject jSONLicenseInfo = jSONObject.getJSONObject("licensedInfo");

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String strIssueDate = jSONLicenseInfo.getString("issueDate");
        this.licIssueDate = sDateFormat.parse( strIssueDate  );

        String strExpireDate = jSONLicenseInfo.getString("expireDate");
        this.licExpiryDate = sDateFormat.parse( strExpireDate  );

        this.licCompany = jSONLicenseInfo.getString("company");

    }

}
