package org.influxdata.client.service;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import org.influxdata.client.domain.Error;
import org.influxdata.client.domain.LineProtocolError;
import org.influxdata.client.domain.LineProtocolLengthError;
import org.influxdata.client.domain.WritePrecision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WriteService {
  /**
   * write time-series data into influxdb
   * 
   * @param org specifies the destination organization for writes (required)
   * @param bucket specifies the destination bucket for writes (required)
   * @param body line protocol body (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @param contentEncoding when present, its value indicates to the database that compression is applied to the line-protocol body. (optional, default to identity)
   * @param contentType Content-Type is used to indicate the format of the data sent to the server. (optional, default to text/plain; charset&#x3D;utf-8)
   * @param contentLength Content-Length is an entity header is indicating the size of the entity-body, in bytes, sent to the database. If the length is greater than the database max body configuration option, a 413 response is sent. (optional)
   * @param accept specifies the return content format. (optional, default to application/json)
   * @param precision specifies the precision for the unix timestamps within the body line-protocol (optional)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:text/plain"
  })
  @POST("api/v2/write")
  Call<Void> writePost(
    @retrofit2.http.Query("org") String org, @retrofit2.http.Query("bucket") String bucket, @retrofit2.http.Body String body, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan, @retrofit2.http.Header("Content-Encoding") String contentEncoding, @retrofit2.http.Header("Content-Type") String contentType, @retrofit2.http.Header("Content-Length") Integer contentLength, @retrofit2.http.Header("Accept") String accept, @retrofit2.http.Query("precision") WritePrecision precision
  );

}
