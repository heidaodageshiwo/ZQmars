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
  
# 从users.xls文件获取10000条用户数据  
# 该文件由create_users.py生成  
def get_table():  
	#FILE_NAME = 'E:\tools\Python27\datetest.xlsx'  
	data = xlrd.open_workbook(r'E:\tools\Python27\data.xlsx')  
	table = data.sheets()[0]  
	return table  
  
# 循环插入execute 
def insert_by_loop(table):  
	nrows = table.nrows  
	for i in xrange(1,nrows):  
		param=[]  
		try:  
			sql = 'INSERT INTO user values(%s,%s,%s)'  
			# 第一列SN，第二列NAME，第三列LONGITUDE 
			print 'Insert: ',table.cell(i, 0).value, table.cell(i, 1).value, table.cell(i, 2).value  
			param = (table.cell(i, 0).value, table.cell(i, 1).value, table.cell(i, 2).value)  
			# 单条插入  
			cur.execute(sql, param)  
			conn.commit()  
		except Exception as e:  
			print e  
			conn.rollback()  
	print '[insert_by_loop execute] total:',nrows-1  
  
# 批量插入site 
def insert_site_by_many(table):  
	nrows = table.nrows  
	paramSite=[]
	for i in xrange(1,nrows):  
		# 第一列SN，第二列NAME，第三列LONGITUDE 
		paramSite.append([table.cell(i, 1).value, table.cell(i, 2).value, table.cell(i, 3).value, table.cell(i, 18).value, table.cell(i, 19).value, table.cell(i, 20).value, table.cell(i, 21).value, table.cell(i, 22).value, table.cell(i, 23).value, table.cell(i, 24).value, table.cell(i, 5).value, table.cell(i, 6).value, table.cell(i, 7).value, table.cell(i, 8).value, table.cell(i, 9).value, table.cell(i, 10).value])
	try:  
		sqlSite = 'INSERT INTO t_site(ID, SN, NAME, LONGITUDE, LATITUDE, ADDRESS, REMARK, DELETE_FLAG, CREATE_TIME, UPDATE_TIME, PROVINCE_ID, CITY_ID, TOWN_ID, PROVINCE_NAME, CITY_NAME, TOWN_NAME) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'  			
		# 批量插入  
		cur.executemany(sqlSite, paramSite)
		conn.commit(sqlSite, paramSite)  
	except Exception as e:  
		print e  
		conn.rollback()   
	print '[insert_by_many executemany] total:',nrows-1   
 
 # 批量插入device
def insert_device_by_many(table):  
	nrows = table.nrows  
	param=[]
	for i in xrange(1,nrows):  
		# 第一列SITE_ID，第二列SN，第三列NAME  
		param.append([table.cell(i, 1).value, table.cell(i, 11).value, table.cell(i, 12).value, table.cell(i, 13).value, table.cell(i, 14).value, table.cell(i, 15).value, table.cell(i, 16).value, table.cell(i, 21).value, table.cell(i, 23).value, table.cell(i, 24).value, table.cell(i, 22).value])
	try:  
		sql = 'INSERT INTO t_device(SITE_ID, SN, NAME, TYPE, BAND, OPERATOR, MANUFACTURER, REMARK, CREATE_TIME, UPDATE_TIME, DELETE_FLAG) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
		# 批量插入  
		cur.executemany(sql, param)
		conn.commit(sql, param) 
	except Exception as e:  
		print e  
		conn.rollback()   
	print '[insert_by_many executemany] total:',nrows-1  
  
# 连接数据库  
conn = MySQLdb.connect(host="127.0.0.1", port=3306, user="root", passwd="root", db="efence2", charset="utf8")  
cur = conn.cursor()  
  
# 新建数据库  
#cur.execute('DROP TABLE IF EXISTS user')  
#sql = """CREATE TABLE user( 
#		username CHAR(255) NOT NULL, 
#		salt CHAR(255), 
#		pwd CHAR(255) 
#		)"""  

#cur.execute(sql)  
  
# 从excel文件获取数据  
table = get_table()  
  
# 使用循环插入  
#start = time.clock()  
#insert_by_loop(table)  
#end = time.clock()  
#print '[insert_by_loop execute] Time Usage:',end-start  
  
# 使用批量插入  
start = time.clock()  
insert_device_by_many(table) 
insert_site_by_many(table)
end = time.clock()  
print '[insert_by_many executemany] Time Usage:',end-start  
  
# 释放数据连接  
if cur:  
	cur.close()  
if conn:  
	conn.close() 
