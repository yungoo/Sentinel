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

import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public abstract class AbstractRuleConsulProvider<T> implements DynamicRuleProvider<List<T>> {

    private ConsulConfig consulConfig;

    private ConsulClient consulClient;

    private Converter<String, List<T>> converter;

    public AbstractRuleConsulProvider(ConsulConfig consulConfig, ConsulClient consulClient, Converter<String, List<T>> converter) {
        this.consulConfig = consulConfig;
        this.consulClient = consulClient;
        this.converter = converter;
    }

    @Override
    public List<T> getRules(String appName) throws Exception {
        Class<T> tClass  = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        String key = ConsulConfigUtil.getRulesKey(appName, tClass);
        Response<GetValue> response = getValueImmediately(key);
        if (response != null) {
            GetValue value = response.getValue();
            if (value != null) {
                String rules = value.getDecodedValue();
                return converter.convert(rules);
            }
        }

        return new ArrayList<>();
    }

    /**
     * Get data from Consul immediately.
     *
     * @param key data key in Consul
     * @return the value associated to the key, or null if error occurs
     */
    private Response<GetValue> getValueImmediately(String key) {
        return getValue(key, -1, -1);
    }

    /**
     * Get data from Consul (blocking).
     *
     * @param key      data key in Consul
     * @param index    the index of data in Consul.
     * @param waitTime time(second) for waiting get updated value.
     * @return the value associated to the key, or null if error occurs
     */
    private Response<GetValue> getValue(String key, long index, long waitTime) {
        try {
            if (StringUtil.isBlank(consulConfig.getToken())) {
                return consulClient.getKVValue(key, consulConfig.getToken());
            } else {
                return consulClient.getKVValue(key, consulConfig.getToken(), new QueryParams(waitTime, index));
            }
        } catch (Throwable t) {
            RecordLog.warn("[ConsulDataSource] Failed to get value for key: " + key, t);
        }
        return null;
    }
}
