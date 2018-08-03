package com.iekun.ef.model;

import java.util.ArrayList;
import java.util.List;

public class VersionLibExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public VersionLibExample() {
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

        public Criteria andVersionTypeIsNull() {
            addCriterion("VERSION_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andVersionTypeIsNotNull() {
            addCriterion("VERSION_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andVersionTypeEqualTo(Byte value) {
            addCriterion("VERSION_TYPE =", value, "versionType");
            return (Criteria) this;
        }

        public Criteria andVersionTypeNotEqualTo(Byte value) {
            addCriterion("VERSION_TYPE <>", value, "versionType");
            return (Criteria) this;
        }

        public Criteria andVersionTypeGreaterThan(Byte value) {
            addCriterion("VERSION_TYPE >", value, "versionType");
            return (Criteria) this;
        }

        public Criteria andVersionTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("VERSION_TYPE >=", value, "versionType");
            return (Criteria) this;
        }

        public Criteria andVersionTypeLessThan(Byte value) {
            addCriterion("VERSION_TYPE <", value, "versionType");
            return (Criteria) this;
        }

        public Criteria andVersionTypeLessThanOrEqualTo(Byte value) {
            addCriterion("VERSION_TYPE <=", value, "versionType");
            return (Criteria) this;
        }

        public Criteria andVersionTypeIn(List<Byte> values) {
            addCriterion("VERSION_TYPE in", values, "versionType");
            return (Criteria) this;
        }

        public Criteria andVersionTypeNotIn(List<Byte> values) {
            addCriterion("VERSION_TYPE not in", values, "versionType");
            return (Criteria) this;
        }

        public Criteria andVersionTypeBetween(Byte value1, Byte value2) {
            addCriterion("VERSION_TYPE between", value1, value2, "versionType");
            return (Criteria) this;
        }

        public Criteria andVersionTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("VERSION_TYPE not between", value1, value2, "versionType");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("VERSION is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("VERSION =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("VERSION <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("VERSION >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("VERSION >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("VERSION <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("VERSION <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("VERSION like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("VERSION not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("VERSION in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("VERSION not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("VERSION between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("VERSION not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionIsNull() {
            addCriterion("FPGA_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionIsNotNull() {
            addCriterion("FPGA_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionEqualTo(String value) {
            addCriterion("FPGA_VERSION =", value, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionNotEqualTo(String value) {
            addCriterion("FPGA_VERSION <>", value, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionGreaterThan(String value) {
            addCriterion("FPGA_VERSION >", value, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionGreaterThanOrEqualTo(String value) {
            addCriterion("FPGA_VERSION >=", value, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionLessThan(String value) {
            addCriterion("FPGA_VERSION <", value, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionLessThanOrEqualTo(String value) {
            addCriterion("FPGA_VERSION <=", value, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionLike(String value) {
            addCriterion("FPGA_VERSION like", value, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionNotLike(String value) {
            addCriterion("FPGA_VERSION not like", value, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionIn(List<String> values) {
            addCriterion("FPGA_VERSION in", values, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionNotIn(List<String> values) {
            addCriterion("FPGA_VERSION not in", values, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionBetween(String value1, String value2) {
            addCriterion("FPGA_VERSION between", value1, value2, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andFpgaVersionNotBetween(String value1, String value2) {
            addCriterion("FPGA_VERSION not between", value1, value2, "fpgaVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionIsNull() {
            addCriterion("BBU_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andBbuVersionIsNotNull() {
            addCriterion("BBU_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andBbuVersionEqualTo(String value) {
            addCriterion("BBU_VERSION =", value, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionNotEqualTo(String value) {
            addCriterion("BBU_VERSION <>", value, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionGreaterThan(String value) {
            addCriterion("BBU_VERSION >", value, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionGreaterThanOrEqualTo(String value) {
            addCriterion("BBU_VERSION >=", value, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionLessThan(String value) {
            addCriterion("BBU_VERSION <", value, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionLessThanOrEqualTo(String value) {
            addCriterion("BBU_VERSION <=", value, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionLike(String value) {
            addCriterion("BBU_VERSION like", value, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionNotLike(String value) {
            addCriterion("BBU_VERSION not like", value, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionIn(List<String> values) {
            addCriterion("BBU_VERSION in", values, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionNotIn(List<String> values) {
            addCriterion("BBU_VERSION not in", values, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionBetween(String value1, String value2) {
            addCriterion("BBU_VERSION between", value1, value2, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andBbuVersionNotBetween(String value1, String value2) {
            addCriterion("BBU_VERSION not between", value1, value2, "bbuVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionIsNull() {
            addCriterion("SW_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andSwVersionIsNotNull() {
            addCriterion("SW_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andSwVersionEqualTo(String value) {
            addCriterion("SW_VERSION =", value, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionNotEqualTo(String value) {
            addCriterion("SW_VERSION <>", value, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionGreaterThan(String value) {
            addCriterion("SW_VERSION >", value, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionGreaterThanOrEqualTo(String value) {
            addCriterion("SW_VERSION >=", value, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionLessThan(String value) {
            addCriterion("SW_VERSION <", value, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionLessThanOrEqualTo(String value) {
            addCriterion("SW_VERSION <=", value, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionLike(String value) {
            addCriterion("SW_VERSION like", value, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionNotLike(String value) {
            addCriterion("SW_VERSION not like", value, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionIn(List<String> values) {
            addCriterion("SW_VERSION in", values, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionNotIn(List<String> values) {
            addCriterion("SW_VERSION not in", values, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionBetween(String value1, String value2) {
            addCriterion("SW_VERSION between", value1, value2, "swVersion");
            return (Criteria) this;
        }

        public Criteria andSwVersionNotBetween(String value1, String value2) {
            addCriterion("SW_VERSION not between", value1, value2, "swVersion");
            return (Criteria) this;
        }

        public Criteria andUploadUserIsNull() {
            addCriterion("UPLOAD_USER is null");
            return (Criteria) this;
        }

        public Criteria andUploadUserIsNotNull() {
            addCriterion("UPLOAD_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUploadUserEqualTo(String value) {
            addCriterion("UPLOAD_USER =", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserNotEqualTo(String value) {
            addCriterion("UPLOAD_USER <>", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserGreaterThan(String value) {
            addCriterion("UPLOAD_USER >", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserGreaterThanOrEqualTo(String value) {
            addCriterion("UPLOAD_USER >=", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserLessThan(String value) {
            addCriterion("UPLOAD_USER <", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserLessThanOrEqualTo(String value) {
            addCriterion("UPLOAD_USER <=", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserLike(String value) {
            addCriterion("UPLOAD_USER like", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserNotLike(String value) {
            addCriterion("UPLOAD_USER not like", value, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserIn(List<String> values) {
            addCriterion("UPLOAD_USER in", values, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserNotIn(List<String> values) {
            addCriterion("UPLOAD_USER not in", values, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserBetween(String value1, String value2) {
            addCriterion("UPLOAD_USER between", value1, value2, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadUserNotBetween(String value1, String value2) {
            addCriterion("UPLOAD_USER not between", value1, value2, "uploadUser");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNull() {
            addCriterion("UPLOAD_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNotNull() {
            addCriterion("UPLOAD_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeEqualTo(String value) {
            addCriterion("UPLOAD_TIME =", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotEqualTo(String value) {
            addCriterion("UPLOAD_TIME <>", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThan(String value) {
            addCriterion("UPLOAD_TIME >", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UPLOAD_TIME >=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThan(String value) {
            addCriterion("UPLOAD_TIME <", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThanOrEqualTo(String value) {
            addCriterion("UPLOAD_TIME <=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLike(String value) {
            addCriterion("UPLOAD_TIME like", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotLike(String value) {
            addCriterion("UPLOAD_TIME not like", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIn(List<String> values) {
            addCriterion("UPLOAD_TIME in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotIn(List<String> values) {
            addCriterion("UPLOAD_TIME not in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeBetween(String value1, String value2) {
            addCriterion("UPLOAD_TIME between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotBetween(String value1, String value2) {
            addCriterion("UPLOAD_TIME not between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andFileUrlIsNull() {
            addCriterion("FILE_URL is null");
            return (Criteria) this;
        }

        public Criteria andFileUrlIsNotNull() {
            addCriterion("FILE_URL is not null");
            return (Criteria) this;
        }

        public Criteria andFileUrlEqualTo(String value) {
            addCriterion("FILE_URL =", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlNotEqualTo(String value) {
            addCriterion("FILE_URL <>", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlGreaterThan(String value) {
            addCriterion("FILE_URL >", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_URL >=", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlLessThan(String value) {
            addCriterion("FILE_URL <", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlLessThanOrEqualTo(String value) {
            addCriterion("FILE_URL <=", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlLike(String value) {
            addCriterion("FILE_URL like", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlNotLike(String value) {
            addCriterion("FILE_URL not like", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlIn(List<String> values) {
            addCriterion("FILE_URL in", values, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlNotIn(List<String> values) {
            addCriterion("FILE_URL not in", values, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlBetween(String value1, String value2) {
            addCriterion("FILE_URL between", value1, value2, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlNotBetween(String value1, String value2) {
            addCriterion("FILE_URL not between", value1, value2, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
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

        public Criteria andChecksumIsNull() {
            addCriterion("CHECKSUM is null");
            return (Criteria) this;
        }

        public Criteria andChecksumIsNotNull() {
            addCriterion("CHECKSUM is not null");
            return (Criteria) this;
        }

        public Criteria andChecksumEqualTo(String value) {
            addCriterion("CHECKSUM =", value, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumNotEqualTo(String value) {
            addCriterion("CHECKSUM <>", value, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumGreaterThan(String value) {
            addCriterion("CHECKSUM >", value, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumGreaterThanOrEqualTo(String value) {
            addCriterion("CHECKSUM >=", value, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumLessThan(String value) {
            addCriterion("CHECKSUM <", value, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumLessThanOrEqualTo(String value) {
            addCriterion("CHECKSUM <=", value, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumLike(String value) {
            addCriterion("CHECKSUM like", value, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumNotLike(String value) {
            addCriterion("CHECKSUM not like", value, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumIn(List<String> values) {
            addCriterion("CHECKSUM in", values, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumNotIn(List<String> values) {
            addCriterion("CHECKSUM not in", values, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumBetween(String value1, String value2) {
            addCriterion("CHECKSUM between", value1, value2, "checksum");
            return (Criteria) this;
        }

        public Criteria andChecksumNotBetween(String value1, String value2) {
            addCriterion("CHECKSUM not between", value1, value2, "checksum");
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