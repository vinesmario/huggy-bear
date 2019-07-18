package com.vinesmario.microservice.client.common.web.feign.interceptor;//package com.vinesmario.microservice.client.common.web.feign.interceptor;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//
//@Slf4j
//@Configuration
//public class HeadersInterceptor implements RequestInterceptor {
//
//	@Override
//	public void apply(RequestTemplate requestTemplate) {
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
//				.getRequestAttributes();
//		if (null != attributes) {
//			HttpServletRequest request = attributes.getRequest();
//			Enumeration<String> headerNames = request.getHeaderNames();
//			if (headerNames != null) {
//				while (headerNames.hasMoreElements()) {
//					String name = headerNames.nextElement();
//					String values = request.getHeader(name);
////					log.debug("set attribute {}={}", name, values);
//					requestTemplate.header(name, values);
//				}
//			}
//		}
//	}
//
//}