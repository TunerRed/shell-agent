package org.shellagent.entity;

import java.util.ArrayList;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserExample() {
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

        public Criteria andUsernameIsNull() {
            addCriterion("`username` is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("`username` is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("`username` =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("`username` <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("`username` >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("`username` >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("`username` <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("`username` <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("`username` like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("`username` not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("`username` in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("`username` not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("`username` between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("`username` not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("`password` is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("`password` is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("`password` =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("`password` <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("`password` >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("`password` >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("`password` <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("`password` <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("`password` like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("`password` not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("`password` in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("`password` not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("`password` between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("`password` not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqIsNull() {
            addCriterion("`grant_server_seq` is null");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqIsNotNull() {
            addCriterion("`grant_server_seq` is not null");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqEqualTo(String value) {
            addCriterion("`grant_server_seq` =", value, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqNotEqualTo(String value) {
            addCriterion("`grant_server_seq` <>", value, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqGreaterThan(String value) {
            addCriterion("`grant_server_seq` >", value, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqGreaterThanOrEqualTo(String value) {
            addCriterion("`grant_server_seq` >=", value, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqLessThan(String value) {
            addCriterion("`grant_server_seq` <", value, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqLessThanOrEqualTo(String value) {
            addCriterion("`grant_server_seq` <=", value, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqLike(String value) {
            addCriterion("`grant_server_seq` like", value, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqNotLike(String value) {
            addCriterion("`grant_server_seq` not like", value, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqIn(List<String> values) {
            addCriterion("`grant_server_seq` in", values, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqNotIn(List<String> values) {
            addCriterion("`grant_server_seq` not in", values, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqBetween(String value1, String value2) {
            addCriterion("`grant_server_seq` between", value1, value2, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andGrantServerSeqNotBetween(String value1, String value2) {
            addCriterion("`grant_server_seq` not between", value1, value2, "grantServerSeq");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("`phone` is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("`phone` is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("`phone` =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("`phone` <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("`phone` >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("`phone` >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("`phone` <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("`phone` <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("`phone` like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("`phone` not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("`phone` in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("`phone` not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("`phone` between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("`phone` not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andRootIsNull() {
            addCriterion("`root` is null");
            return (Criteria) this;
        }

        public Criteria andRootIsNotNull() {
            addCriterion("`root` is not null");
            return (Criteria) this;
        }

        public Criteria andRootEqualTo(String value) {
            addCriterion("`root` =", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootNotEqualTo(String value) {
            addCriterion("`root` <>", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootGreaterThan(String value) {
            addCriterion("`root` >", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootGreaterThanOrEqualTo(String value) {
            addCriterion("`root` >=", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootLessThan(String value) {
            addCriterion("`root` <", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootLessThanOrEqualTo(String value) {
            addCriterion("`root` <=", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootLike(String value) {
            addCriterion("`root` like", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootNotLike(String value) {
            addCriterion("`root` not like", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootIn(List<String> values) {
            addCriterion("`root` in", values, "root");
            return (Criteria) this;
        }

        public Criteria andRootNotIn(List<String> values) {
            addCriterion("`root` not in", values, "root");
            return (Criteria) this;
        }

        public Criteria andRootBetween(String value1, String value2) {
            addCriterion("`root` between", value1, value2, "root");
            return (Criteria) this;
        }

        public Criteria andRootNotBetween(String value1, String value2) {
            addCriterion("`root` not between", value1, value2, "root");
            return (Criteria) this;
        }

        public Criteria andLabel1IsNull() {
            addCriterion("`label1` is null");
            return (Criteria) this;
        }

        public Criteria andLabel1IsNotNull() {
            addCriterion("`label1` is not null");
            return (Criteria) this;
        }

        public Criteria andLabel1EqualTo(String value) {
            addCriterion("`label1` =", value, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1NotEqualTo(String value) {
            addCriterion("`label1` <>", value, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1GreaterThan(String value) {
            addCriterion("`label1` >", value, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1GreaterThanOrEqualTo(String value) {
            addCriterion("`label1` >=", value, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1LessThan(String value) {
            addCriterion("`label1` <", value, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1LessThanOrEqualTo(String value) {
            addCriterion("`label1` <=", value, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1Like(String value) {
            addCriterion("`label1` like", value, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1NotLike(String value) {
            addCriterion("`label1` not like", value, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1In(List<String> values) {
            addCriterion("`label1` in", values, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1NotIn(List<String> values) {
            addCriterion("`label1` not in", values, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1Between(String value1, String value2) {
            addCriterion("`label1` between", value1, value2, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel1NotBetween(String value1, String value2) {
            addCriterion("`label1` not between", value1, value2, "label1");
            return (Criteria) this;
        }

        public Criteria andLabel2IsNull() {
            addCriterion("`label2` is null");
            return (Criteria) this;
        }

        public Criteria andLabel2IsNotNull() {
            addCriterion("`label2` is not null");
            return (Criteria) this;
        }

        public Criteria andLabel2EqualTo(String value) {
            addCriterion("`label2` =", value, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2NotEqualTo(String value) {
            addCriterion("`label2` <>", value, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2GreaterThan(String value) {
            addCriterion("`label2` >", value, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2GreaterThanOrEqualTo(String value) {
            addCriterion("`label2` >=", value, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2LessThan(String value) {
            addCriterion("`label2` <", value, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2LessThanOrEqualTo(String value) {
            addCriterion("`label2` <=", value, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2Like(String value) {
            addCriterion("`label2` like", value, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2NotLike(String value) {
            addCriterion("`label2` not like", value, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2In(List<String> values) {
            addCriterion("`label2` in", values, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2NotIn(List<String> values) {
            addCriterion("`label2` not in", values, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2Between(String value1, String value2) {
            addCriterion("`label2` between", value1, value2, "label2");
            return (Criteria) this;
        }

        public Criteria andLabel2NotBetween(String value1, String value2) {
            addCriterion("`label2` not between", value1, value2, "label2");
            return (Criteria) this;
        }
    }

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