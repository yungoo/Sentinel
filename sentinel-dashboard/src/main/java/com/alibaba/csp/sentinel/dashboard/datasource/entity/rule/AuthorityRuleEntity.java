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
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author Eric Zhao
 * @since 0.2.1
 */
public class AuthorityRuleEntity implements RuleEntity {

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

    private int strategy = RuleConstant.AUTHORITY_WHITE;

    private Date gmtCreate;
    private Date gmtModified;

    public AuthorityRuleEntity() {
    }

    public AuthorityRuleEntity(AuthorityRule authorityRule) {
        AssertUtil.notNull(authorityRule, "Authority rule should not be null");
        this.resource = authorityRule.getResource();
        this.limitApp = authorityRule.getLimitApp();
        this.strategy = authorityRule.getStrategy();
    }

    public static AuthorityRuleEntity fromAuthorityRule(String app, String ip, Integer port, AuthorityRule rule) {
        AuthorityRuleEntity entity = new AuthorityRuleEntity(rule);
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

    public AuthorityRuleEntity setApp(String app) {
        this.app = app;
        return this;
    }

    @Override
    public String getIp() {
        return ip;
    }

    public AuthorityRuleEntity setIp(String ip) {
        this.ip = ip;
        return this;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    public AuthorityRuleEntity setPort(Integer port) {
        this.port = port;
        return this;
    }

    @Override
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public AuthorityRuleEntity setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public AuthorityRuleEntity setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    public String getLimitApp() {
        return limitApp;
    }

    public String getResource() {
        return resource;
    }

    public int getStrategy() {
        return strategy;
    }

    public AuthorityRuleEntity setResource(String resource) {
        this.resource = resource;
        return this;
    }

    public AuthorityRuleEntity setLimitApp(String limitApp) {
        this.limitApp = limitApp;
        return this;
    }

    public AuthorityRuleEntity setStrategy(int strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public Rule toRule() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(resource);
        rule.setLimitApp(limitApp);
        rule.setStrategy(strategy);
        return rule;
    }
}
