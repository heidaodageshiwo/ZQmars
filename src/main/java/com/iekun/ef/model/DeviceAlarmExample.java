package com.iekun.ef.model;

import java.util.ArrayList;
import java.util.List;

public class DeviceAlarmExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public DeviceAlarmExample() {
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

        public Criteria andAlarmTypeIsNull() {
            addCriterion("ALARM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeIsNotNull() {
            addCriterion("ALARM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeEqualTo(String value) {
            addCriterion("ALARM_TYPE =", value, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeNotEqualTo(String value) {
            addCriterion("ALARM_TYPE <>", value, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeGreaterThan(String value) {
            addCriterion("ALARM_TYPE >", value, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ALARM_TYPE >=", value, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeLessThan(String value) {
            addCriterion("ALARM_TYPE <", value, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeLessThanOrEqualTo(String value) {
            addCriterion("ALARM_TYPE <=", value, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeLike(String value) {
            addCriterion("ALARM_TYPE like", value, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeNotLike(String value) {
            addCriterion("ALARM_TYPE not like", value, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeIn(List<String> values) {
            addCriterion("ALARM_TYPE in", values, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeNotIn(List<String> values) {
            addCriterion("ALARM_TYPE not in", values, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeBetween(String value1, String value2) {
            addCriterion("ALARM_TYPE between", value1, value2, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmTypeNotBetween(String value1, String value2) {
            addCriterion("ALARM_TYPE not between", value1, value2, "alarmType");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeIsNull() {
            addCriterion("ALARM_CODE is null");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeIsNotNull() {
            addCriterion("ALARM_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeEqualTo(String value) {
            addCriterion("ALARM_CODE =", value, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeNotEqualTo(String value) {
            addCriterion("ALARM_CODE <>", value, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeGreaterThan(String value) {
            addCriterion("ALARM_CODE >", value, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ALARM_CODE >=", value, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeLessThan(String value) {
            addCriterion("ALARM_CODE <", value, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeLessThanOrEqualTo(String value) {
            addCriterion("ALARM_CODE <=", value, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeLike(String value) {
            addCriterion("ALARM_CODE like", value, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeNotLike(String value) {
            addCriterion("ALARM_CODE not like", value, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeIn(List<String> values) {
            addCriterion("ALARM_CODE in", values, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeNotIn(List<String> values) {
            addCriterion("ALARM_CODE not in", values, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeBetween(String value1, String value2) {
            addCriterion("ALARM_CODE between", value1, value2, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmCodeNotBetween(String value1, String value2) {
            addCriterion("ALARM_CODE not between", value1, value2, "alarmCode");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoIsNull() {
            addCriterion("ALARM_INFO is null");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoIsNotNull() {
            addCriterion("ALARM_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoEqualTo(String value) {
            addCriterion("ALARM_INFO =", value, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoNotEqualTo(String value) {
            addCriterion("ALARM_INFO <>", value, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoGreaterThan(String value) {
            addCriterion("ALARM_INFO >", value, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoGreaterThanOrEqualTo(String value) {
            addCriterion("ALARM_INFO >=", value, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoLessThan(String value) {
            addCriterion("ALARM_INFO <", value, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoLessThanOrEqualTo(String value) {
            addCriterion("ALARM_INFO <=", value, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoLike(String value) {
            addCriterion("ALARM_INFO like", value, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoNotLike(String value) {
            addCriterion("ALARM_INFO not like", value, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoIn(List<String> values) {
            addCriterion("ALARM_INFO in", values, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoNotIn(List<String> values) {
            addCriterion("ALARM_INFO not in", values, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoBetween(String value1, String value2) {
            addCriterion("ALARM_INFO between", value1, value2, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmInfoNotBetween(String value1, String value2) {
            addCriterion("ALARM_INFO not between", value1, value2, "alarmInfo");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeIsNull() {
            addCriterion("ALARM_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeIsNotNull() {
            addCriterion("ALARM_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeEqualTo(String value) {
            addCriterion("ALARM_TIME =", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeNotEqualTo(String value) {
            addCriterion("ALARM_TIME <>", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeGreaterThan(String value) {
            addCriterion("ALARM_TIME >", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ALARM_TIME >=", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeLessThan(String value) {
            addCriterion("ALARM_TIME <", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeLessThanOrEqualTo(String value) {
            addCriterion("ALARM_TIME <=", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeLike(String value) {
            addCriterion("ALARM_TIME like", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeNotLike(String value) {
            addCriterion("ALARM_TIME not like", value, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeIn(List<String> values) {
            addCriterion("ALARM_TIME in", values, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeNotIn(List<String> values) {
            addCriterion("ALARM_TIME not in", values, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeBetween(String value1, String value2) {
            addCriterion("ALARM_TIME between", value1, value2, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmTimeNotBetween(String value1, String value2) {
            addCriterion("ALARM_TIME not between", value1, value2, "alarmTime");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelIsNull() {
            addCriterion("ALARM_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelIsNotNull() {
            addCriterion("ALARM_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelEqualTo(String value) {
            addCriterion("ALARM_LEVEL =", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelNotEqualTo(String value) {
            addCriterion("ALARM_LEVEL <>", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelGreaterThan(String value) {
            addCriterion("ALARM_LEVEL >", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelGreaterThanOrEqualTo(String value) {
            addCriterion("ALARM_LEVEL >=", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelLessThan(String value) {
            addCriterion("ALARM_LEVEL <", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelLessThanOrEqualTo(String value) {
            addCriterion("ALARM_LEVEL <=", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelLike(String value) {
            addCriterion("ALARM_LEVEL like", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelNotLike(String value) {
            addCriterion("ALARM_LEVEL not like", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelIn(List<String> values) {
            addCriterion("ALARM_LEVEL in", values, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelNotIn(List<String> values) {
            addCriterion("ALARM_LEVEL not in", values, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelBetween(String value1, String value2) {
            addCriterion("ALARM_LEVEL between", value1, value2, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelNotBetween(String value1, String value2) {
            addCriterion("ALARM_LEVEL not between", value1, value2, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNull() {
            addCriterion("SITE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNotNull() {
            addCriterion("SITE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSiteIdEqualTo(Long value) {
            addCriterion("SITE_ID =", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotEqualTo(Long value) {
            addCriterion("SITE_ID <>", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThan(Long value) {
            addCriterion("SITE_ID >", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(Long value) {
            addCriterion("SITE_ID >=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThan(Long value) {
            addCriterion("SITE_ID <", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(Long value) {
            addCriterion("SITE_ID <=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIn(List<Long> values) {
            addCriterion("SITE_ID in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotIn(List<Long> values) {
            addCriterion("SITE_ID not in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdBetween(Long value1, Long value2) {
            addCriterion("SITE_ID between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotBetween(Long value1, Long value2) {
            addCriterion("SITE_ID not between", value1, value2, "siteId");
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