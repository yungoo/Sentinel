/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.datasource.entity.rule;

import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowClusterConfig;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

/**
 * @author Eric Zhao
 * @since 0.2.1
 */
public class ParamFlowRuleEntity implements RuleEntity {

    protected Long id;

    protected String app;
    protected String ip;
    protected Integer port;

    /**
     * Resource name.
     */
    private String resource;

    /**
     * <p>
     * Application name that will be limited by origin.
     * The default limitApp is {@code default}, which means allowing all origin apps.
     * </p>
     * <p>
     * For authority rules, multiple origin name can be separated with comma (',').
     * </p>
     */
    private String limitApp;

    /**
     * The threshold type of flow control (0: thread count, 1: QPS).
     */
    private int grade = RuleConstant.FLOW_GRADE_QPS;

    /**
     * Parameter index.
     */
    private Integer paramIdx;

    /**
     * The threshold count.
     */
    private double count;

    /**
     * Traffic shaping behavior (since 1.6.0).
     */
    private int controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;

    private int maxQueueingTimeMs = 0;
    private int burstCount = 0;
    private long durationInSec = 1;

    /**
     * Original exclusion items of parameters.
     */
    private List<ParamFlowItem> paramFlowItemList = new ArrayList<ParamFlowItem>();

    /**
     * Indicating whether the rule is for cluster mode.
     */
    private boolean clusterMode = false;
    /**
     * Cluster mode specific config for parameter flow rule.
     */
    private ParamFlowClusterConfig clusterConfig;

    private Date gmtCreate;
    private Date gmtModified;

    public ParamFlowRuleEntity() {
    }

    public ParamFlowRuleEntity(ParamFlowRule rule) {
        AssertUtil.notNull(rule, "Authority rule should not be null");

        this.resource = rule.getResource();
        this.limitApp = rule.getLimitApp();
        this.grade = rule.getGrade();
        this.paramIdx = rule.getParamIdx();
        this.count = rule.getCount();
        this.controlBehavior = rule.getControlBehavior();
        this.maxQueueingTimeMs = rule.getMaxQueueingTimeMs();
        this.burstCount = rule.getBurstCount();
        this.durationInSec = rule.getDurationInSec();
        this.paramFlowItemList = rule.getParamFlowItemList();
        this.clusterMode = rule.isClusterMode();
        this.clusterConfig = rule.getClusterConfig();
    }

    public static ParamFlowRuleEntity fromAuthorityRule(String app, String ip, Integer port, ParamFlowRule rule) {
        ParamFlowRuleEntity entity = new ParamFlowRuleEntity(rule);
        entity.setApp(app);
        entity.setIp(ip);
        entity.setPort(port);
        return entity;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getApp() {
        return app;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public String getLimitApp() {
        return limitApp;
    }

    public String getResource() {
        return resource;
    }

    public int getGrade() {
        return grade;
    }

    public Integer getParamIdx() {
        return paramIdx;
    }

    public double getCount() {
        return count;
    }

    public List<ParamFlowItem> getParamFlowItemList() {
        return paramFlowItemList;
    }

    public int getControlBehavior() {
        return controlBehavior;
    }

    public int getMaxQueueingTimeMs() {
        return maxQueueingTimeMs;
    }

    public int getBurstCount() {
        return burstCount;
    }

    public long getDurationInSec() {
        return durationInSec;
    }

    public boolean isClusterMode() {
        return clusterMode;
    }

    public ParamFlowClusterConfig getClusterConfig() {
        return clusterConfig;
    }

    public ParamFlowRuleEntity setResource(String resource) {
        this.resource = resource;
        return this;
    }

    public ParamFlowRuleEntity setLimitApp(String limitApp) {
        this.limitApp = limitApp;
        return this;
    }

    public ParamFlowRuleEntity setApp(String app) {
        this.app = app;
        return this;
    }
    public ParamFlowRuleEntity setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public ParamFlowRuleEntity setPort(Integer port) {
        this.port = port;
        return this;
    }

    public ParamFlowRuleEntity setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public ParamFlowRuleEntity setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    public ParamFlowRuleEntity setControlBehavior(int controlBehavior) {
        this.controlBehavior = controlBehavior;
        return this;
    }

    public ParamFlowRuleEntity setMaxQueueingTimeMs(int maxQueueingTimeMs) {
        this.maxQueueingTimeMs = maxQueueingTimeMs;
        return this;
    }

    public ParamFlowRuleEntity setBurstCount(int burstCount) {
        this.burstCount = burstCount;
        return this;
    }

    public ParamFlowRuleEntity setDurationInSec(long durationInSec) {
        this.durationInSec = durationInSec;
        return this;
    }

    public ParamFlowRuleEntity setGrade(int grade) {
        this.grade = grade;
        return this;
    }

    public ParamFlowRuleEntity setParamIdx(Integer paramIdx) {
        this.paramIdx = paramIdx;
        return this;
    }

    public ParamFlowRuleEntity setCount(double count) {
        this.count = count;
        return this;
    }

    public ParamFlowRuleEntity setParamFlowItemList(List<ParamFlowItem> paramFlowItemList) {
        this.paramFlowItemList = paramFlowItemList;
        return this;
    }

    public ParamFlowRuleEntity setClusterMode(boolean clusterMode) {
        this.clusterMode = clusterMode;
        return this;
    }

    public ParamFlowRuleEntity setClusterConfig(ParamFlowClusterConfig clusterConfig) {
        this.clusterConfig = clusterConfig;
        return this;
    }

    @Override
    public Rule toRule() {
        ParamFlowRule rule = new ParamFlowRule(this.resource);
        rule.setParamIdx(paramIdx)
                .setCount(count)
                .setControlBehavior(controlBehavior)
                .setBurstCount(burstCount)
                .setGrade(grade)
                .setDurationInSec(durationInSec)
                .setMaxQueueingTimeMs(maxQueueingTimeMs)
                .setParamFlowItemList(paramFlowItemList)
                .setClusterConfig(clusterConfig)
                .setClusterMode(clusterMode)
                .setLimitApp(limitApp);
        return rule;
    }
}
