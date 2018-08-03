package com.iekun.ef.model;

import java.util.ArrayList;
import java.util.List;

public class OutboxExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public OutboxExample() {
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

        public Criteria andNotifyNameIsNull() {
            addCriterion("NOTIFY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andNotifyNameIsNotNull() {
            addCriterion("NOTIFY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyNameEqualTo(String value) {
            addCriterion("NOTIFY_NAME =", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameNotEqualTo(String value) {
            addCriterion("NOTIFY_NAME <>", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameGreaterThan(String value) {
            addCriterion("NOTIFY_NAME >", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameGreaterThanOrEqualTo(String value) {
            addCriterion("NOTIFY_NAME >=", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameLessThan(String value) {
            addCriterion("NOTIFY_NAME <", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameLessThanOrEqualTo(String value) {
            addCriterion("NOTIFY_NAME <=", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameLike(String value) {
            addCriterion("NOTIFY_NAME like", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameNotLike(String value) {
            addCriterion("NOTIFY_NAME not like", value, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameIn(List<String> values) {
            addCriterion("NOTIFY_NAME in", values, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameNotIn(List<String> values) {
            addCriterion("NOTIFY_NAME not in", values, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameBetween(String value1, String value2) {
            addCriterion("NOTIFY_NAME between", value1, value2, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyNameNotBetween(String value1, String value2) {
            addCriterion("NOTIFY_NAME not between", value1, value2, "notifyName");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneIsNull() {
            addCriterion("NOTIFY_PHONE is null");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneIsNotNull() {
            addCriterion("NOTIFY_PHONE is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneEqualTo(String value) {
            addCriterion("NOTIFY_PHONE =", value, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneNotEqualTo(String value) {
            addCriterion("NOTIFY_PHONE <>", value, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneGreaterThan(String value) {
            addCriterion("NOTIFY_PHONE >", value, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("NOTIFY_PHONE >=", value, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneLessThan(String value) {
            addCriterion("NOTIFY_PHONE <", value, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneLessThanOrEqualTo(String value) {
            addCriterion("NOTIFY_PHONE <=", value, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneLike(String value) {
            addCriterion("NOTIFY_PHONE like", value, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneNotLike(String value) {
            addCriterion("NOTIFY_PHONE not like", value, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneIn(List<String> values) {
            addCriterion("NOTIFY_PHONE in", values, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneNotIn(List<String> values) {
            addCriterion("NOTIFY_PHONE not in", values, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneBetween(String value1, String value2) {
            addCriterion("NOTIFY_PHONE between", value1, value2, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyPhoneNotBetween(String value1, String value2) {
            addCriterion("NOTIFY_PHONE not between", value1, value2, "notifyPhone");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailIsNull() {
            addCriterion("NOTIFY_EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailIsNotNull() {
            addCriterion("NOTIFY_EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailEqualTo(String value) {
            addCriterion("NOTIFY_EMAIL =", value, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailNotEqualTo(String value) {
            addCriterion("NOTIFY_EMAIL <>", value, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailGreaterThan(String value) {
            addCriterion("NOTIFY_EMAIL >", value, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailGreaterThanOrEqualTo(String value) {
            addCriterion("NOTIFY_EMAIL >=", value, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailLessThan(String value) {
            addCriterion("NOTIFY_EMAIL <", value, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailLessThanOrEqualTo(String value) {
            addCriterion("NOTIFY_EMAIL <=", value, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailLike(String value) {
            addCriterion("NOTIFY_EMAIL like", value, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailNotLike(String value) {
            addCriterion("NOTIFY_EMAIL not like", value, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailIn(List<String> values) {
            addCriterion("NOTIFY_EMAIL in", values, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailNotIn(List<String> values) {
            addCriterion("NOTIFY_EMAIL not in", values, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailBetween(String value1, String value2) {
            addCriterion("NOTIFY_EMAIL between", value1, value2, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyEmailNotBetween(String value1, String value2) {
            addCriterion("NOTIFY_EMAIL not between", value1, value2, "notifyEmail");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeIsNull() {
            addCriterion("NOTIFY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeIsNotNull() {
            addCriterion("NOTIFY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeEqualTo(Byte value) {
            addCriterion("NOTIFY_TYPE =", value, "notifyType");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeNotEqualTo(Byte value) {
            addCriterion("NOTIFY_TYPE <>", value, "notifyType");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeGreaterThan(Byte value) {
            addCriterion("NOTIFY_TYPE >", value, "notifyType");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("NOTIFY_TYPE >=", value, "notifyType");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeLessThan(Byte value) {
            addCriterion("NOTIFY_TYPE <", value, "notifyType");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeLessThanOrEqualTo(Byte value) {
            addCriterion("NOTIFY_TYPE <=", value, "notifyType");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeIn(List<Byte> values) {
            addCriterion("NOTIFY_TYPE in", values, "notifyType");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeNotIn(List<Byte> values) {
            addCriterion("NOTIFY_TYPE not in", values, "notifyType");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeBetween(Byte value1, Byte value2) {
            addCriterion("NOTIFY_TYPE between", value1, value2, "notifyType");
            return (Criteria) this;
        }

        public Criteria andNotifyTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("NOTIFY_TYPE not between", value1, value2, "notifyType");
            return (Criteria) this;
        }

        public Criteria andEventTypeIsNull() {
            addCriterion("EVENT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andEventTypeIsNotNull() {
            addCriterion("EVENT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andEventTypeEqualTo(Byte value) {
            addCriterion("EVENT_TYPE =", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeNotEqualTo(Byte value) {
            addCriterion("EVENT_TYPE <>", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeGreaterThan(Byte value) {
            addCriterion("EVENT_TYPE >", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("EVENT_TYPE >=", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeLessThan(Byte value) {
            addCriterion("EVENT_TYPE <", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeLessThanOrEqualTo(Byte value) {
            addCriterion("EVENT_TYPE <=", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeIn(List<Byte> values) {
            addCriterion("EVENT_TYPE in", values, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeNotIn(List<Byte> values) {
            addCriterion("EVENT_TYPE not in", values, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeBetween(Byte value1, Byte value2) {
            addCriterion("EVENT_TYPE between", value1, value2, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("EVENT_TYPE not between", value1, value2, "eventType");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("STATUS not between", value1, value2, "status");
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

        public Criteria andLastSendTimeIsNull() {
            addCriterion("LAST_SEND_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeIsNotNull() {
            addCriterion("LAST_SEND_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeEqualTo(String value) {
            addCriterion("LAST_SEND_TIME =", value, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeNotEqualTo(String value) {
            addCriterion("LAST_SEND_TIME <>", value, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeGreaterThan(String value) {
            addCriterion("LAST_SEND_TIME >", value, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeGreaterThanOrEqualTo(String value) {
            addCriterion("LAST_SEND_TIME >=", value, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeLessThan(String value) {
            addCriterion("LAST_SEND_TIME <", value, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeLessThanOrEqualTo(String value) {
            addCriterion("LAST_SEND_TIME <=", value, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeLike(String value) {
            addCriterion("LAST_SEND_TIME like", value, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeNotLike(String value) {
            addCriterion("LAST_SEND_TIME not like", value, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeIn(List<String> values) {
            addCriterion("LAST_SEND_TIME in", values, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeNotIn(List<String> values) {
            addCriterion("LAST_SEND_TIME not in", values, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeBetween(String value1, String value2) {
            addCriterion("LAST_SEND_TIME between", value1, value2, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andLastSendTimeNotBetween(String value1, String value2) {
            addCriterion("LAST_SEND_TIME not between", value1, value2, "lastSendTime");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNull() {
            addCriterion("DELETE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNotNull() {
            addCriterion("DELETE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG =", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG <>", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThan(Boolean value) {
            addCriterion("DELETE_FLAG >", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG >=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThan(Boolean value) {
            addCriterion("DELETE_FLAG <", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("DELETE_FLAG <=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIn(List<Boolean> values) {
            addCriterion("DELETE_FLAG in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotIn(List<Boolean> values) {
            addCriterion("DELETE_FLAG not in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("DELETE_FLAG between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("DELETE_FLAG not between", value1, value2, "deleteFlag");
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