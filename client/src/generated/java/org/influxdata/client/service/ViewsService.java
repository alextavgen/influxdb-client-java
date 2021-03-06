package org.influxdata.client.service;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import org.influxdata.client.domain.Error;
import org.influxdata.client.domain.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ViewsService {
  /**
   * Retrieve the view for a cell in a dashboard
   * 
   * @param dashboardID ID of dashboard (required)
   * @param cellID ID of cell (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;View&gt;
   */
  @GET("api/v2/dashboards/{dashboardID}/cells/{cellID}/view")
  Call<View> dashboardsDashboardIDCellsCellIDViewGet(
    @retrofit2.http.Path("dashboardID") String dashboardID, @retrofit2.http.Path("cellID") String cellID, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

  /**
   * Update the view for a cell
   * 
   * @param dashboardID ID of dashboard to update (required)
   * @param cellID ID of cell to update (required)
   * @param view updates the view for a cell (required)
   * @param zapTraceSpan OpenTracing span context (optional)
   * @return Call&lt;View&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PATCH("api/v2/dashboards/{dashboardID}/cells/{cellID}/view")
  Call<View> dashboardsDashboardIDCellsCellIDViewPatch(
    @retrofit2.http.Path("dashboardID") String dashboardID, @retrofit2.http.Path("cellID") String cellID, @retrofit2.http.Body View view, @retrofit2.http.Header("Zap-Trace-Span") String zapTraceSpan
  );

}
