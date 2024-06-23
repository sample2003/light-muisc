package com.sample.music.domain.result;

public class HttpStatusCode {
    // 1xx（信息性状态码）：表示请求已被接收，继续处理。
    //
    // 100 Continue：继续（服务器收到部分请求，要求客户端继续发送剩余部分）。
    public static final int CONTINUE = 100;
    public static final int SWITCHING_PROTOCOLS = 101;

    // 2xx（成功状态码）：请求正常处理完毕。
    //
    // 200 OK：成功（请求已成功处理）。
    // 201 Created：已创建（请求成功并且服务器创建了新的资源）。
    // 202 Accepted：已接受（服务器已接受请求，但未处理完成）。
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;

    // 3xx（重定向状态码）：需要后续操作才能完成请求。
    //
    // 301 Moved Permanently：永久移动（请求的资源已永久移动到新位置）。
    // 302 Found：找到（请求的资源临时移动到另一个URI）。
    // 304 Not Modified：未修改（如果资源未改变，则无需重新下载）。
    public static final int MOVED_PERMANENTLY = 301;
    public static final int FOUND = 302;
    public static final int NOT_MODIFIED = 304;

    // 4xx（客户端错误状态码）：请求含有词法错误或无法完成。
    //
    // 400 Bad Request：错误请求（请求无法理解或参数有误）。
    // 401 Unauthorized：未授权（请求需要用户验证）。
    // 403 Forbidden：禁止访问（服务器理解请求，但是拒绝执行）。
    // 404 Not Found：未找到（请求的资源不存在）。
    // 405 Method Not Allowed：方法不允许（请求方法不被服务器支持）。
    // 408 Request Timeout：请求超时（服务器等待请求超时）。
    // 409 Conflict：冲突（请求与当前状态冲突）。
    // 413 Payload Too Large：负载太大（请求实体过大）。
    // 415 Unsupported Media Type：不支持的媒体类型（服务器不支持请求的媒体格式）。
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int CONFLICT = 409;
    public static final int PAYLOAD_TOO_LARGE = 413;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

    // 5xx（服务器错误状态码）：服务器处理请求出错。
    //
    // 500 Internal Server Error：内部服务器错误（服务器内部错误，无法完成请求）。
    // 501 Not Implemented：未实现（服务器不支持请求的功能）。
    // 502 Bad Gateway：错误网关（充当网关或代理的服务器从上游服务器收到无效响应）。
    // 503 Service Unavailable：服务不可用（服务器目前无法使用，可能因为过载或维护）。
    // 504 Gateway Timeout：网关超时（充当网关或代理的服务器未及时从上游服务器接收请求）。
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;

    // 私有构造函数，防止实例化
    private HttpStatusCode() {
    }
}