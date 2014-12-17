/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.groovy.core.http;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.groovy.core.MultiMap
import io.vertx.core.http.WebsocketVersion
import io.vertx.groovy.core.metrics.Measured
import io.vertx.core.http.HttpMethod
import java.util.Map
import io.vertx.core.json.JsonObject
import io.vertx.core.Handler
/**
 * An HTTP client that maintains a pool of connections to a specific host, at a specific port. The client supports
 * pipelining of requests.<p>
 * As well as HTTP requests, the client can act as a factory for {@code WebSocket websockets}.<p>
 * If an instance is instantiated from an event loop then the handlers
 * of the instance will always be called on that same event loop.
 * If an instance is instantiated from some other arbitrary Java thread (i.e. when running embedded) then
 * and event loop will be assigned to the instance and used when any of its handlers
 * are called.<p>
 * Instances of HttpClient are thread-safe.<p>
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@CompileStatic
public class HttpClient implements Measured {
  final def io.vertx.core.http.HttpClient delegate;
  public HttpClient(io.vertx.core.http.HttpClient delegate) {
    this.delegate = delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * The metric base name
   *
   * @return the metric base name
   */
  public String metricBaseName() {
    def ret = ((io.vertx.core.metrics.Measured) this.delegate).metricBaseName();
    return ret;
  }
  /**
   * Will return the metrics that correspond with this measured object.
   *
   * @return the map of metrics where the key is the name of the metric (excluding the base name) and the value is
   * the json data representing that metric
   */
  public Map<String, Map<String, Object>> metrics() {
    def ret = ((io.vertx.core.metrics.Measured) this.delegate).metrics()?.collectEntries({k, v -> [k, v.getMap()]});
    return ret;
  }
  public HttpClientRequest request(HttpMethod method, String absoluteURI) {
    def ret= HttpClientRequest.FACTORY.apply(this.delegate.request(method, absoluteURI));
    return ret;
  }
  /**
   * Create a new http client request.
   *
   * The returned request does not have yet a response handler and one should be set before sending
   * any data to the remote server.
   *
   * @param method the http method
   * @param port the remote server port
   * @param host the remote server host
   * @param requestURI the request uri
   * @return the http client request
   */
  public HttpClientRequest request(HttpMethod method, int port, String host, String requestURI) {
    def ret= HttpClientRequest.FACTORY.apply(this.delegate.request(method, port, host, requestURI));
    return ret;
  }
  public HttpClientRequest request(HttpMethod method, String absoluteURI, Handler<HttpClientResponse> responseHandler) {
    def ret= HttpClientRequest.FACTORY.apply(this.delegate.request(method, absoluteURI, new Handler<io.vertx.core.http.HttpClientResponse>() {
      public void handle(io.vertx.core.http.HttpClientResponse event) {
        responseHandler.handle(HttpClientResponse.FACTORY.apply(event));
      }
    }));
    return ret;
  }
  public HttpClientRequest request(HttpMethod method, int port, String host, String requestURI, Handler<HttpClientResponse> responseHandler) {
    def ret= HttpClientRequest.FACTORY.apply(this.delegate.request(method, port, host, requestURI, new Handler<io.vertx.core.http.HttpClientResponse>() {
      public void handle(io.vertx.core.http.HttpClientResponse event) {
        responseHandler.handle(HttpClientResponse.FACTORY.apply(event));
      }
    }));
    return ret;
  }
  public WebSocketStream websocket(int port, String host, String requestURI) {
    def ret= WebSocketStream.FACTORY.apply(this.delegate.websocket(port, host, requestURI));
    return ret;
  }
  public WebSocketStream websocket(int port, String host, String requestURI, MultiMap headers) {
    def ret= WebSocketStream.FACTORY.apply(this.delegate.websocket(port, host, requestURI, (io.vertx.core.MultiMap)headers.getDelegate()));
    return ret;
  }
  public WebSocketStream websocket(int port, String host, String requestURI, MultiMap headers, WebsocketVersion version) {
    def ret= WebSocketStream.FACTORY.apply(this.delegate.websocket(port, host, requestURI, (io.vertx.core.MultiMap)headers.getDelegate(), version));
    return ret;
  }
  /**
   * @return a {@link io.vertx.core.http.WebSocketStream} configured with the specified arguments
   */
  public WebSocketStream websocket(int port, String host, String requestURI, MultiMap headers, WebsocketVersion version, String subProtocols) {
    def ret= WebSocketStream.FACTORY.apply(this.delegate.websocket(port, host, requestURI, (io.vertx.core.MultiMap)headers.getDelegate(), version, subProtocols));
    return ret;
  }
  public HttpClient connectWebsocket(int port, String host, String requestURI, Handler<WebSocket> wsConnect) {
    def ret= HttpClient.FACTORY.apply(this.delegate.connectWebsocket(port, host, requestURI, new Handler<io.vertx.core.http.WebSocket>() {
      public void handle(io.vertx.core.http.WebSocket event) {
        wsConnect.handle(WebSocket.FACTORY.apply(event));
      }
    }));
    return ret;
  }
  public HttpClient connectWebsocket(int port, String host, String requestURI, MultiMap headers, Handler<WebSocket> wsConnect) {
    def ret= HttpClient.FACTORY.apply(this.delegate.connectWebsocket(port, host, requestURI, (io.vertx.core.MultiMap)headers.getDelegate(), new Handler<io.vertx.core.http.WebSocket>() {
      public void handle(io.vertx.core.http.WebSocket event) {
        wsConnect.handle(WebSocket.FACTORY.apply(event));
      }
    }));
    return ret;
  }
  public HttpClient connectWebsocket(int port, String host, String requestURI, MultiMap headers, WebsocketVersion version, Handler<WebSocket> wsConnect) {
    def ret= HttpClient.FACTORY.apply(this.delegate.connectWebsocket(port, host, requestURI, (io.vertx.core.MultiMap)headers.getDelegate(), version, new Handler<io.vertx.core.http.WebSocket>() {
      public void handle(io.vertx.core.http.WebSocket event) {
        wsConnect.handle(WebSocket.FACTORY.apply(event));
      }
    }));
    return ret;
  }
  public HttpClient connectWebsocket(int port, String host, String requestURI, MultiMap headers, WebsocketVersion version, String subProtocols, Handler<WebSocket> wsConnect) {
    def ret= HttpClient.FACTORY.apply(this.delegate.connectWebsocket(port, host, requestURI, (io.vertx.core.MultiMap)headers.getDelegate(), version, subProtocols, new Handler<io.vertx.core.http.WebSocket>() {
      public void handle(io.vertx.core.http.WebSocket event) {
        wsConnect.handle(WebSocket.FACTORY.apply(event));
      }
    }));
    return ret;
  }
  /**
   * Close the HTTP client. This will cause any pooled HTTP connections to be closed.
   */
  public void close() {
    this.delegate.close();
  }

  static final java.util.function.Function<io.vertx.core.http.HttpClient, HttpClient> FACTORY = io.vertx.lang.groovy.Factories.createFactory() {
    io.vertx.core.http.HttpClient arg -> new HttpClient(arg);
  };
}