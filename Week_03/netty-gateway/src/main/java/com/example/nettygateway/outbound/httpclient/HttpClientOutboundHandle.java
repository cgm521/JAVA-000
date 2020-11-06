package com.example.nettygateway.outbound.httpclient;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.example.nettygateway.outbound.AbsOutboundHandler;
import com.example.nettygateway.router.HttpEndpointRouter;
import com.example.nettygateway.utils.NamedThreadFactory;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/10/31 17:15
 */
@Slf4j
@Component("httpClientOutboundHandle")
public class HttpClientOutboundHandle extends AbsOutboundHandler {
    private ExecutorService proxyService;

    public HttpClientOutboundHandle() {
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        this.proxyService = new ThreadPoolExecutor(cores, cores, keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize), new NamedThreadFactory("proxyService"),
            handler);
    }

    /**
     * 请求处理器
     * @param fullRequest
     * @param ctx
     */
    @Override
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final String routeUrl) {
        proxyService.submit(() -> invoke(fullRequest, ctx, routeUrl));
    }

    /**
     * 请求执行调用
     *
     * @param fullRequest
     * @param ctx
     */
    private void invoke(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String routeUrl) {
        FullHttpResponse response = null;
        try {
            if (StringUtils.isEmpty(routeUrl)) {
                byte[] bytes = "请求服务不存在".getBytes();
                response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytes));
                response.headers().set("Content-Type", "application/json");
                response.headers().setInt("Content-Length", bytes.length);
                response.headers().set(HttpHeaderNames.CONNECTION, "close");
                return;
            }
            switch (fullRequest.method().name()) {
                case "GET":
                    response = get(fullRequest, routeUrl);
                    break;
                case "POST":
                    response = post(fullRequest, routeUrl);
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                if (!HttpUtil.isKeepAlive(response)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            // 是否引用，防止内存泄漏
            // LEAK: ByteBuf.release() was not called before it's garbage-collected. See https://netty.io/wiki/reference-counted-objects.html for more information.
            fullRequest.release();
            ctx.flush();
        }
    }



    /**
     * POST请求
     *
     * @param fullRequest
     * @return
     */
    private FullHttpResponse post(final FullHttpRequest fullRequest, final String route) {
        FullHttpResponse response = null;
        try {
            HttpClient client = new HttpClient();
            client.getParams().setContentCharset("UTF-8");

            final String url = route + fullRequest.uri();
            PostMethod postMethod = new PostMethod(url);

            addHeader(postMethod, fullRequest.headers());
            String reqParam = parse(fullRequest);

            RequestEntity requestEntity = new StringRequestEntity(reqParam, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            int status = client.executeMethod(postMethod);
            response = responseParse(status, postMethod);
        } catch (IOException e) {
            log.error("http post 请求失败", e);
            response = new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST);
            response.headers().set("Content-Type", "application/json");
            response.headers().set(HttpHeaderNames.CONNECTION, "close");
        }
        return response;
    }

    /**
     * GET请求
     *
     * @param fullRequest
     * @return
     * @throws Exception
     */
    private FullHttpResponse get(final FullHttpRequest fullRequest, final String route) throws Exception {
        FullHttpResponse response;
        try {
            HttpClient client = new HttpClient();
            client.getParams().setContentCharset("UTF-8");

            final String url = route + fullRequest.uri();
            GetMethod getMethod = new GetMethod(url);

            addHeader(getMethod, fullRequest.headers());

            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            int status = client.executeMethod(getMethod);
            response = responseParse(status, getMethod);

        } catch (IOException e) {
            log.error("http get 请求失败", e);
            response = new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST);
            response.headers().set("Content-Type", "application/json");
            response.headers().set(HttpHeaderNames.CONNECTION, "close");
        }
        return response;
    }

    /**
     * 设置请求头
     *
     * @param methodBase
     */
    private void addHeader(HttpMethodBase methodBase, HttpHeaders headers) {
        headers.entries().forEach(entry -> {
            methodBase.addRequestHeader(entry.getKey(), entry.getValue());
        });
    }

    /**
     * 响应解析
     *
     * @param status
     * @param methodBase
     * @return
     * @throws IOException
     */
    private FullHttpResponse responseParse(int status, HttpMethodBase methodBase) throws IOException {
        FullHttpResponse response;
        if (HttpStatus.SC_OK == status) {
            byte[] bytes = methodBase.getResponseBody();
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytes));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", bytes.length);
            // 不设置connection为close 会报错java.net.SocketException: Too many open files in system
            // 因为无法关闭链接，导致打开文件过多
            response.headers().set(HttpHeaderNames.CONNECTION, "close");
        } else {
            response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.valueOf(status));
            response.headers().set("Content-Type", "application/json");
            response.headers().set(HttpHeaderNames.CONNECTION, "close");
        }
        return response;
    }
}
