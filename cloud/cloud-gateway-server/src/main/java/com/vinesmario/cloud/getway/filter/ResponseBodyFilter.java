package com.vinesmario.cloud.getway.filter;


import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by leon on 2017/4/28.
 */
public class ResponseBodyFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(ResponseBodyFilter.class);

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return -10;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * token认证的错误返回列表
	 * token 错误
	 * {"error": "invalid_token","error_description": "Invalid access token: 24bab99d-7766-41e3-b340-afd40a6a594f-1"}
	 * token 过期
	 * {"error":"invalid_token","error_description":"access token expired: 24bab99d-7766-41e3-b340-afd40a6a594f"}
	 * 没有token但是接口需要
	 * {"error":"unauthorized","error_description":"Full authentication is required to access this resource"}
	 * 用户名和密码错误
	 * {"error":"invalid_grant","error_description":"坏的凭证"}
	 *
	 * @return
	 */
    @Override
    public Object run() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//
//        String accept = ctx.getRequest().getHeader("Accept");  //获取客户端所需的数据格式
//        if (accept != null && !accept.equals("application/json")) return null;  //如果不是json 直接返回
//
//        int status = ctx.getResponseStatusCode();
//        if (HttpStatus.OK.value() != status
//                && HttpStatus.BAD_REQUEST.value() != status
//                && HttpStatus.UNAUTHORIZED.value() != status
//                && HttpStatus.INTERNAL_SERVER_ERROR.value() != status) return null;
//
//        ResponseEntity re = new ResponseEntity();
//        re.setStatus(status);
//
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            Object e = ctx.get("error.exception");
//            if (e != null && e instanceof ZuulException) {
//                ZuulException zuulException = (ZuulException) e;
//                log.error("Zuul failure detected: " + zuulException.getMessage(), zuulException);
//
//                // Remove error code to prevent further error handling in follow up filters
//                ctx.remove("error.status_code");
//
//                re.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                re.setCode(ApiResultEnum.API_GATEWAY_ERROR.getCode());
//                re.setMessage(ApiResultEnum.API_GATEWAY_ERROR.getMessage());
//                re.setException(((ZuulException) e).getMessage());
//                String rejson = objectMapper.writeValueAsString(re);
//                ctx.setResponseBody(rejson);
//                ctx.setResponseStatusCode(HttpStatus.OK.value());//Can set any error code as excepted
//                ctx.getResponse().setContentType("application/json");
//                return null;
//            }
//
//            //获取原来的body
//            final InputStream responseDataStream = ctx.getResponseDataStream();
//            if (responseDataStream == null) return null;
//            String resbody = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
//
//            //如果是200，直接包装返回
//            if (status == 401 || status == 400) {
//                if (resbody.contains("invalid_token")) {
//                    re.setCode(ApiResultEnum.INVALID_TOKEN.getCode()); //4011代表token不正确
//                    re.setMessage(ApiResultEnum.INVALID_TOKEN.getMessage());
//                } else if (resbody.contains("unauthorized")) {
//                    re.setCode(ApiResultEnum.UNAUTHORIZED.getCode()); //4012代表需要token登录
//                    re.setMessage(ApiResultEnum.UNAUTHORIZED.getMessage());
//                } else if (resbody.contains("unsupported_grant_type")) {
//                    re.setCode(ApiResultEnum.UNSUPPORTED_GRANT_TYPE.getCode());
//                    re.setMessage(ApiResultEnum.UNSUPPORTED_GRANT_TYPE.getMessage());
//                } else if (resbody.contains("invalid_grant")) {
//                    re.setCode(ApiResultEnum.INVALID_GRANT.getCode());
//                    re.setMessage(ApiResultEnum.INVALID_GRANT.getMessage());
//                } else if (resbody.contains("invalid_client")) {
//                    re.setCode(ApiResultEnum.INVALID_CLIENT.getCode());
//                    re.setMessage(ApiResultEnum.INVALID_CLIENT.getMessage());
//                }
//                //if(resbody.contains("invalid_token")) re.setCode(4013); //4012代表需要token登录
//                log.debug(resbody);
//                re.setData(objectMapper.readTree(resbody));
//                String rejson = objectMapper.writeValueAsString(re);
//                ctx.setResponseBody(rejson);
//                ctx.setResponseStatusCode(HttpStatus.OK.value());
//            } else if (status == 200) {
//                re.setData(objectMapper.readTree(resbody));
//                String rejson = objectMapper.writeValueAsString(re);
//                ctx.setResponseBody(rejson);
//            } else if (status == 500) {
//                //修改http code 为200，但是status还是不变
//                ctx.setResponseBody(resbody);
//                ctx.setResponseStatusCode(HttpStatus.OK.value());
//            }
//            return null;
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
