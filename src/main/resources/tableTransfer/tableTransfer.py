# coding:utf8
# -------------------------------------------  
# Python MySQLdb 循环插入execute与批量插入executemany性能分析  
# 插入数据量：  
# 每条字段：
# Author : chichester  
# -------------------------------------------  
import MySQLdb  
import xlrd  
import time  
import sys  
reload(sys)  
sys.setdefaultencoding("utf-8")  

EFENCEV1_HOST="localhost"
EFENCEV1_USERNAME="root"
EFENCEV1_PASSWORD="root"
EFENCEV1_DBNAME="efence"

EFENCEV2_HOST="localhost"
EFENCEV2_USERNAME="root"
EFENCEV2_PASSWORD="root"
EFENCEV2_DBNAME="efence2"

  
def queryUeInfo( db ):

    sql="SELECT V_DEVICE_SN, V_IMSI, V_IMEI, V_STMSI, V_LATYPE, N_BLACKLIST, N_REALTIME, V_CAPTURE_TIME, N_RARTA, V_CREATE_TIME from t_ue_info where N_UE_INFO_ID < 100"

    cursor = db.cursor()
    cursor.execute(sql)
    results = cursor.fetchall()
    cursor.close()
    return results
	
def querySiteDevice(db):
    sql ="SELECT  TD.SN as DEVICE_SN, TS.NAME as SITE_NAME, TS.SN as SITE_SN, TS.CITY_NAME, TS.CITY_CODE from t_site TS  LEFT JOIN t_device TD on TD.SITE_ID = TS.ID  where TD.DELETE_FLAG =0 AND TS.DELETE_FLAG = 0"  
    cursor = db.cursor()
    cursor.execute(sql)
    results = cursor.fetchall()
    cursor.close()
    return results

def  updateUeinfoData(db, captureDataSet):
     cursor = db.cursor()
     for dvSiteInfo in captureDataSet:
        deviceSn = dvSiteInfo[0]
        siteName = dvSiteInfo[1]
        siteSn   = dvSiteInfo[2]
        cityName = dvSiteInfo[3]
        print "deviceSn = %s, siteName = %s, siteSn =%s, cityName = %s"  % (deviceSn, siteName, siteSn, cityName)
        updateSql = u" update t_ue_info set SITE_NAME = '" + siteName + u"' ," + u" SITE_SN = '"  + siteSn  + u"' "+ u" WHERE DEVICE_SN = '" +  deviceSn + u"'"
        print updateSql
        cursor.execute(updateSql)
        #cursor.close()
	
def  insertUeinfoData(db, captureDataSet):
    cursor = db.cursor()
    for Ueinfo in captureDataSet:
		deviceSn = Ueinfo[0]
		imsi = Ueinfo[1]
		imei = Ueinfo[2]
		stmsi = Ueinfo[3]
		latype = Ueinfo[4]
		blackList = Ueinfo[5]
		realTime = Ueinfo[6]
		captureTime = Ueinfo[7]
		rarta = Ueinfo[8]
		createTime = Ueinfo[9]
		print 'DEVICE_SN = %s, imsi = %s, imei = %s, stmsi = %s, latype =%s, blackList =%d, realTime =%d, captureTime =%s, rarta =%d, createTime =%s' % (deviceSn, imsi, imei, stmsi, latype,blackList, realTime, captureTime, rarta, createTime)
		sqlx = "INSERT INTO t_ue_info(DEVICE_SN, IMSI, IMEI, STMSI, LATYPE, INDICATION, REALTIME, CAPTURE_TIME, RARTA, CREATE_TIME) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"  
		#sqlx = u"SELECT * from t_ue_info where 1=1"
		cursor.execute(sqlx, (deviceSn, imsi, imei, stmsi, latype, blackList, realTime, captureTime, rarta, createTime))
		#cursor.close()

def main():
	db = MySQLdb.connect(host=EFENCEV1_HOST, user=EFENCEV1_USERNAME, passwd=EFENCEV1_PASSWORD,
						 db=EFENCEV1_DBNAME, port=3306, charset="utf8")
	
	db2 = MySQLdb.connect(host=EFENCEV2_HOST, user=EFENCEV2_USERNAME, passwd=EFENCEV2_PASSWORD,
                         db=EFENCEV2_DBNAME, port=3306, charset="utf8")

	captureUeinfo = queryUeInfo(db)
	
	insertUeinfoData(db2, captureUeinfo)
	
	dvSiteInfo = querySiteDevice(db2)
	
	updateUeinfoData(db2, dvSiteInfo)
	
	
	print " exec is finished!"
	
	db.close()
	db2.close()
     

if __name__ == '__main__':
	main()