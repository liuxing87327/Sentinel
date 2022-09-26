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
package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.boot.nacos.config.properties.NacosConfigProperties;
import com.alibaba.boot.nacos.config.util.NacosPropertiesBuilder;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
@Configuration
public class NacosConfig {

    @Resource
    NacosConfigProperties nacosConfigProperties;
    @Resource
    ConfigurableEnvironment environment;

    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return source -> JSON.toJSONString(source, true);
    }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    /**
     * 网关API
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Converter<List<ApiDefinitionEntity>, String> apiDefinitionEntityEncoder() {
        return source -> JSON.toJSONString(source, true);
    }

    @Bean
    public Converter<String, List<ApiDefinitionEntity>> apiDefinitionEntityDecoder() {
        return s -> JSON.parseArray(s, ApiDefinitionEntity.class);
    }

    /**
     * 网关flowRule
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Converter<List<GatewayFlowRuleEntity>, String> gatewayFlowRuleEntityEncoder() {
        return source -> JSON.toJSONString(source, true);
    }

    @Bean
    public Converter<String, List<GatewayFlowRuleEntity>> gatewayFlowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, GatewayFlowRuleEntity.class);
    }

    @Bean
    public ConfigService nacosConfigService() throws Exception {
        Properties properties = NacosPropertiesBuilder.buildNacosProperties(environment,
                nacosConfigProperties.getServerAddr(),
                nacosConfigProperties.getNamespace(), nacosConfigProperties.getEndpoint(),
                nacosConfigProperties.getSecretKey(),
                nacosConfigProperties.getAccessKey(),
                nacosConfigProperties.getRamRoleName(),
                nacosConfigProperties.getConfigLongPollTimeout(),
                nacosConfigProperties.getConfigRetryTime(),
                nacosConfigProperties.getMaxRetry(),
                nacosConfigProperties.isEnableRemoteSyncConfig(),
                nacosConfigProperties.getUsername(), nacosConfigProperties.getPassword());
        return ConfigFactory.createConfigService(properties);
    }

}
