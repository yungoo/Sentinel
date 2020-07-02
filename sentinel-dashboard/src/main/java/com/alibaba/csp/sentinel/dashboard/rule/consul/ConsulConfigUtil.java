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
package com.alibaba.csp.sentinel.dashboard.rule.consul;


import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import org.apache.commons.lang.StringUtils;

public class ConsulConfigUtil {
    public static final String RULE_ROOT_PATH = "sentinel";
    public static final String FLOW_RULES = "flow-rules";
    public static final String DEGRADE_RULES = "degrade-rules";
    public static final String SYSTEM_RULES = "system-rules";
    public static final String AUTHORITY_RULES = "authority-rules";
    public static final String PARAM_FLOW_RULES = "param-flow-rules";
    public static final String API_DEFINITIONS = "apis";
    public static final String GW_FLOW_RULES = "gw-flow-rules";

    public static final int RETRY_TIMES = 3;
    public static final int SLEEP_TIME = 1000;

    public static <T> String getRulesKey(String appName, Class<T> clazz) {
        StringBuilder stringBuilder = new StringBuilder(RULE_ROOT_PATH);

        if (StringUtils.isBlank(appName)) {
            return stringBuilder.toString();
        }

        if (appName.startsWith("/")) {
            stringBuilder.append(appName);
        } else {
            stringBuilder.append("/")
                    .append(appName);
        }

        String ruleType = FLOW_RULES;
        if (DegradeRuleEntity.class.equals(clazz)) {
            ruleType = DEGRADE_RULES;
        } else if (SystemRuleEntity.class.equals(clazz)) {
            ruleType = SYSTEM_RULES;
        } else if (AuthorityRuleEntity.class.equals(clazz)) {
            ruleType = AUTHORITY_RULES;
        } else if (ParamFlowRuleEntity.class.equals(clazz)) {
            ruleType = PARAM_FLOW_RULES;
        } else if (GatewayFlowRuleEntity.class.equals(clazz)) {
            ruleType = GW_FLOW_RULES;
        } else if (ApiDefinitionEntity.class.equals(clazz)) {
            ruleType = API_DEFINITIONS;
        }

        if (appName.endsWith("/")) {
            stringBuilder.append(ruleType);
        } else {
            stringBuilder.append("/")
                    .append(ruleType);
        }

        return stringBuilder.toString();
    }

}