package com.iekun.ef.model;

import java.util.ArrayList;
import java.util.List;

public class UpgradeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public UpgradeExample() {
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

        public Criteria andVersionIdIsNull() {
            addCriterion("VERSION_ID is null");
            return (Criteria) this;
        }

        public Criteria andVersionIdIsNotNull() {
            addCriterion("VERSION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andVersionIdEqualTo(Long value) {
            addCriterion("VERSION_ID =", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdNotEqualTo(Long value) {
            addCriterion("VERSION_ID <>", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdGreaterThan(Long value) {
            addCriterion("VERSION_ID >", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdGreaterThanOrEqualTo(Long value) {
            addCriterion("VERSION_ID >=", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdLessThan(Long value) {
            addCriterion("VERSION_ID <", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdLessThanOrEqualTo(Long value) {
            addCriterion("VERSION_ID <=", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdIn(List<Long> values) {
            addCriterion("VERSION_ID in", values, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdNotIn(List<Long> values) {
            addCriterion("VERSION_ID not in", values, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdBetween(Long value1, Long value2) {
            addCriterion("VERSION_ID between", value1, value2, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdNotBetween(Long value1, Long value2) {
            addCriterion("VERSION_ID not between", value1, value2, "versionId");
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

        public Criteria andOldVersionIsNull() {
            addCriterion("OLD_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andOldVersionIsNotNull() {
            addCriterion("OLD_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andOldVersionEqualTo(String value) {
            addCriterion("OLD_VERSION =", value, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionNotEqualTo(String value) {
            addCriterion("OLD_VERSION <>", value, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionGreaterThan(String value) {
            addCriterion("OLD_VERSION >", value, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_VERSION >=", value, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionLessThan(String value) {
            addCriterion("OLD_VERSION <", value, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionLessThanOrEqualTo(String value) {
            addCriterion("OLD_VERSION <=", value, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionLike(String value) {
            addCriterion("OLD_VERSION like", value, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionNotLike(String value) {
            addCriterion("OLD_VERSION not like", value, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionIn(List<String> values) {
            addCriterion("OLD_VERSION in", values, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionNotIn(List<String> values) {
            addCriterion("OLD_VERSION not in", values, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionBetween(String value1, String value2) {
            addCriterion("OLD_VERSION between", value1, value2, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldVersionNotBetween(String value1, String value2) {
            addCriterion("OLD_VERSION not between", value1, value2, "oldVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionIsNull() {
            addCriterion("OLD_FPGA_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionIsNotNull() {
            addCriterion("OLD_FPGA_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionEqualTo(String value) {
            addCriterion("OLD_FPGA_VERSION =", value, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionNotEqualTo(String value) {
            addCriterion("OLD_FPGA_VERSION <>", value, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionGreaterThan(String value) {
            addCriterion("OLD_FPGA_VERSION >", value, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_FPGA_VERSION >=", value, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionLessThan(String value) {
            addCriterion("OLD_FPGA_VERSION <", value, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionLessThanOrEqualTo(String value) {
            addCriterion("OLD_FPGA_VERSION <=", value, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionLike(String value) {
            addCriterion("OLD_FPGA_VERSION like", value, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionNotLike(String value) {
            addCriterion("OLD_FPGA_VERSION not like", value, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionIn(List<String> values) {
            addCriterion("OLD_FPGA_VERSION in", values, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionNotIn(List<String> values) {
            addCriterion("OLD_FPGA_VERSION not in", values, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionBetween(String value1, String value2) {
            addCriterion("OLD_FPGA_VERSION between", value1, value2, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldFpgaVersionNotBetween(String value1, String value2) {
            addCriterion("OLD_FPGA_VERSION not between", value1, value2, "oldFpgaVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionIsNull() {
            addCriterion("OLD_BBU_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionIsNotNull() {
            addCriterion("OLD_BBU_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionEqualTo(String value) {
            addCriterion("OLD_BBU_VERSION =", value, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionNotEqualTo(String value) {
            addCriterion("OLD_BBU_VERSION <>", value, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionGreaterThan(String value) {
            addCriterion("OLD_BBU_VERSION >", value, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_BBU_VERSION >=", value, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionLessThan(String value) {
            addCriterion("OLD_BBU_VERSION <", value, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionLessThanOrEqualTo(String value) {
            addCriterion("OLD_BBU_VERSION <=", value, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionLike(String value) {
            addCriterion("OLD_BBU_VERSION like", value, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionNotLike(String value) {
            addCriterion("OLD_BBU_VERSION not like", value, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionIn(List<String> values) {
            addCriterion("OLD_BBU_VERSION in", values, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionNotIn(List<String> values) {
            addCriterion("OLD_BBU_VERSION not in", values, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionBetween(String value1, String value2) {
            addCriterion("OLD_BBU_VERSION between", value1, value2, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldBbuVersionNotBetween(String value1, String value2) {
            addCriterion("OLD_BBU_VERSION not between", value1, value2, "oldBbuVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionIsNull() {
            addCriterion("OLD_SW_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionIsNotNull() {
            addCriterion("OLD_SW_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionEqualTo(String value) {
            addCriterion("OLD_SW_VERSION =", value, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionNotEqualTo(String value) {
            addCriterion("OLD_SW_VERSION <>", value, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionGreaterThan(String value) {
            addCriterion("OLD_SW_VERSION >", value, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionGreaterThanOrEqualTo(String value) {
            addCriterion("OLD_SW_VERSION >=", value, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionLessThan(String value) {
            addCriterion("OLD_SW_VERSION <", value, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionLessThanOrEqualTo(String value) {
            addCriterion("OLD_SW_VERSION <=", value, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionLike(String value) {
            addCriterion("OLD_SW_VERSION like", value, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionNotLike(String value) {
            addCriterion("OLD_SW_VERSION not like", value, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionIn(List<String> values) {
            addCriterion("OLD_SW_VERSION in", values, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionNotIn(List<String> values) {
            addCriterion("OLD_SW_VERSION not in", values, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionBetween(String value1, String value2) {
            addCriterion("OLD_SW_VERSION between", value1, value2, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andOldSwVersionNotBetween(String value1, String value2) {
            addCriterion("OLD_SW_VERSION not between", value1, value2, "oldSwVersion");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeIsNull() {
            addCriterion("SCHEDULE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeIsNotNull() {
            addCriterion("SCHEDULE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeEqualTo(String value) {
            addCriterion("SCHEDULE_TIME =", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeNotEqualTo(String value) {
            addCriterion("SCHEDULE_TIME <>", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeGreaterThan(String value) {
            addCriterion("SCHEDULE_TIME >", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeGreaterThanOrEqualTo(String value) {
            addCriterion("SCHEDULE_TIME >=", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeLessThan(String value) {
            addCriterion("SCHEDULE_TIME <", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeLessThanOrEqualTo(String value) {
            addCriterion("SCHEDULE_TIME <=", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeLike(String value) {
            addCriterion("SCHEDULE_TIME like", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeNotLike(String value) {
            addCriterion("SCHEDULE_TIME not like", value, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeIn(List<String> values) {
            addCriterion("SCHEDULE_TIME in", values, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeNotIn(List<String> values) {
            addCriterion("SCHEDULE_TIME not in", values, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeBetween(String value1, String value2) {
            addCriterion("SCHEDULE_TIME between", value1, value2, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andScheduleTimeNotBetween(String value1, String value2) {
            addCriterion("SCHEDULE_TIME not between", value1, value2, "scheduleTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeIsNull() {
            addCriterion("SUCCESS_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeIsNotNull() {
            addCriterion("SUCCESS_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeEqualTo(String value) {
            addCriterion("SUCCESS_TIME =", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeNotEqualTo(String value) {
            addCriterion("SUCCESS_TIME <>", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeGreaterThan(String value) {
            addCriterion("SUCCESS_TIME >", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeGreaterThanOrEqualTo(String value) {
            addCriterion("SUCCESS_TIME >=", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeLessThan(String value) {
            addCriterion("SUCCESS_TIME <", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeLessThanOrEqualTo(String value) {
            addCriterion("SUCCESS_TIME <=", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeLike(String value) {
            addCriterion("SUCCESS_TIME like", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeNotLike(String value) {
            addCriterion("SUCCESS_TIME not like", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeIn(List<String> values) {
            addCriterion("SUCCESS_TIME in", values, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeNotIn(List<String> values) {
            addCriterion("SUCCESS_TIME not in", values, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeBetween(String value1, String value2) {
            addCriterion("SUCCESS_TIME between", value1, value2, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeNotBetween(String value1, String value2) {
            addCriterion("SUCCESS_TIME not between", value1, value2, "successTime");
            return (Criteria) this;
        }

        public Criteria andRetrtiesIsNull() {
            addCriterion("RETRTIES is null");
            return (Criteria) this;
        }

        public Criteria andRetrtiesIsNotNull() {
            addCriterion("RETRTIES is not null");
            return (Criteria) this;
        }

        public Criteria andRetrtiesEqualTo(Byte value) {
            addCriterion("RETRTIES =", value, "retrties");
            return (Criteria) this;
        }

        public Criteria andRetrtiesNotEqualTo(Byte value) {
            addCriterion("RETRTIES <>", value, "retrties");
            return (Criteria) this;
        }

        public Criteria andRetrtiesGreaterThan(Byte value) {
            addCriterion("RETRTIES >", value, "retrties");
            return (Criteria) this;
        }

        public Criteria andRetrtiesGreaterThanOrEqualTo(Byte value) {
            addCriterion("RETRTIES >=", value, "retrties");
            return (Criteria) this;
        }

        public Criteria andRetrtiesLessThan(Byte value) {
            addCriterion("RETRTIES <", value, "retrties");
            return (Criteria) this;
        }

        public Criteria andRetrtiesLessThanOrEqualTo(Byte value) {
            addCriterion("RETRTIES <=", value, "retrties");
            return (Criteria) this;
        }

        public Criteria andRetrtiesIn(List<Byte> values) {
            addCriterion("RETRTIES in", values, "retrties");
            return (Criteria) this;
        }

        public Criteria andRetrtiesNotIn(List<Byte> values) {
            addCriterion("RETRTIES not in", values, "retrties");
            return (Criteria) this;
        }

        public Criteria andRetrtiesBetween(Byte value1, Byte value2) {
            addCriterion("RETRTIES between", value1, value2, "retrties");
            return (Criteria) this;
        }

        public Criteria andRetrtiesNotBetween(Byte value1, Byte value2) {
            addCriterion("RETRTIES not between", value1, value2, "retrties");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("UPDATE_TIME like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("UPDATE_TIME not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andStatusFlagIsNull() {
            addCriterion("STATUS_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andStatusFlagIsNotNull() {
            addCriterion("STATUS_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andStatusFlagEqualTo(Byte value) {
            addCriterion("STATUS_FLAG =", value, "statusFlag");
            return (Criteria) this;
        }

        public Criteria andStatusFlagNotEqualTo(Byte value) {
            addCriterion("STATUS_FLAG <>", value, "statusFlag");
            return (Criteria) this;
        }

        public Criteria andStatusFlagGreaterThan(Byte value) {
            addCriterion("STATUS_FLAG >", value, "statusFlag");
            return (Criteria) this;
        }

        public Criteria andStatusFlagGreaterThanOrEqualTo(Byte value) {
            addCriterion("STATUS_FLAG >=", value, "statusFlag");
            return (Criteria) this;
        }

        public Criteria andStatusFlagLessThan(Byte value) {
            addCriterion("STATUS_FLAG <", value, "statusFlag");
            return (Criteria) this;
        }

        public Criteria andStatusFlagLessThanOrEqualTo(Byte value) {
            addCriterion("STATUS_FLAG <=", value, "statusFlag");
            return (Criteria) this;
        }

        public Criteria andStatusFlagIn(List<Byte> values) {
            addCriterion("STATUS_FLAG in", values, "statusFlag");
            return (Criteria) this;
        }

        public Criteria andStatusFlagNotIn(List<Byte> values) {
            addCriterion("STATUS_FLAG not in", values, "statusFlag");
            return (Criteria) this;
        }

        public Criteria andStatusFlagBetween(Byte value1, Byte value2) {
            addCriterion("STATUS_FLAG between", value1, value2, "statusFlag");
            return (Criteria) this;
        }

        public Criteria andStatusFlagNotBetween(Byte value1, Byte value2) {
            addCriterion("STATUS_FLAG not between", value1, value2, "statusFlag");
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