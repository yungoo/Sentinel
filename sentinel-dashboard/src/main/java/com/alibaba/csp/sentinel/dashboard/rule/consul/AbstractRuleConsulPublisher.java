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

import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.kv.model.PutParams;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class AbstractRuleConsulPublisher<T> implements DynamicRulePublisher<List<T>> {
    private ConsulConfig consulConfig;
    private ConsulClient client;
    private Converter<List<T>, String> converter;

    public AbstractRuleConsulPublisher(ConsulConfig config, ConsulClient client, Converter<List<T>, String> converter) {
        this.consulConfig = config;
        this.client = client;
        this.converter = converter;
    }

    @Override
    public void publish(String app, List<T> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");

        Class<T> tClass  = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        String path = ConsulConfigUtil.getRulesKey(app, tClass);
        byte[] data = CollectionUtils.isEmpty(rules) ? "[]".getBytes() : converter.convert(rules).getBytes();

        if (StringUtil.isBlank(consulConfig.getToken())) {
            client.setKVBinaryValue(path, data);
        } else {
            client.setKVBinaryValue(path, data, consulConfig.getToken(), new PutParams());
        }
    }
}