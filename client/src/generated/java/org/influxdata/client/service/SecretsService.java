package org.influxdata.client.service;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import org.influxdata.client.domain.Error;
import org.influxdata.client.domain.SecretKeys;
import org.influxdata.client.domain.SecretKeysResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SecretsService {
  /**
   * delete provided secrets
   * 
   * @param orgID ID of the organization (required)
   * @param secretKeys secret key to deleted (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("api/v2/orgs/{orgID}/secrets/delete")
  Call<Void> orgsOrgIDSecretsDeletePost(
    @retrofit2.http.Path("orgID") String orgID, @retrofit2.http.Body SecretKeys secretKeys, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

  /**
   * List all secret keys for an organization
   * 
   * @param orgID ID of the organization (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;SecretKeysResponse&gt;
   */
  @GET("api/v2/orgs/{orgID}/secrets")
  Call<SecretKeysResponse> orgsOrgIDSecretsGet(
    @retrofit2.http.Path("orgID") String orgID, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

  /**
   * Apply patch to the provided secrets
   * 
   * @param orgID ID of the organization (required)
   * @param requestBody secret key value pairs to update/add (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PATCH("api/v2/orgs/{orgID}/secrets")
  Call<Void> orgsOrgIDSecretsPatch(
    @retrofit2.http.Path("orgID") String orgID, @retrofit2.http.Body Map<String, String> requestBody, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

}
