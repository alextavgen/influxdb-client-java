package org.influxdata.client.service;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import org.influxdata.client.domain.Document;
import org.influxdata.client.domain.DocumentCreate;
import org.influxdata.client.domain.DocumentUpdate;
import org.influxdata.client.domain.Documents;
import org.influxdata.client.domain.Error;
import org.influxdata.client.domain.LabelMapping;
import org.influxdata.client.domain.LabelResponse;
import org.influxdata.client.domain.LabelsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TemplatesService {
  /**
   * 
   * 
   * @param zapTraceSpan OpenTracing span context (optional)
   * @param org specifies the name of the organization of the template (optional)
   * @param orgID specifies the organization id of the template (optional)
   * @return Call&lt;Documents&gt;
   */
  @GET("api/v2/documents/templates")
  Call<Documents> documentsTemplatesGet(
    @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan, @retrofit2.http.Query("org") String org, @retrofit2.http.Query("orgID") String orgID
  );

  /**
   * Create a template
   * 
   * @param documentCreate template that will be created (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;Document&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("api/v2/documents/templates")
  Call<Document> documentsTemplatesPost(
    @retrofit2.http.Body DocumentCreate documentCreate, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

  /**
   * delete a template document
   * 
   * @param templateID ID of template (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;Void&gt;
   */
  @DELETE("api/v2/documents/templates/{templateID}")
  Call<Void> documentsTemplatesTemplateIDDelete(
    @retrofit2.http.Path("templateID") String templateID, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

  /**
   * 
   * 
   * @param templateID ID of template (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;Document&gt;
   */
  @GET("api/v2/documents/templates/{templateID}")
  Call<Document> documentsTemplatesTemplateIDGet(
    @retrofit2.http.Path("templateID") String templateID, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

  /**
   * list all labels for a template
   * 
   * @param templateID ID of template (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;LabelsResponse&gt;
   */
  @GET("api/v2/documents/templates/{templateID}/labels")
  Call<LabelsResponse> documentsTemplatesTemplateIDLabelsGet(
    @retrofit2.http.Path("templateID") String templateID, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

  /**
   * delete a label from a template
   * 
   * @param templateID ID of template (required)
   * @param labelID the label ID (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;Void&gt;
   */
  @DELETE("api/v2/documents/templates/{templateID}/labels/{labelID}")
  Call<Void> documentsTemplatesTemplateIDLabelsLabelIDDelete(
    @retrofit2.http.Path("templateID") String templateID, @retrofit2.http.Path("labelID") String labelID, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

  /**
   * add a label to a template
   * 
   * @param templateID ID of template (required)
   * @param labelMapping label to add (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;LabelResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("api/v2/documents/templates/{templateID}/labels")
  Call<LabelResponse> documentsTemplatesTemplateIDLabelsPost(
    @retrofit2.http.Path("templateID") String templateID, @retrofit2.http.Body LabelMapping labelMapping, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

  /**
   * 
   * 
   * @param templateID ID of template (required)
   * @param documentUpdate template that will be updated (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;Document&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("api/v2/documents/templates/{templateID}")
  Call<Document> documentsTemplatesTemplateIDPut(
    @retrofit2.http.Path("templateID") String templateID, @retrofit2.http.Body DocumentUpdate documentUpdate, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

}
