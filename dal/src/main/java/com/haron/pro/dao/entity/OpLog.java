package com.haron.pro.dao.entity;

import java.util.Date;

public class OpLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_log.id
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_log.open_id
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_log.ip_address
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    private String ipAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_log.api
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    private String api;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_log.param
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    private String param;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_log.result
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    private String result;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_log.create_time
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_log.id
     *
     * @return the value of op_log.id
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_log.id
     *
     * @param id the value for op_log.id
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_log.open_id
     *
     * @return the value of op_log.open_id
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_log.open_id
     *
     * @param openId the value for op_log.open_id
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_log.ip_address
     *
     * @return the value of op_log.ip_address
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_log.ip_address
     *
     * @param ipAddress the value for op_log.ip_address
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_log.api
     *
     * @return the value of op_log.api
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public String getApi() {
        return api;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_log.api
     *
     * @param api the value for op_log.api
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public void setApi(String api) {
        this.api = api;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_log.param
     *
     * @return the value of op_log.param
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public String getParam() {
        return param;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_log.param
     *
     * @param param the value for op_log.param
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public void setParam(String param) {
        this.param = param;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_log.result
     *
     * @return the value of op_log.result
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public String getResult() {
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_log.result
     *
     * @param result the value for op_log.result
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_log.create_time
     *
     * @return the value of op_log.create_time
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_log.create_time
     *
     * @param createTime the value for op_log.create_time
     *
     * @mbggenerated Thu Aug 09 15:10:42 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}