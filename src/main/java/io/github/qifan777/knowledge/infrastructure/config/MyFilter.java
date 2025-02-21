package io.github.qifan777.knowledge.infrastructure.config;

import io.github.qifan777.knowledge.infrastructure.config.threadLocalAutoExtends.AutoExtendsThreadListener;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class MyFilter {
//
//    private static ApplicationContext applicationContext;
//
//    public MyFilter(@Autowired ApplicationContext applicationContext) {
//        applicationContext = applicationContext;
//    }

//    @Override
//    public @Nonnull Mono<Void> filter(@Nonnull ServerWebExchange ex, @Nonnull WebFilterChain chain) {
//        ServerHttpResponse response = ex.getResponse();
//        response.writeAndFlushWith(new Flowable<>() {
//            @Override
//            protected void subscribeActual(Subscriber<? super Publisher<? extends DataBuffer>> subscriber) {
//                subscriber.onNext(PublishProcessor.defer(
//                        (Callable<Publisher<? extends DataBuffer>>) () ->
//                                PublishProcessor.defer(() -> {
//                                    PublishProcessor<DefaultDataBuffer> processor = PublishProcessor.create();
//                                    processor.onNext(DefaultDataBufferFactory.sharedInstance.wrap(
//                                            JSON.toJSONBytes(ServerSentEvent.builder("hello")
//                                                    .event("message")
//                                                    .build())));
//                                    return processor;
//                                })
//                ));
//            }
//        });
//        return chain.filter(ex);
//    }
}
