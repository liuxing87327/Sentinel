package com.alibaba.csp.sentinel.dashboard.rule.gateway;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * GateWayApiNacosProvider
 *
 * @author liuxing
 * @summary GateWayApiNacosProvider
 * @Copyright (c) 2022, 湖南数字侠软件有限公司版权所有
 * @since 2022年09月26日 20:47:37
 */
@Component("gateWayApiNacosProvider")
public class GateWayApiNacosProvider implements DynamicRuleProvider<List<ApiDefinitionEntity>> {

    @Autowired
    private ConfigService configService;
    @Autowired
    private Converter<String, List<ApiDefinitionEntity>> converter;

    @Override
    public List<ApiDefinitionEntity> getRules(String appName) throws Exception {
        String rules = configService.getConfig(appName + NacosConfigUtil.GETWAY_API_DATA_ID_POSTFIX, NacosConfigUtil.GROUP_ID, 3000);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}

