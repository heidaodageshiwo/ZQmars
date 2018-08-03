package com.iekun.ef.model;

import java.util.ArrayList;
import java.util.List;

public class TargetAlarmExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public TargetAlarmExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNull() {
            addCriterion("SITE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNotNull() {
            addCriterion("SITE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSiteNameEqualTo(String value) {
            addCriterion("SITE_NAME =", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotEqualTo(String value) {
            addCriterion("SITE_NAME <>", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThan(String value) {
            addCriterion("SITE_NAME >", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThanOrEqualTo(String value) {
            addCriterion("SITE_NAME >=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThan(String value) {
            addCriterion("SITE_NAME <", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThanOrEqualTo(String value) {
            addCriterion("SITE_NAME <=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLike(String value) {
            addCriterion("SITE_NAME like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotLike(String value) {
            addCriterion("SITE_NAME not like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameIn(List<String> values) {
            addCriterion("SITE_NAME in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotIn(List<String> values) {
            addCriterion("SITE_NAME not in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameBetween(String value1, String value2) {
            addCriterion("SITE_NAME between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotBetween(String value1, String value2) {
            addCriterion("SITE_NAME not between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andDeviceSnIsNull() {
            addCriterion("DEVICE_SN is null");
            return (Criteria) this;
        }

        public Criteria andDeviceSnIsNotNull() {
            addCriterion("DEVICE_SN is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceSnEqualTo(String value) {
            addCriterion("DEVICE_SN =", value, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnNotEqualTo(String value) {
            addCriterion("DEVICE_SN <>", value, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnGreaterThan(String value) {
            addCriterion("DEVICE_SN >", value, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnGreaterThanOrEqualTo(String value) {
            addCriterion("DEVICE_SN >=", value, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnLessThan(String value) {
            addCriterion("DEVICE_SN <", value, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnLessThanOrEqualTo(String value) {
            addCriterion("DEVICE_SN <=", value, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnLike(String value) {
            addCriterion("DEVICE_SN like", value, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnNotLike(String value) {
            addCriterion("DEVICE_SN not like", value, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnIn(List<String> values) {
            addCriterion("DEVICE_SN in", values, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnNotIn(List<String> values) {
            addCriterion("DEVICE_SN not in", values, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnBetween(String value1, String value2) {
            addCriterion("DEVICE_SN between", value1, value2, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andDeviceSnNotBetween(String value1, String value2) {
            addCriterion("DEVICE_SN not between", value1, value2, "deviceSn");
            return (Criteria) this;
        }

        public Criteria andImsiIsNull() {
            addCriterion("IMSI is null");
            return (Criteria) this;
        }

        public Criteria andImsiIsNotNull() {
            addCriterion("IMSI is not null");
            return (Criteria) this;
        }

        public Criteria andImsiEqualTo(String value) {
            addCriterion("IMSI =", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotEqualTo(String value) {
            addCriterion("IMSI <>", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiGreaterThan(String value) {
            addCriterion("IMSI >", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiGreaterThanOrEqualTo(String value) {
            addCriterion("IMSI >=", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiLessThan(String value) {
            addCriterion("IMSI <", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiLessThanOrEqualTo(String value) {
            addCriterion("IMSI <=", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiLike(String value) {
            addCriterion("IMSI like", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotLike(String value) {
            addCriterion("IMSI not like", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiIn(List<String> values) {
            addCriterion("IMSI in", values, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotIn(List<String> values) {
            addCriterion("IMSI not in", values, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiBetween(String value1, String value2) {
            addCriterion("IMSI between", value1, value2, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotBetween(String value1, String value2) {
            addCriterion("IMSI not between", value1, value2, "imsi");
            return (Criteria) this;
        }

        public Criteria andImeiIsNull() {
            addCriterion("IMEI is null");
            return (Criteria) this;
        }

        public Criteria andImeiIsNotNull() {
            addCriterion("IMEI is not null");
            return (Criteria) this;
        }

        public Criteria andImeiEqualTo(String value) {
            addCriterion("IMEI =", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotEqualTo(String value) {
            addCriterion("IMEI <>", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiGreaterThan(String value) {
            addCriterion("IMEI >", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiGreaterThanOrEqualTo(String value) {
            addCriterion("IMEI >=", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiLessThan(String value) {
            addCriterion("IMEI <", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiLessThanOrEqualTo(String value) {
            addCriterion("IMEI <=", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiLike(String value) {
            addCriterion("IMEI like", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotLike(String value) {
            addCriterion("IMEI not like", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiIn(List<String> values) {
            addCriterion("IMEI in", values, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotIn(List<String> values) {
            addCriterion("IMEI not in", values, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiBetween(String value1, String value2) {
            addCriterion("IMEI between", value1, value2, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotBetween(String value1, String value2) {
            addCriterion("IMEI not between", value1, value2, "imei");
            return (Criteria) this;
        }

        public Criteria andStmsiIsNull() {
            addCriterion("STMSI is null");
            return (Criteria) this;
        }

        public Criteria andStmsiIsNotNull() {
            addCriterion("STMSI is not null");
            return (Criteria) this;
        }

        public Criteria andStmsiEqualTo(String value) {
            addCriterion("STMSI =", value, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiNotEqualTo(String value) {
            addCriterion("STMSI <>", value, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiGreaterThan(String value) {
            addCriterion("STMSI >", value, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiGreaterThanOrEqualTo(String value) {
            addCriterion("STMSI >=", value, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiLessThan(String value) {
            addCriterion("STMSI <", value, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiLessThanOrEqualTo(String value) {
            addCriterion("STMSI <=", value, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiLike(String value) {
            addCriterion("STMSI like", value, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiNotLike(String value) {
            addCriterion("STMSI not like", value, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiIn(List<String> values) {
            addCriterion("STMSI in", values, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiNotIn(List<String> values) {
            addCriterion("STMSI not in", values, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiBetween(String value1, String value2) {
            addCriterion("STMSI between", value1, value2, "stmsi");
            return (Criteria) this;
        }

        public Criteria andStmsiNotBetween(String value1, String value2) {
            addCriterion("STMSI not between", value1, value2, "stmsi");
            return (Criteria) this;
        }

        public Criteria andMacIsNull() {
            addCriterion("MAC is null");
            return (Criteria) this;
        }

        public Criteria andMacIsNotNull() {
            addCriterion("MAC is not null");
            return (Criteria) this;
        }

        public Criteria andMacEqualTo(String value) {
            addCriterion("MAC =", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotEqualTo(String value) {
            addCriterion("MAC <>", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacGreaterThan(String value) {
            addCriterion("MAC >", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacGreaterThanOrEqualTo(String value) {
            addCriterion("MAC >=", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLessThan(String value) {
            addCriterion("MAC <", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLessThanOrEqualTo(String value) {
            addCriterion("MAC <=", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLike(String value) {
            addCriterion("MAC like", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotLike(String value) {
            addCriterion("MAC not like", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacIn(List<String> values) {
            addCriterion("MAC in", values, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotIn(List<String> values) {
            addCriterion("MAC not in", values, "mac");
            return (Criteria) this;
        }

        public Criteria andMacBetween(String value1, String value2) {
            addCriterion("MAC between", value1, value2, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotBetween(String value1, String value2) {
            addCriterion("MAC not between", value1, value2, "mac");
            return (Criteria) this;
        }

        public Criteria andLatypeIsNull() {
            addCriterion("LATYPE is null");
            return (Criteria) this;
        }

        public Criteria andLatypeIsNotNull() {
            addCriterion("LATYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLatypeEqualTo(String value) {
            addCriterion("LATYPE =", value, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeNotEqualTo(String value) {
            addCriterion("LATYPE <>", value, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeGreaterThan(String value) {
            addCriterion("LATYPE >", value, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeGreaterThanOrEqualTo(String value) {
            addCriterion("LATYPE >=", value, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeLessThan(String value) {
            addCriterion("LATYPE <", value, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeLessThanOrEqualTo(String value) {
            addCriterion("LATYPE <=", value, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeLike(String value) {
            addCriterion("LATYPE like", value, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeNotLike(String value) {
            addCriterion("LATYPE not like", value, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeIn(List<String> values) {
            addCriterion("LATYPE in", values, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeNotIn(List<String> values) {
            addCriterion("LATYPE not in", values, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeBetween(String value1, String value2) {
            addCriterion("LATYPE between", value1, value2, "latype");
            return (Criteria) this;
        }

        public Criteria andLatypeNotBetween(String value1, String value2) {
            addCriterion("LATYPE not between", value1, value2, "latype");
            return (Criteria) this;
        }

        public Criteria andIndicationIsNull() {
            addCriterion("INDICATION is null");
            return (Criteria) this;
        }

        public Criteria andIndicationIsNotNull() {
            addCriterion("INDICATION is not null");
            return (Criteria) this;
        }

        public Criteria andIndicationEqualTo(Byte value) {
            addCriterion("INDICATION =", value, "indication");
            return (Criteria) this;
        }

        public Criteria andIndicationNotEqualTo(Byte value) {
            addCriterion("INDICATION <>", value, "indication");
            return (Criteria) this;
        }

        public Criteria andIndicationGreaterThan(Byte value) {
            addCriterion("INDICATION >", value, "indication");
            return (Criteria) this;
        }

        public Criteria andIndicationGreaterThanOrEqualTo(Byte value) {
            addCriterion("INDICATION >=", value, "indication");
            return (Criteria) this;
        }

        public Criteria andIndicationLessThan(Byte value) {
            addCriterion("INDICATION <", value, "indication");
            return (Criteria) this;
        }

        public Criteria andIndicationLessThanOrEqualTo(Byte value) {
            addCriterion("INDICATION <=", value, "indication");
            return (Criteria) this;
        }

        public Criteria andIndicationIn(List<Byte> values) {
            addCriterion("INDICATION in", values, "indication");
            return (Criteria) this;
        }

        public Criteria andIndicationNotIn(List<Byte> values) {
            addCriterion("INDICATION not in", values, "indication");
            return (Criteria) this;
        }

        public Criteria andIndicationBetween(Byte value1, Byte value2) {
            addCriterion("INDICATION between", value1, value2, "indication");
            return (Criteria) this;
        }

        public Criteria andIndicationNotBetween(Byte value1, Byte value2) {
            addCriterion("INDICATION not between", value1, value2, "indication");
            return (Criteria) this;
        }

        public Criteria andRealtimeIsNull() {
            addCriterion("REALTIME is null");
            return (Criteria) this;
        }

        public Criteria andRealtimeIsNotNull() {
            addCriterion("REALTIME is not null");
            return (Criteria) this;
        }

        public Criteria andRealtimeEqualTo(Boolean value) {
            addCriterion("REALTIME =", value, "realtime");
            return (Criteria) this;
        }

        public Criteria andRealtimeNotEqualTo(Boolean value) {
            addCriterion("REALTIME <>", value, "realtime");
            return (Criteria) this;
        }

        public Criteria andRealtimeGreaterThan(Boolean value) {
            addCriterion("REALTIME >", value, "realtime");
            return (Criteria) this;
        }

        public Criteria andRealtimeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("REALTIME >=", value, "realtime");
            return (Criteria) this;
        }

        public Criteria andRealtimeLessThan(Boolean value) {
            addCriterion("REALTIME <", value, "realtime");
            return (Criteria) this;
        }

        public Criteria andRealtimeLessThanOrEqualTo(Boolean value) {
            addCriterion("REALTIME <=", value, "realtime");
            return (Criteria) this;
        }

        public Criteria andRealtimeIn(List<Boolean> values) {
            addCriterion("REALTIME in", values, "realtime");
            return (Criteria) this;
        }

        public Criteria andRealtimeNotIn(List<Boolean> values) {
            addCriterion("REALTIME not in", values, "realtime");
            return (Criteria) this;
        }

        public Criteria andRealtimeBetween(Boolean value1, Boolean value2) {
            addCriterion("REALTIME between", value1, value2, "realtime");
            return (Criteria) this;
        }

        public Criteria andRealtimeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("REALTIME not between", value1, value2, "realtime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeIsNull() {
            addCriterion("CAPTURE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeIsNotNull() {
            addCriterion("CAPTURE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeEqualTo(String value) {
            addCriterion("CAPTURE_TIME =", value, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeNotEqualTo(String value) {
            addCriterion("CAPTURE_TIME <>", value, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeGreaterThan(String value) {
            addCriterion("CAPTURE_TIME >", value, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CAPTURE_TIME >=", value, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeLessThan(String value) {
            addCriterion("CAPTURE_TIME <", value, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeLessThanOrEqualTo(String value) {
            addCriterion("CAPTURE_TIME <=", value, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeLike(String value) {
            addCriterion("CAPTURE_TIME like", value, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeNotLike(String value) {
            addCriterion("CAPTURE_TIME not like", value, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeIn(List<String> values) {
            addCriterion("CAPTURE_TIME in", values, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeNotIn(List<String> values) {
            addCriterion("CAPTURE_TIME not in", values, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeBetween(String value1, String value2) {
            addCriterion("CAPTURE_TIME between", value1, value2, "captureTime");
            return (Criteria) this;
        }

        public Criteria andCaptureTimeNotBetween(String value1, String value2) {
            addCriterion("CAPTURE_TIME not between", value1, value2, "captureTime");
            return (Criteria) this;
        }

        public Criteria andRartaIsNull() {
            addCriterion("RARTA is null");
            return (Criteria) this;
        }

        public Criteria andRartaIsNotNull() {
            addCriterion("RARTA is not null");
            return (Criteria) this;
        }

        public Criteria andRartaEqualTo(Long value) {
            addCriterion("RARTA =", value, "rarta");
            return (Criteria) this;
        }

        public Criteria andRartaNotEqualTo(Long value) {
            addCriterion("RARTA <>", value, "rarta");
            return (Criteria) this;
        }

        public Criteria andRartaGreaterThan(Long value) {
            addCriterion("RARTA >", value, "rarta");
            return (Criteria) this;
        }

        public Criteria andRartaGreaterThanOrEqualTo(Long value) {
            addCriterion("RARTA >=", value, "rarta");
            return (Criteria) this;
        }

        public Criteria andRartaLessThan(Long value) {
            addCriterion("RARTA <", value, "rarta");
            return (Criteria) this;
        }

        public Criteria andRartaLessThanOrEqualTo(Long value) {
            addCriterion("RARTA <=", value, "rarta");
            return (Criteria) this;
        }

        public Criteria andRartaIn(List<Long> values) {
            addCriterion("RARTA in", values, "rarta");
            return (Criteria) this;
        }

        public Criteria andRartaNotIn(List<Long> values) {
            addCriterion("RARTA not in", values, "rarta");
            return (Criteria) this;
        }

        public Criteria andRartaBetween(Long value1, Long value2) {
            addCriterion("RARTA between", value1, value2, "rarta");
            return (Criteria) this;
        }

        public Criteria andRartaNotBetween(Long value1, Long value2) {
            addCriterion("RARTA not between", value1, value2, "rarta");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("CREATE_TIME like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("CREATE_TIME not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNull() {
            addCriterion("CITY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNotNull() {
            addCriterion("CITY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCityNameEqualTo(String value) {
            addCriterion("CITY_NAME =", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotEqualTo(String value) {
            addCriterion("CITY_NAME <>", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThan(String value) {
            addCriterion("CITY_NAME >", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThanOrEqualTo(String value) {
            addCriterion("CITY_NAME >=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThan(String value) {
            addCriterion("CITY_NAME <", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThanOrEqualTo(String value) {
            addCriterion("CITY_NAME <=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLike(String value) {
            addCriterion("CITY_NAME like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotLike(String value) {
            addCriterion("CITY_NAME not like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameIn(List<String> values) {
            addCriterion("CITY_NAME in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotIn(List<String> values) {
            addCriterion("CITY_NAME not in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameBetween(String value1, String value2) {
            addCriterion("CITY_NAME between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotBetween(String value1, String value2) {
            addCriterion("CITY_NAME not between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andBandIsNull() {
            addCriterion("BAND is null");
            return (Criteria) this;
        }

        public Criteria andBandIsNotNull() {
            addCriterion("BAND is not null");
            return (Criteria) this;
        }

        public Criteria andBandEqualTo(String value) {
            addCriterion("BAND =", value, "band");
            return (Criteria) this;
        }

        public Criteria andBandNotEqualTo(String value) {
            addCriterion("BAND <>", value, "band");
            return (Criteria) this;
        }

        public Criteria andBandGreaterThan(String value) {
            addCriterion("BAND >", value, "band");
            return (Criteria) this;
        }

        public Criteria andBandGreaterThanOrEqualTo(String value) {
            addCriterion("BAND >=", value, "band");
            return (Criteria) this;
        }

        public Criteria andBandLessThan(String value) {
            addCriterion("BAND <", value, "band");
            return (Criteria) this;
        }

        public Criteria andBandLessThanOrEqualTo(String value) {
            addCriterion("BAND <=", value, "band");
            return (Criteria) this;
        }

        public Criteria andBandLike(String value) {
            addCriterion("BAND like", value, "band");
            return (Criteria) this;
        }

        public Criteria andBandNotLike(String value) {
            addCriterion("BAND not like", value, "band");
            return (Criteria) this;
        }

        public Criteria andBandIn(List<String> values) {
            addCriterion("BAND in", values, "band");
            return (Criteria) this;
        }

        public Criteria andBandNotIn(List<String> values) {
            addCriterion("BAND not in", values, "band");
            return (Criteria) this;
        }

        public Criteria andBandBetween(String value1, String value2) {
            addCriterion("BAND between", value1, value2, "band");
            return (Criteria) this;
        }

        public Criteria andBandNotBetween(String value1, String value2) {
            addCriterion("BAND not between", value1, value2, "band");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("OPERATOR is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("OPERATOR is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("OPERATOR =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("OPERATOR <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("OPERATOR >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATOR >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("OPERATOR <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("OPERATOR <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("OPERATOR like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("OPERATOR not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("OPERATOR in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("OPERATOR not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("OPERATOR between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("OPERATOR not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionIsNull() {
            addCriterion("PHONE_SECTION is null");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionIsNotNull() {
            addCriterion("PHONE_SECTION is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionEqualTo(String value) {
            addCriterion("PHONE_SECTION =", value, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionNotEqualTo(String value) {
            addCriterion("PHONE_SECTION <>", value, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionGreaterThan(String value) {
            addCriterion("PHONE_SECTION >", value, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionGreaterThanOrEqualTo(String value) {
            addCriterion("PHONE_SECTION >=", value, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionLessThan(String value) {
            addCriterion("PHONE_SECTION <", value, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionLessThanOrEqualTo(String value) {
            addCriterion("PHONE_SECTION <=", value, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionLike(String value) {
            addCriterion("PHONE_SECTION like", value, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionNotLike(String value) {
            addCriterion("PHONE_SECTION not like", value, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionIn(List<String> values) {
            addCriterion("PHONE_SECTION in", values, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionNotIn(List<String> values) {
            addCriterion("PHONE_SECTION not in", values, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionBetween(String value1, String value2) {
            addCriterion("PHONE_SECTION between", value1, value2, "phoneSection");
            return (Criteria) this;
        }

        public Criteria andPhoneSectionNotBetween(String value1, String value2) {
            addCriterion("PHONE_SECTION not between", value1, value2, "phoneSection");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}