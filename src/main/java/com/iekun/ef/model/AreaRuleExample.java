package com.iekun.ef.model;

import java.util.ArrayList;
import java.util.List;

public class AreaRuleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public AreaRuleExample() {
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

        public Criteria andSourceCityCodeIsNull() {
            addCriterion("SOURCE_CITY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeIsNotNull() {
            addCriterion("SOURCE_CITY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeEqualTo(String value) {
            addCriterion("SOURCE_CITY_CODE =", value, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeNotEqualTo(String value) {
            addCriterion("SOURCE_CITY_CODE <>", value, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeGreaterThan(String value) {
            addCriterion("SOURCE_CITY_CODE >", value, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_CITY_CODE >=", value, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeLessThan(String value) {
            addCriterion("SOURCE_CITY_CODE <", value, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_CITY_CODE <=", value, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeLike(String value) {
            addCriterion("SOURCE_CITY_CODE like", value, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeNotLike(String value) {
            addCriterion("SOURCE_CITY_CODE not like", value, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeIn(List<String> values) {
            addCriterion("SOURCE_CITY_CODE in", values, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeNotIn(List<String> values) {
            addCriterion("SOURCE_CITY_CODE not in", values, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeBetween(String value1, String value2) {
            addCriterion("SOURCE_CITY_CODE between", value1, value2, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andSourceCityCodeNotBetween(String value1, String value2) {
            addCriterion("SOURCE_CITY_CODE not between", value1, value2, "sourceCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeIsNull() {
            addCriterion("DEST_CITY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeIsNotNull() {
            addCriterion("DEST_CITY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeEqualTo(String value) {
            addCriterion("DEST_CITY_CODE =", value, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeNotEqualTo(String value) {
            addCriterion("DEST_CITY_CODE <>", value, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeGreaterThan(String value) {
            addCriterion("DEST_CITY_CODE >", value, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeGreaterThanOrEqualTo(String value) {
            addCriterion("DEST_CITY_CODE >=", value, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeLessThan(String value) {
            addCriterion("DEST_CITY_CODE <", value, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeLessThanOrEqualTo(String value) {
            addCriterion("DEST_CITY_CODE <=", value, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeLike(String value) {
            addCriterion("DEST_CITY_CODE like", value, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeNotLike(String value) {
            addCriterion("DEST_CITY_CODE not like", value, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeIn(List<String> values) {
            addCriterion("DEST_CITY_CODE in", values, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeNotIn(List<String> values) {
            addCriterion("DEST_CITY_CODE not in", values, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeBetween(String value1, String value2) {
            addCriterion("DEST_CITY_CODE between", value1, value2, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andDestCityCodeNotBetween(String value1, String value2) {
            addCriterion("DEST_CITY_CODE not between", value1, value2, "destCityCode");
            return (Criteria) this;
        }

        public Criteria andPolicyIsNull() {
            addCriterion("POLICY is null");
            return (Criteria) this;
        }

        public Criteria andPolicyIsNotNull() {
            addCriterion("POLICY is not null");
            return (Criteria) this;
        }

        public Criteria andPolicyEqualTo(Byte value) {
            addCriterion("POLICY =", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyNotEqualTo(Byte value) {
            addCriterion("POLICY <>", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyGreaterThan(Byte value) {
            addCriterion("POLICY >", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyGreaterThanOrEqualTo(Byte value) {
            addCriterion("POLICY >=", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyLessThan(Byte value) {
            addCriterion("POLICY <", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyLessThanOrEqualTo(Byte value) {
            addCriterion("POLICY <=", value, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyIn(List<Byte> values) {
            addCriterion("POLICY in", values, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyNotIn(List<Byte> values) {
            addCriterion("POLICY not in", values, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyBetween(Byte value1, Byte value2) {
            addCriterion("POLICY between", value1, value2, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyNotBetween(Byte value1, Byte value2) {
            addCriterion("POLICY not between", value1, value2, "policy");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityIsNull() {
            addCriterion("POLICY_PRIORITY is null");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityIsNotNull() {
            addCriterion("POLICY_PRIORITY is not null");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityEqualTo(Byte value) {
            addCriterion("POLICY_PRIORITY =", value, "policyPriority");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityNotEqualTo(Byte value) {
            addCriterion("POLICY_PRIORITY <>", value, "policyPriority");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityGreaterThan(Byte value) {
            addCriterion("POLICY_PRIORITY >", value, "policyPriority");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityGreaterThanOrEqualTo(Byte value) {
            addCriterion("POLICY_PRIORITY >=", value, "policyPriority");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityLessThan(Byte value) {
            addCriterion("POLICY_PRIORITY <", value, "policyPriority");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityLessThanOrEqualTo(Byte value) {
            addCriterion("POLICY_PRIORITY <=", value, "policyPriority");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityIn(List<Byte> values) {
            addCriterion("POLICY_PRIORITY in", values, "policyPriority");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityNotIn(List<Byte> values) {
            addCriterion("POLICY_PRIORITY not in", values, "policyPriority");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityBetween(Byte value1, Byte value2) {
            addCriterion("POLICY_PRIORITY between", value1, value2, "policyPriority");
            return (Criteria) this;
        }

        public Criteria andPolicyPriorityNotBetween(Byte value1, Byte value2) {
            addCriterion("POLICY_PRIORITY not between", value1, value2, "policyPriority");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNull() {
            addCriterion("CREATOR_ID is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("CREATOR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(Long value) {
            addCriterion("CREATOR_ID =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(Long value) {
            addCriterion("CREATOR_ID <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(Long value) {
            addCriterion("CREATOR_ID >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("CREATOR_ID >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(Long value) {
            addCriterion("CREATOR_ID <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(Long value) {
            addCriterion("CREATOR_ID <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<Long> values) {
            addCriterion("CREATOR_ID in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<Long> values) {
            addCriterion("CREATOR_ID not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(Long value1, Long value2) {
            addCriterion("CREATOR_ID between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(Long value1, Long value2) {
            addCriterion("CREATOR_ID not between", value1, value2, "creatorId");
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