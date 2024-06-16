package com.afklm.spring.security.habile.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.RewriteLocationResponseHeaderGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Class in charge of overwriting the Location header when such response is
 * provided by backend. Endpoint is kept unchanged but the hostname and port
 * are replaced by proxy ones.<br>
 * It also injects the <b>x-frame-options</b> to <b>SAMEORIGIN</b> in order
 * to allow migrated app using frames to work.
 * 
 * @author m408461
 */
@Component
public class ModifyResponseFilter implements GlobalFilter, Ordered {

    @Autowired
    private RewriteLocationResponseHeaderGatewayFilterFactory rewriteLocationResponseHeaderGatewayFilterFactory;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        GatewayFilter delegateBody = rewriteLocationResponseHeaderGatewayFilterFactory.apply(new RewriteLocationResponseHeaderGatewayFilterFactory.Config());
        exchange.getResponse().getHeaders().set(XFrameOptionsServerHttpHeadersWriter.X_FRAME_OPTIONS,
                XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN.toString());
        return delegateBody.filter(exchange, chain);
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER+1;
    }
}
