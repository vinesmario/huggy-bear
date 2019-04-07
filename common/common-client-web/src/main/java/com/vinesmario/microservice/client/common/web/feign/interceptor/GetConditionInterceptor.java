package com.vinesmario.microservice.client.common.web.feign.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Slf4j
@Configuration
public class GetConditionInterceptor implements RequestInterceptor {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void apply(RequestTemplate template) {
		// 因为feign 不支持 GET 方法传 POJO,
		// 将body转key=value
		if (template.method().equalsIgnoreCase("GET") && template.body() != null) {
			try {
				JsonNode jsonNode = objectMapper.readTree(template.body());
				template.body(null);

				Map<String, Collection<String>> queries = new HashMap<>();
				buildCondition(jsonNode, "", queries);
				template.queries(queries);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void buildCondition(JsonNode jsonNode, String path, Map<String, Collection<String>> queries) {
		if (!jsonNode.isContainerNode()) {   // 叶子节点
			if (jsonNode.isNull()) {
				return;
			}
			Collection<String> values = queries.get(path);
			if (null == values) {
				values = new ArrayList<>();
				queries.put(path, values);
			}
			values.add(jsonNode.asText());
//			log.info("set param {}={}", path, jsonNode.asText());
			return;
		}
		if (jsonNode.isArray()) {   // 数组节点
			Iterator<JsonNode> it = jsonNode.elements();
			while (it.hasNext()) {
				buildCondition(it.next(), path, queries);
			}
		} else {
			Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
			while (it.hasNext()) {
				Map.Entry<String, JsonNode> entry = it.next();
				if (StringUtils.hasText(path)) {
					buildCondition(entry.getValue(), path + "." + entry.getKey(), queries);
				} else {  // 根节点
					buildCondition(entry.getValue(), entry.getKey(), queries);
				}
			}
		}
	}
}
