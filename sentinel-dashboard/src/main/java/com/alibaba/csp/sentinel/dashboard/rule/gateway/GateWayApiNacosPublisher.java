package com.alibaba.csp.sentinel.dashboard.rule.gateway;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GateWayApiNacosPublisher
 *
 * @author liuxing
 * @summary GateWayApiNacosPublisher
 * @Copyright (c) 2022, 湖南数字侠软件有限公司版权所有
 * @since 2022年09月26日 20:48:23
 */
@Component("gateWayApiNacosPublisher")
public class GateWayApiNacosPublisher implements DynamicRulePublisher<List<ApiDefinitionEntity>> {

    @Autowired
    private ConfigService configService;
    @Autowired
    private Converter<List<ApiDefinitionEntity>, String> converter;

    @Override
    public void publish(String app, List<ApiDefinitionEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }
        configService.publishConfig(app + NacosConfigUtil.GETWAY_API_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, converter.convert(rules), ConfigType.JSON.getType());
    }
}


