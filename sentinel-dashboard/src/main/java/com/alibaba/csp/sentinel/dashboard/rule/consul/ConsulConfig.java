/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.csp.sentinel.dashboard.rule.consul;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.fastjson.JSON;
import com.ecwid.consul.v1.ConsulClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties("consul")
@Configuration
public class ConsulConfig {

    private String    host;
    private int       port;
    private String    token;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Bean("flowRuleConsulProvider")
    public DynamicRuleProvider<List<FlowRuleEntity>> flowRuleProvider(ConsulConfig config, ConsulClient client) {
        return new AbstractRuleConsulProvider<FlowRuleEntity>(config, client, s -> JSON.parseArray(s, FlowRuleEntity.class)) {};
    }

    @Bean("flowRuleConsulPublisher")
    public DynamicRulePublisher<List<FlowRuleEntity>> flowRulePublisher(ConsulConfig config,
                                                                        ConsulClient client) {
        return new AbstractRuleConsulPublisher<FlowRuleEntity>(config, client, JSON::toJSONString) {};
    }

    @Bean("degradeRuleConsulProvider")
    public DynamicRuleProvider<List<DegradeRuleEntity>> degradeRuleProvider(ConsulConfig config, ConsulClient client) {
        return new AbstractRuleConsulProvider<DegradeRuleEntity>(config, client, s -> JSON.parseArray(s, DegradeRuleEntity.class)) {};
    }

    @Bean("degradeRuleConsulPublisher")
    public DynamicRulePublisher<List<DegradeRuleEntity>> degradeRulePublisher(ConsulConfig config,
                                                                        ConsulClient client) {
        return new AbstractRuleConsulPublisher<DegradeRuleEntity>(config, client, JSON::toJSONString) {};
    }

    @Bean("systemRuleConsulProvider")
    public DynamicRuleProvider<List<SystemRuleEntity>> systemRuleProvider(ConsulConfig config, ConsulClient client) {
        return new AbstractRuleConsulProvider<SystemRuleEntity>(config, client, s -> JSON.parseArray(s, SystemRuleEntity.class)) {};
    }

    @Bean("systemRuleConsulPublisher")
    public DynamicRulePublisher<List<SystemRuleEntity>> systemRulePublisher(ConsulConfig config,
                                                                              ConsulClient client) {
        return new AbstractRuleConsulPublisher<SystemRuleEntity>(config, client, JSON::toJSONString) {};
    }

    @Bean("authorityRuleConsulProvider")
    public DynamicRuleProvider<List<AuthorityRuleEntity>> authorityRuleProvider(ConsulConfig config, ConsulClient client) {
        return new AbstractRuleConsulProvider<AuthorityRuleEntity>(config, client, s -> JSON.parseArray(s, AuthorityRuleEntity.class)) {};
    }

    @Bean("authorityRuleConsulPublisher")
    public DynamicRulePublisher<List<AuthorityRuleEntity>> authorityRulePublisher(ConsulConfig config,
                                                                            ConsulClient client) {
        return new AbstractRuleConsulPublisher<AuthorityRuleEntity>(config, client, JSON::toJSONString) {};
    }

    @Bean("paramFlowRuleConsulProvider")
    public DynamicRuleProvider<List<ParamFlowRuleEntity>> paramFlowRuleProvider(ConsulConfig config, ConsulClient client) {
        return new AbstractRuleConsulProvider<ParamFlowRuleEntity>(config, client, s -> JSON.parseArray(s, ParamFlowRuleEntity.class)) {};
    }

    @Bean("paramFlowRuleConsulPublisher")
    public DynamicRulePublisher<List<ParamFlowRuleEntity>> paramFlowRulePublisher(ConsulConfig config,
                                                                                  ConsulClient client) {
        return new AbstractRuleConsulPublisher<ParamFlowRuleEntity>(config, client, JSON::toJSONString) {};
    }

    @Bean("gatewayFlowRuleConsulProvider")
    public DynamicRuleProvider<List<GatewayFlowRuleEntity>> gatewayFlowRuleProvider(ConsulConfig config, ConsulClient client) {
        return new AbstractRuleConsulProvider<GatewayFlowRuleEntity>(config, client, s -> JSON.parseArray(s, GatewayFlowRuleEntity.class)) {};
    }

    @Bean("gatewayFlowRuleConsulPublisher")
    public DynamicRulePublisher<List<GatewayFlowRuleEntity>> gatewayFlowRulePublisher(ConsulConfig config,
                                                                                  ConsulClient client) {
        return new AbstractRuleConsulPublisher<GatewayFlowRuleEntity>(config, client, JSON::toJSONString) {};
    }

    @Bean("apiDefinitionConsulProvider")
    public DynamicRuleProvider<List<ApiDefinitionEntity>> apiDefinitionProvider(ConsulConfig config, ConsulClient client) {
        return new AbstractRuleConsulProvider<ApiDefinitionEntity>(config, client, s -> JSON.parseArray(s, ApiDefinitionEntity.class)) {};
    }

    @Bean("apiDefinitionConsulPublisher")
    public DynamicRulePublisher<List<ApiDefinitionEntity>> apiDefinitionRulePublisher(ConsulConfig config,
                                                                                      ConsulClient client) {
        return new AbstractRuleConsulPublisher<ApiDefinitionEntity>(config, client, JSON::toJSONString) {};
    }

    @Bean
    public ConsulClient consulClient() throws Exception {
        return new ConsulClient(host, port);
    }
}
